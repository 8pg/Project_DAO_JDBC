package model.dao;

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author P. Godoy
 */
public interface SellerDao {

    public void insert(Seller obj);

    public void update(Seller obj);

    public void deleteByID(Integer id);

    public Seller findByID(Integer id);

    public List<Seller> findByDepartment(Department department);

    public List<Seller> findAll();
}
