package HumanCreator.sql;

import HumanCreator.model.Human;

import java.sql.*;
import java.util.ArrayList;

public class SqlHelper {
    public static final String URL = "jdbc:mysql://localhost:3306/HUMANS?serverTimezone=UTC";
    public static final String USER = "blk-sql";
    public static final String PASS = "61754911";
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

        return null;
    }
    public static void testStart(){
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

    private static int putHumanToDB(Human human){
        StringBuilder queryAddress = new StringBuilder().append("INSERT INTO address (postcode,country,region,city,street,house,flat) VALUES (")
                .append("\"").append(human.getMailIndex()).append("\",")
                .append("\"").append(human.getCountry()).append("\",")
                .append("\"").append(human.getRegion()).append("\",")
                .append("\"").append(human.getTown()).append("\",")
                .append("\"").append(human.getStreet()).append("\",")
                .append(human.getNumberHouse()).append(",")
                .append(human.getNumberFlat()).append(")");


        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            int res = stmt.executeUpdate(queryAddress.toString());
            return res;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {

            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }


        StringBuilder queryPerson = new StringBuilder().append("INSERT INTO person (surname,name,birthday,gender,inn,address_id) VALUES (")
                .append("\"").append(human.getMailIndex()).append("\",")
                .append("\"").append(human.getCountry()).append("\",")
                .append("\"").append(human.getRegion()).append("\",")
                .append("\"").append(human.getTown()).append("\",")
                .append("\"").append(human.getStreet()).append("\",")
                .append(human.getNumberHouse()).append(",")
                .append(human.getNumberFlat()).append(")");

        return -1;
    }
    public static void putHumanListToDB(ArrayList<Human> humans){
            for (Human human: humans) {
                if (!isDBhasHuman(human)){
                    putHumanToDB(human);
                }
                else {
                    //update
                }
            }
    }
    public static ArrayList<Human> getHumansListFromDB(){
        ArrayList <Human> humans = new ArrayList<>();
        //getting humans in iteration
        return humans;
    }
    private static boolean isDBhasHuman(Human human){
        StringBuilder sb = new StringBuilder()
                .append("SELECT * FROM persons where name= \"").append(human.getName())
                .append("\" AND surname=\"").append(human.getSurname())
                .append("\" AND middlename=\"").append(human.getPatronymic()).append("\"");
        ResultSet resultSet = getResultByQuery(sb.toString());
        return false;
    }
}
