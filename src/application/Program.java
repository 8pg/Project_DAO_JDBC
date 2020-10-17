package application;

import java.util.Date;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author 8pg
 */
public class Program {

    public static void main(String[] args) {

        Department dep = new Department(5, "Camping");
        Seller sel = new Seller(8, "Jose De La Cruz", "ze@gmail.com", new Date(), 1586.00, dep);

        System.out.println(dep);
        System.out.println(sel);
    }

}
