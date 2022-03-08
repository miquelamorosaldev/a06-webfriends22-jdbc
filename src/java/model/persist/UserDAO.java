package model.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import model.Category;
import model.User;

public class UserDAO {

    private static DBConnect dataSource;
    private final Properties queries;
    private static String PROPS_FILE;

    public UserDAO(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/user_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));

        dataSource = DBConnect.getInstance(ruta);
    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    
    /**
     * Mètode que usem per a validar si un usuari amb una contrassenya existeix
     * a la base de dades.
     * @param username
     * @param password
     * @return User. Dades de l'usuari. Si no hi ha dades ha fallat el login.
     */
    public User login(String username, String password) throws DBConnectionException {
        User loggedUser = new User();

        // https://www.baeldung.com/sql-injection
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("LOGIN")); )
        {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                loggedUser.setUsername(res.getString("username"));
                loggedUser.setPassword(res.getString("password"));
                loggedUser.setRole(res.getString("role"));
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Error en la conexión a la base de datos.");
        } catch (Exception e) {
            throw new DBConnectionException("Error en el sistema.");
        } 
        return loggedUser;
    }
    
    /**
     * Un cop obtingut l'usuari de la base de dades el validem.
     * @param user
     * @return boolean. Retorna cert si l'usuari és correcte. 
     */
    public boolean validateLoginUser(User user, String username, String password) {
        boolean loggedUser = false; 
        return loggedUser;
    }
    
    /**
     * Un cop obtingut l'usuari de la base de dades el validem.
     * @param user
     * @return boolean. Retorna cert si l'usuari és admin. 
     */
    public boolean validateAdminUser(User user) {
        boolean adminUser = false; 
        
        return adminUser;
    }

    
    public ArrayList<User> findAll() {
        ArrayList<User> list = new ArrayList<>();
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {    
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));

            while (res.next()) {
                User user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setRole(res.getString("role"));
                list.add(user);
            }

        } catch (SQLException e) {
            list = new ArrayList<>();
        }
        
        return list;
    }

    public int insert(Category cat) {
        int rowsAffected;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("INSERT")); )
        {
            // TODO
//            pst.setString(1, cat.getDescription());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    public int modify(Category cat) {
        int rowsAffected;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE")); )
        {
            // TODO
//            pst.setString(1, cat.getDescription());
//            pst.setInt(2, cat.getId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    public int delete(Category cat) {
        int rowsAffected;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("DELETE")); )
        {
                        // TODO
//            pst.setInt(1, cat.getId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = -2;
        }

        return rowsAffected;
    }
}