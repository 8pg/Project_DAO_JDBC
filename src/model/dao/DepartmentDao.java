package model.dao;

import java.util.List;
import model.entities.Department;

/**
 *
 * @author P. Godoy
 */
public interface DepartmentDao {

    public void insert(Department obj);

    public void update(Department obj);

    public void deleteByID(Integer id);

    public Department findByID(Integer id);

    public void findAll(List<Department> list);
}
