package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author P. Godoy
 */
public class DB {

    private static Connection conn = null; //Objeto de conexão com o banco de dados do jdbc, importar do java.sql

    //Criação de metodo para conexão com o banco de dados
    public static Connection getConnection() {
        if (conn == null) {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            try {
                conn = DriverManager.getConnection(url, props); //é passado a url do banco e as propriedades de conexão
                //Conectar com o banco de dados no jdbc é instanciar um objeto do tipo connection
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs); //Faz a leitura do arquivo properties apontado pelo InputStream fs e guarda os dados dentro do arquivo props
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeSResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        try {
            return "Connected to database: " + conn.getCatalog();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

}
