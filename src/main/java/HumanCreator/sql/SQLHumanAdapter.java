package HumanCreator.sql;

import HumanCreator.enums.Gender;
import HumanCreator.generators.localGenerator.FileLoader;
import HumanCreator.generators.localGenerator.Generator;
import HumanCreator.model.Human;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLHumanAdapter {

    private static String URL;
    private static String USER;
    private static String PASS;

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void testHumans() {
        //addr id 79
        Human human = new Human.Builder()
                .setGender(Gender.MALE)
                .setName("Аверьян")
                .setSurname("Николаев")
                .setPatronymic("Кириллович")
                .setCountry("Страна")
                .setRegion("Регион")
                .setTown("Город")
                .setStreet("Улица")
                .setBirthday(LocalDate.of(1900, 12, 5))
                .setAge(Generator.getAgeByDate(LocalDate.of(1993, 7, 5)))
                .setInn("000000000000")
                .setMailIndex(111111)
                .setNumberHouse("11")
                .setNumberFlat(11)
                .build();

        putHumanToDB(human);
    }

    public static ArrayList<Human> getHumansListFromDB(int count) {
        ArrayList<Human> humans = new ArrayList<>();
        //выводим лимит (коунт) . получаем айдишник адреса из записи, и читаем таблиуу с адресами
        String query = new StringBuilder().append("SELECT * FROM persons INNER JOIN address ON persons.address_id = address.id LIMIT ").append(count).append(";").toString();
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Human human = new Human.Builder()
                        .setGender(rs.getString("gender").equals("М") ? Gender.MALE : Gender.FEMALE)
                        .setName(rs.getString("name"))
                        .setSurname(rs.getString("surname"))
                        .setPatronymic(rs.getString("middlename"))
                        .setCountry(rs.getString("country"))
                        .setRegion(rs.getString("region"))
                        .setTown(rs.getString("city"))
                        .setStreet(rs.getString("street"))
                        // .setBirthday(LocalDate.parse(rs.getString("birthday")))
                        //.setAge(Generator.getAgeByDate(LocalDate.parse(rs.getString("birthday"), DateTimeFormatter.ofPattern("YYYY-MM-DD"))))
                        .setInn(rs.getString("inn"))
                        .setMailIndex(Integer.valueOf(rs.getString("postcode")))
                        .setNumberHouse(rs.getString("house"))
                        .setNumberFlat(Integer.valueOf(rs.getString("flat")))
                        .build();
                humans.add(human);

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


        return humans;

    }

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
            updateHumanInAddress(human, getAddressIdInPersonsDB(idHumanInDB));
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
                .append("UPDATE persons SET ")
                .append("surname=\"").append(human.getSurname())
                .append("\", name=\"").append(human.getName())
                .append("\", middlename=\"").append(human.getPatronymic())
                .append("\", birthday=\"").append(human.getBirthDay())
                .append("\", gender=\"").append((human.getGender() == Gender.MALE) ? "М" : "Ж")
                .append("\", inn=\"").append(human.getInn())
                .append("\" WHERE id = ").append(humanID).append(";");
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
                .append("UPDATE address SET postcode=\"").append(human.getMailIndex())
                .append("\", country=\"").append(human.getCountry())
                .append("\", region=\"").append(human.getRegion())
                .append("\", city=\"").append(human.getTown())
                .append("\", street=\"").append(human.getStreet())
                .append("\", house=").append(human.getNumberHouse())
                .append(", flat =").append(human.getNumberFlat())
                .append(" WHERE id = ").append(addressID).append(";");
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

    public static int getAddressIdInPersonsDB(int idHuman) {
        StringBuilder query = new StringBuilder()
                .append("SELECT * FROM persons where id= \"").append(idHuman).append("\";");

        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
                return rs.getInt("address_id");
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
