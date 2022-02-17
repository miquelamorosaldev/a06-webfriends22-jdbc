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

public class CategoryDAO {

    private static DBConnect dataSource;
    private final Properties queries;
    private static String PROPS_FILE;

    public CategoryDAO(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/category_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));

        dataSource = DBConnect.getInstance(ruta);
    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    public ArrayList<Category> findAll() {
        ArrayList<Category> list = new ArrayList<>();
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {    
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));

            while (res.next()) {
                Category cat = new Category();
                cat.setId(res.getInt("id_categorie"));
                cat.setDescription(res.getString("description"));
                list.add(cat);
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
            pst.setString(1, cat.getDescription());
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
            pst.setString(1, cat.getDescription());
            pst.setInt(2, cat.getId());
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
            pst.setInt(1, cat.getId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = -2;
        }

        return rowsAffected;
    }
}