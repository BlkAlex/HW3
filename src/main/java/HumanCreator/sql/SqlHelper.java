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

    public static void testStart(){
        String query = "select * from HUMANS.test ";

        try {
            // opening database connection to MySQL server

            con = DriverManager.getConnection(URL, USER, PASS);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
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
    public static void putHumanListToDB(ArrayList<Human> humans){
            for (Human human: humans) {
                if (!isDBhasHuman(human)){
                    //insert
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
        return false;
    }
}
