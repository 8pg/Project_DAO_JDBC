package application;

import model.dao.DaoFactory;
import model.entities.Seller;

/**
 *
 * @author 8pg
 */
public class Program {

    public static void main(String[] args) {

        Seller sellerDao = DaoFactory.creatSellerDao().findByID(7);

        System.out.println(sellerDao);
    }

}
