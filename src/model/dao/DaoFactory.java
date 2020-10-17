package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

/**
 * Classe com operações estaticas para instanciar os DAO
 *
 * @author P. Godoy
 */
public class DaoFactory {

    public static SellerDao creatSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}
