package HumanCreator.sql;

import HumanCreator.enums.Gender;
import HumanCreator.generators.localGenerator.FileLoader;
import HumanCreator.generators.localGenerator.Generator;
import HumanCreator.model.Human;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLHumansAdapter {

    private static String URL;
    private static String USER;
    private static String PASS;

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static ArrayList<Human> getHumansListFromDB(int count) {
        ArrayList<Human> humans = new ArrayList<>();
        String query = "SELECT * FROM persons INNER JOIN address ON persons.address_id = address.id LIMIT " + count + ";";

        getResultMapBySelectQuery(query);
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
                        .setBirthday(LocalDate.parse(rs.getString("birthday"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .setAge(Generator.getAgeByDate(LocalDate.parse(rs.getString("birthday"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        .setInn(rs.getString("inn"))
                        .setMailIndex(Integer.valueOf(rs.getString("postcode")))
                        .setNumberHouse(rs.getString("house"))
                        .setNumberFlat(Integer.valueOf(rs.getString("flat")))
                        .build();
                humans.add(human);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                rs.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
        }
        return humans;
    }

    private static ArrayList<HashMap<String, String>> getResultMapBySelectQuery(String query) {
        ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                int columnCount = rsmd.getColumnCount();
                HashMap<String, String> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnName(i), rs.getString(i));
                }
                hashMapArrayList.add(map);
            }

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                rs.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
        }
        return hashMapArrayList;
    }

    private static int getResultByUpdateQuery(String query) {
        int countRows = 0;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            stmt = con.createStatement();
            countRows = stmt.executeUpdate(query);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                stmt.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
            try {
                rs.close();
            } catch (SQLException se) {
                System.out.println(se.toString());
            }
        }
        return countRows;
    }

    public static void initDbParams() {
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

    private static void putHumanToDB(Human human) {
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

    private static void insertHumanToAddress(Human human) {
        String insertQueryAddress = "INSERT INTO address (postcode,country,region,city,street,house,flat) VALUES (" +
                "\"" + human.getMailIndex() + "\"," +
                "\"" + human.getCountry() + "\"," +
                "\"" + human.getRegion() + "\"," +
                "\"" + human.getTown() + "\"," +
                "\"" + human.getStreet() + "\"," +
                human.getNumberHouse() + "," +
                human.getNumberFlat() + ");";
        getResultByUpdateQuery(insertQueryAddress);
    }

    private static void insertHumanToPersons(Human human, int addressID) {
        String insertQueryPerson = "INSERT INTO persons (surname,name,middlename,birthday,gender,inn,address_id) VALUES (" +
                "\"" + human.getSurname() + "\"," +
                "\"" + human.getName() + "\"," +
                "\"" + human.getPatronymic() + "\"," +
                "\"" + human.getBirthDay() + "\"," +
                "\"" + (human.getGender() == Gender.MALE ? "М" : "Ж") + "\"," +
                "\"" + human.getInn() + "\"," +
                addressID + ");";
        getResultByUpdateQuery(insertQueryPerson);
    }

    private static void updateHumanInPesons(Human human, int humanID) {
        String updateQueryPerson = "UPDATE persons SET " +
                "surname=\"" + human.getSurname() +
                "\", name=\"" + human.getName() +
                "\", middlename=\"" + human.getPatronymic() +
                "\", birthday=\"" + human.getBirthDay() +
                "\", gender=\"" + ((human.getGender() == Gender.MALE) ? "М" : "Ж") +
                "\", inn=\"" + human.getInn() +
                "\" WHERE id = " + humanID + ";";
        getResultByUpdateQuery(updateQueryPerson);
    }

    private static void updateHumanInAddress(Human human, int addressID) {
        String updateQueryAddress = "UPDATE address SET postcode=\"" + human.getMailIndex() +
                "\", country=\"" + human.getCountry() +
                "\", region=\"" + human.getRegion() +
                "\", city=\"" + human.getTown() +
                "\", street=\"" + human.getStreet() +
                "\", house=" + human.getNumberHouse() +
                ", flat =" + human.getNumberFlat() +
                " WHERE id = " + addressID + ";";
        getResultByUpdateQuery(updateQueryAddress);
    }

    private static int getHumanIdInDB(Human human) {
        String query = "SELECT * FROM persons where name= \"" + human.getName() +
                "\" AND surname=\"" + human.getSurname() +
                "\" AND middlename=\"" + human.getPatronymic() + "\";";
        ArrayList<HashMap<String, String>> hashMapArrayList = getResultMapBySelectQuery(query);
        if (hashMapArrayList.size() == 0) {
            return -1;
        } else {
            return Integer.valueOf(hashMapArrayList.get(0).get("id"));
        }
    }

    private static int getAddressIdInPersonsDB(int idHuman) {
        ArrayList<HashMap<String, String>> hashMapArrayList = getResultMapBySelectQuery("SELECT * FROM persons where id= \"" + idHuman + "\";");
        if (hashMapArrayList.size() == 0) {
            return -1;
        } else {
            return Integer.valueOf(hashMapArrayList.get(0).get("address_id"));
        }
    }

    private static int getAddressIdInAddressDB(Human human) {
        String query = "SELECT * FROM address where postcode= \"" + human.getMailIndex() +
                "\" AND country=\"" + human.getCountry() +
                "\" AND region=\"" + human.getRegion() +
                "\" AND city=\"" + human.getTown() +
                "\" AND street=\"" + human.getStreet() +
                "\" AND house=" + human.getNumberHouse() +
                " AND flat=" + human.getNumberFlat() + ";";
        ArrayList<HashMap<String, String>> hashMapArrayList = getResultMapBySelectQuery(query);
        if (hashMapArrayList.size() == 0) {
            return -1;
        } else {
            return Integer.valueOf(hashMapArrayList.get(0).get("id"));
        }

    }
}
