package application;

import java.util.List;
import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author 8pg
 */
public class Program {

    public static void main(String[] args) {

        List<Seller> sellerDao = DaoFactory.creatSellerDao().findByDepartment(new Department(2));

        for (Seller seller : sellerDao) {
            System.out.println(sellerDao);
        }
    }

}
