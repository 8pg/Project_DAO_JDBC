package model.dao.impl;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author P. Godoy
 */
public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            prst = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentID) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            prst.setString(1, obj.getName());
            prst.setString(2, obj.getEmail());
            prst.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); //Instancia um obj sql.Date que recebe uma data convertida em milissegundos, é passada a data do obj convertendo-a em milissegundos pelo getTime()
            prst.setDouble(4, obj.getBaseSalary());
            prst.setInt(5, obj.getDepartment().getId());

            int rowsAffected = prst.executeUpdate();

            if (rowsAffected > 0) {
                rs = prst.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id); //Seta o Id no objeto referente a contagem das linhas do começo ate null-1
                }
            } else {
                throw new db.DbException("No row affected!");
            }
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(prst);
            DB.closeSResultSet(rs);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            prst = conn.prepareStatement("UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ? ", Statement.RETURN_GENERATED_KEYS);

            prst.setString(1, obj.getName());
            prst.setString(2, obj.getEmail());
            prst.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); //Instancia um obj sql.Date que recebe uma data convertida em milissegundos, é passada a data do obj convertendo-a em milissegundos pelo getTime()
            prst.setDouble(4, obj.getBaseSalary());
            prst.setInt(5, obj.getDepartment().getId());
            prst.setInt(6, obj.getId());

            prst.executeUpdate();
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(prst);
            DB.closeSResultSet(rs);
        }
    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement prst = null;
        try {
            prst = conn.prepareStatement("DELETE FROM seller WHERE id = ? ");

            prst.setInt(1, id);

            prst.executeUpdate();
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(prst);
        }
    }

    @Override
    public Seller findByID(Integer id) {
        PreparedStatement prst = null;
        ResultSet rs = null;
        Seller sel = null;
        try {
            prst = conn.prepareStatement("select s.*, d.Name as Department from department d, seller s where s.DepartmentId = d.Id and s.Id = ? "); //Espera uma string que é o comando sql, e atribuindo ao result set

            prst.setInt(1, id);

            rs = prst.executeQuery();

            if (rs.next()) { //se existir um proximo
                return sel = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), new Department(rs.getInt("DepartmentId"), rs.getString("Department")));
            }
            return null;
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(prst);
            DB.closeSResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement prst = null;
        ResultSet rs = null;
        List<Seller> list = new ArrayList<>();

        try {
            prst = conn.prepareStatement("select s.*, d.Name as Department from department d, seller s where d.Id = s.DepartmentId and d.Id = ? order by Name");
            prst.setInt(1, department.getId());

            rs = prst.executeQuery();

            while (rs.next()) {
                if (department.getName() == null) {
                    department = new Department(rs.getInt("DepartmentId"), rs.getString("Department"));
                }
                list.add(new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), department));
            }
            return list;
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(prst);
            DB.closeSResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<Seller> list = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery("select s.*, d.Name as Department from department d, seller s where d.Id = s.DepartmentId order by Name");

            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = new Department(rs.getInt("DepartmentId"), rs.getString("Department"));
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                list.add(new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep));
            }
            return list;
        } catch (SQLException e) {
            throw new db.DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeSResultSet(rs);
        }
    }

}
