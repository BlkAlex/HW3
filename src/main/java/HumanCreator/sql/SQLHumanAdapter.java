package HumanCreator.sql;

import HumanCreator.enums.Gender;
import HumanCreator.generators.localGenerator.FileLoader;
import HumanCreator.model.Human;

import java.sql.*;
import java.util.ArrayList;

public class SQLHumanAdapter {

    private static String URL;
    private static String USER;
    private static String PASS;

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    public static void initDbParams() {
        // go to txt config file and oth.
        ArrayList<String> listSQL = FileLoader.getListByFileName("src/main/resources/SQLParams");
        if (listSQL.size() > 2) {
            URL = listSQL.get(0);
            USER = listSQL.get(1);
            PASS = listSQL.get(2);
        }

    }

    public static void putHumanListToDB(ArrayList<Human> humans) {
        for (Human human : humans) {
            putHumanToDB(human);
        }
    }

    public static void putHumanToDB(Human human) {
        //  проверка присутсивия такого человека в таблице персонс
        int idHumanInDB = getHumanIdInDB(human);
        int idAddressInAddressDB;
        if (idHumanInDB == -1) {
            idAddressInAddressDB = getAddressIdInAddressDB(human);
            if (idAddressInAddressDB == -1) {
                insertHumanToAddress(human);
                insertHumanToPersons(human, getAddressIdInAddressDB(human));
            } else {
                insertHumanToAddress(human);
                insertHumanToPersons(human, idAddressInAddressDB);
            }
        } else {
            updateHumanInPesons(human, idHumanInDB);
            updateHumanInAddress(human, getAddressIdInPersonsDB(human));
        }
    }

    public static void insertHumanToAddress(Human human) {
        StringBuilder insertQueryAddress = new StringBuilder().append("INSERT INTO address (postcode,country,region,city,street,house,flat) VALUES (")
                .append("\"").append(human.getMailIndex()).append("\",")
                .append("\"").append(human.getCountry()).append("\",")
                .append("\"").append(human.getRegion()).append("\",")
                .append("\"").append(human.getTown()).append("\",")
                .append("\"").append(human.getStreet()).append("\",")
                .append(human.getNumberHouse()).append(",")
                .append(human.getNumberFlat()).append(");");
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            stmt.executeUpdate(insertQueryAddress.toString());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public static void insertHumanToPersons(Human human, int addressID) {
        StringBuilder insertQueryPerson = new StringBuilder().append("INSERT INTO persons (surname,name,middlename,birthday,gender,inn,address_id) VALUES (")
                .append("\"").append(human.getSurname()).append("\",")
                .append("\"").append(human.getName()).append("\",")
                .append("\"").append(human.getPatronymic()).append("\",")
                .append("\"").append(human.getBirthDay()).append("\",")
                .append("\"").append(human.getGender() == Gender.MALE ? "М" : "Ж").append("\",")
                .append("\"").append(human.getInn()).append("\",")
                .append(addressID).append(");");
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            stmt.executeUpdate(insertQueryPerson.toString());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public static void updateHumanInPesons(Human human, int humanID) {
        StringBuilder updateQueryPerson = new StringBuilder()
                .append("UPDATE humans SET= \"").append(human.getName())
                .append("\", surname=\"").append(human.getCountry())
                .append("\", name=\"").append(human.getRegion())
                .append("\", middlename=\"").append(human.getTown())
                .append("\", birthday=\"").append(human.getStreet())
                .append("\", gender=").append(human.getNumberHouse())
                .append("\", inn=").append(human.getNumberHouse())
                .append("WHERE id = ").append(humanID).append(";");
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            stmt.executeUpdate(updateQueryPerson.toString());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public static void updateHumanInAddress(Human human, int addressID) {
        StringBuilder updateQueryAddress = new StringBuilder()
                .append("UPDATE address SET= \"").append(human.getName())
                .append("\", country=\"").append(human.getCountry())
                .append("\", region=\"").append(human.getRegion())
                .append("\", city=\"").append(human.getTown())
                .append("\", street=\"").append(human.getStreet())
                .append("\", house=").append(human.getNumberHouse())
                .append(", flat =").append(human.getNumberFlat())
                .append("WHERE id = ").append(addressID).append(";");
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            stmt.executeUpdate(updateQueryAddress.toString());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }

    }

    public static int getHumanIdInDB(Human human) {
        StringBuilder query = new StringBuilder()
                .append("SELECT * FROM persons where name= \"").append(human.getName())
                .append("\" AND surname=\"").append(human.getSurname())
                .append("\" AND middlename=\"").append(human.getPatronymic()).append("\"");

        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return -1;// либо это индекс , либо -1 если нет такого
    }

    public static int getAddressIdInPersonsDB(Human human) {
        // do query
        // get result

        return -1;// либо это индекс , либо -1 если нет такого
    }

    public static int getAddressIdInAddressDB(Human human) {
        StringBuilder query = new StringBuilder()
                .append("SELECT * FROM address where postcode= \"").append(human.getMailIndex())
                .append("\" AND country=\"").append(human.getCountry())
                .append("\" AND region=\"").append(human.getRegion())
                .append("\" AND city=\"").append(human.getTown())
                .append("\" AND street=\"").append(human.getStreet())
                .append("\" AND house=").append(human.getNumberHouse())
                .append(" AND flat=").append(human.getNumberFlat()).append(";");
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return -1;// либо это индекс , либо -1 если нет такого

    }
}
