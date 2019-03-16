package HumanCreator.sql;

import HumanCreator.enums.Gender;
import HumanCreator.model.Human;

import java.sql.*;
import java.util.ArrayList;

public class SqlHelper {
    public static final String URL = "jdbc:mysql://localhost:3306/HUMANS?serverTimezone=UTC";
    public static final String USER = "blk";
    public static final String PASS = "11111111";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static ResultSet getResultByQuery(String query){
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return rs;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {

            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return rs;
    }

    private static void testStart() {
        String query = "select * from HUMANS.test ";
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                System.out.println("id :"+id +" name :" + name + " surname : " + surname);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    private static void insertQuery(String query) {
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            int res = stmt.executeUpdate(query.toString());
            // return res;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {

            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    private static void insertHumanToDB(Human human) {
        StringBuilder insertQueryAddress = new StringBuilder().append("INSERT INTO address (postcode,country,region,city,street,house,flat) VALUES (")
                .append("\"").append(human.getMailIndex()).append("\",")
                .append("\"").append(human.getCountry()).append("\",")
                .append("\"").append(human.getRegion()).append("\",")
                .append("\"").append(human.getTown()).append("\",")
                .append("\"").append(human.getStreet()).append("\",")
                .append(human.getNumberHouse()).append(",")
                .append(human.getNumberFlat()).append(");");
        insertQuery(insertQueryAddress.toString());
        int addressId = getDBAddressID(human);
        StringBuilder insertQueryPerson = new StringBuilder().append("INSERT INTO persons (surname,name,middlename,birthday,gender,inn,address_id) VALUES (")
                .append("\"").append(human.getSurname()).append("\",")
                .append("\"").append(human.getName()).append("\",")
                .append("\"").append(human.getPatronymic()).append("\",")
                .append("\"").append(human.getBirthDay()).append("\",")
                .append("\"").append(human.getGender() == Gender.MALE ? "М" : "Ж").append("\",")
                .append("\"").append(human.getInn()).append("\",")
                .append(2).append(");");
        insertQuery(insertQueryPerson.toString());
    }

    private static void putHumanToDB(Human human) {

        int addressID = getDBAddressID(human);
        int humanID = isDBhasFIO(human);

        if (addressID == -1 && humanID == -1) {
            insertHumanToDB(human);
        }

        StringBuilder updateQueryAddress = new StringBuilder()
                .append("UPDATE address SET= \"").append(human.getName())
                .append("\", country=\"").append(human.getCountry())
                .append("\", region=\"").append(human.getRegion())
                .append("\", city=\"").append(human.getTown())
                .append("\", street=\"").append(human.getStreet())
                .append("\", house=").append(human.getNumberHouse())
                .append(", flat =").append(human.getNumberFlat())
                .append("WHERE id = ").append(addressID).append(";");



    }
    public static void putHumanListToDB(ArrayList<Human> humans){
            for (Human human: humans) {
                putHumanToDB(human);
            }
    }
    public static ArrayList<Human> getHumansListFromDB(){
        ArrayList <Human> humans = new ArrayList<>();
        //getting humans in iteration
        return humans;
    }

    private static int isDBhasFIO(Human human) {
        StringBuilder sb = new StringBuilder()
                .append("SELECT * FROM persons where name= \"").append(human.getName())
                .append("\" AND surname=\"").append(human.getSurname())
                .append("\" AND middlename=\"").append(human.getPatronymic()).append("\"");
        ResultSet resultSet = getResultByQuery(sb.toString());
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static int getDBAddressID(Human human) {
        StringBuilder sb = new StringBuilder()
                .append("SELECT * FROM address where postcode= \"").append(human.getMailIndex())
                .append("\" AND country=\"").append(human.getCountry())
                .append("\" AND region=\"").append(human.getRegion())
                .append("\" AND city=\"").append(human.getTown())
                .append("\" AND street=\"").append(human.getStreet())
                .append("\" AND house=").append(human.getNumberHouse())
                .append(" AND flat=").append(human.getNumberFlat()).append(";");
        ResultSet resultSet = getResultByQuery(sb.toString());
        try {
            if (!resultSet.isClosed()) {

                return resultSet.getInt(1);
            } else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
