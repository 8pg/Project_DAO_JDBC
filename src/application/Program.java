package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author 8pg
 */
public class Program {

    public static void main(String[] args) throws ParseException {

        SellerDao sellerDao = DaoFactory.creatSellerDao();

        //Find Seller by ID
        System.out.println("=== Test #1: Seller findByID | Id: 1 =======================");
        Seller selId = sellerDao.findByID(1);

        System.out.println(selId);
        System.out.println("");

        //Find Sellers by Department
        System.out.println("=== Test #2: Seller findByDepartment | DepartmentId: 2 =====");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Seller> listSellerDepartment = sellerDao.findByDepartment(new Department(2, null));

        for (Seller seller : listSellerDepartment) {
            System.out.println(seller);
        }
        System.out.println("");

        //Find All Sellers
        System.out.println("=== Test #3: findAll Sellers ===============================");
        List<Seller> selAll = sellerDao.findAll();

        for (Seller seller : selAll) {
            System.out.println(seller);
        }
        System.out.println("");
    }

}
