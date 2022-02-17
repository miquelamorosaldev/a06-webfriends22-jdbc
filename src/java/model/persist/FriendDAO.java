package model.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Friend;

public class FriendDAO {

    private final Properties queries;
    private static String PROPS_FILE;
    private static DBConnect dataSource;

    public FriendDAO(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/friend_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));

        dataSource = DBConnect.getInstance(ruta);
    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    /**
     * <strong>getDataSource()</strong>
     *
     * @return object to connect to database.
     */
    public static DBConnect getDataSource() {
        return dataSource;
    }

    public int insert(Friend friend) {
        int rowsAffected = 0;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("INSERT")); )
        {
            // Fill the ? in the query: 
            // INSERT INTO friends (phone, name, age, id_categorie) VALUES (?, ?, ?, ?)
            pst.setString(1, friend.getPhone());
            pst.setString(2, friend.getName());
            pst.setInt(3, friend.getAge());
            pst.setInt(4, friend.getCategoryId());
            rowsAffected = pst.executeUpdate();
            
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    public int update(Friend friend) {
        int rowsAffected = 0;
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE")); )
        {
            // Fill the ? in the query: 
            // UPDATE friends SET name=?, phone=?, age=?, id_categorie=? WHERE id_friends=?
            pst.setString(1, friend.getName());
            pst.setString(2, friend.getPhone());
            pst.setInt(3, friend.getAge());
            pst.setInt(4, friend.getCategoryId());
            pst.setInt(5, friend.getFriendID());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    public int remove(Friend friend) {
        int rowsAffected = 0;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("DELETE")); )
        {
            pst.setInt(1, friend.getFriendID());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = -2;
        }

        return rowsAffected;
    }

    public ArrayList<Friend> findAll() {
        ArrayList<Friend> list = new ArrayList<>();
        
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));
            // DONE, 06/04/2021.
            while (res.next()) {
                Friend friend = new Friend();
                // Check the parameters match with friends.jsp form.
                friend.setFriendID(res.getInt("id_friends"));
                friend.setName(res.getString("name"));
                friend.setAge(res.getInt("age"));
                friend.setPhone(res.getString("phone"));
                friend.setCategoryDesc(res.getString("description"));
                friend.setCategoryId(res.getInt("id_categorie"));
                list.add(friend);
            }
            
        } catch (SQLException e) {
            list = new ArrayList<>();
        }

        return list;
    }

    public Friend findOne(int friendId) {
        Friend resultFriend = new Friend();

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FIND_ONE")); )
        {
            st.setInt(1, friendId);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                // Check the parameters match with friends.jsp form.
                resultFriend.setFriendID(res.getInt("id_friends"));
                resultFriend.setName(res.getString("name"));
                resultFriend.setAge(res.getInt("age"));
                resultFriend.setPhone(res.getString("phone"));
                resultFriend.setCategoryDesc(res.getString("description"));
                resultFriend.setCategoryId(res.getInt("id_categorie"));
            }
        } catch (SQLException e) {
            resultFriend = null;
        }

        return resultFriend;
    }
    
    public ArrayList<Friend> findByCategory(int categoryId) {
        ArrayList<Friend> resultFriend = new ArrayList<>();

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FIND_BYCATEGORY")); )
        {
            st.setInt(1, categoryId);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                Friend friend = new Friend();
                // Check the parameters match with friends.jsp form.
                friend.setFriendID(res.getInt("id_friends"));
                friend.setName(res.getString("name"));
                friend.setAge(res.getInt("age"));
                friend.setPhone(res.getString("phone"));
                friend.setCategoryDesc(res.getString("description"));
                friend.setCategoryId(res.getInt("id_categorie"));
                resultFriend.add(friend);
            }
        } catch (SQLException e) {
            resultFriend = null;
        }

        return resultFriend;
    }

}
