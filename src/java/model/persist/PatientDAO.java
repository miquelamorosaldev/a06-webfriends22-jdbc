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

import model.Patient;

public class PatientDAO {

    private final Properties queries;
    private static String PROPS_FILE;
    private static DBConnect dataSource;

    public PatientDAO(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/patient_queries.properties";
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

    // TODO NOT FINISHED.
    public int insert(Patient patient) {
        int rowsAffected = 0;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("INSERT")); )
        {
            // Fill the ? in the query: 
            // INSERT INTO friends (phone, name, age, id_categorie) VALUES (?, ?, ?, ?)
            pst.setInt(1, patient.getRegisterId());
//            pst.setString(2, friend.getName());
//            pst.setInt(3, friend.getAge());
//            pst.setInt(4, friend.getCategoryId());
            rowsAffected = pst.executeUpdate();
            
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    // TODO NOT FINISHED.
    public int update(Patient patient) {
        int rowsAffected = 0;
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE")); )
        {
            // Fill the ? in the query: 
            // UPDATE friends SET name=?, phone=?, age=?, id_categorie=? WHERE id_friends=?
//            pst.setString(1, patient.getName());
            
        } catch (SQLException e) {
            rowsAffected = 0;
        }
        return rowsAffected;
    }

        // TODO NOT FINISHED.
    public int remove(Patient patient) {
        int rowsAffected = 0;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("DELETE")); )
        {
//            pst.setInt(1, friend.getFriendID());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = -2;
        }

        return rowsAffected;
    }

    public ArrayList<Patient> findAll() {
        ArrayList<Patient> list = new ArrayList<>();
        
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));
            // DONE, 06/03/2022.
            while (res.next()) {
                Patient patient = new Patient();
                // Check the parameters match with friends.jsp form.
                //  SELECT p.idRegistre, p.edat, p.grupEdat, p.IMC, 
                // p.classificació, p.menarquia, p.tipusMenopausia FROM Patient p;
                patient.setRegisterId(res.getInt("idRegistre"));
                patient.setAge(res.getInt("edat"));
                patient.setAgeGroup(res.getString("grupEdat"));
                patient.setImc(res.getDouble("IMC"));
                patient.setClassification(res.getString("classificació"));
                patient.setMenarche(res.getInt("menarquia"));
                patient.setMenopauseType(res.getString("tipusMenopausia"));
                list.add(patient);
            }
            
        } catch (SQLException e) {
            list = new ArrayList<>();
        }

        return list;
    }

    public Patient findOne(int patientId) {
        Patient resultFriend = new Patient();

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FIND_ONE")); )
        {
            st.setInt(1, patientId);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                // Check the parameters match with friends.jsp form.
//                resultFriend.setFriendID(res.getInt("id_friends"));
//                resultFriend.setName(res.getString("name"));
//                resultFriend.setAge(res.getInt("age"));
//                resultFriend.setPhone(res.getString("phone"));
//                resultFriend.setCategoryDesc(res.getString("description"));
//                resultFriend.setCategoryId(res.getInt("id_categorie"));
            }
        } catch (SQLException e) {
            resultFriend = null;
        }

        return resultFriend;
    }
    
    public ArrayList<Patient> findByCategory(int patientId) {
        ArrayList<Patient> resultFriend = new ArrayList<>();

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FIND_BYCATEGORY")); )
        {
            st.setInt(1, patientId);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                Patient friend = new Patient();
                // Check the parameters match with friends.jsp form.
//                friend.setFriendID(res.getInt("id_friends"));
//                friend.setName(res.getString("name"));
//                friend.setAge(res.getInt("age"));
//                friend.setPhone(res.getString("phone"));
//                friend.setCategoryDesc(res.getString("description"));
//                friend.setCategoryId(res.getInt("id_categorie"));
                resultFriend.add(friend);
            }
        } catch (SQLException e) {
            resultFriend = null;
        }

        return resultFriend;
    }

}
