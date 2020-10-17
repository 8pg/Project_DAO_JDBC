package model.dao.impl;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Seller obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
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

            if (rs.next()) { //enquanto existir um proximo
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
        List<Seller> list = null;

        try {
            prst = conn.prepareCall("select s.*, d.Name as Department from department d, seller s where d.Id = s.DepartmentId and d.Id = ? order by Name");
            prst.setInt(1, department.getId());

            rs = prst.executeQuery();

            while (rs.next()) {
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
    public void findAll(List<Seller> list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
