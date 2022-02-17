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