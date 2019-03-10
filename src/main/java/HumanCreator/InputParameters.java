package HumanCreator;

import java.util.ArrayList;

public class InputParameters {

    public final static String FILE_MALE_NAMES = "src/main/resources/MaleNames.txt";
    public final static String FILE_FEMALE_NAMES = "src/main/resources/FemaleNames.txt";
    public final static String FILE_MALE_SURNAMES = "src/main/resources/MaleSurnames.txt";
    public final static String FILE_FEMALE_SURNAMES = "src/main/resources/FemaleSurnames.txt";
    public final static String FILE_MALE_PATRONYMIC = "src/main/resources/MalePatronymic.txt";
    public final static String FILE_FEMALE_PATRONYMIC = "src/main/resources/FemalePatronymic.txt";
    public final static String FILE_COUNTRIES = "src/main/resources/Countries.txt";
    public final static String FILE_REGIONS = "src/main/resources/Regions.txt";
    public final static String FILE_TOWNS = "src/main/resources/Towns.txt";
    public final static String FILE_STREETS = "src/main/resources/Streets.txt";
    public final static int START_RANGE_MAIL_INDEX = 100000;
    public final static int END_RANGE_MAIL_INDEX = 200000;
    public final static int MIN_YEAR_OF_BIRTH = 1900;
    public final static int MAX_NUMBER_HOUSE = 2000;
    public final static int MAX_NUMBER_FLAT = 2000;
    final static int MIN_COUNT_USERS = 1;
    final static int MAX_COUNT_USERS = 30;
    public final static int REGION_INN = 77;
    public final static String MALE_STRING = "М";
    public final static String FEMALE_STRING = "Ж";

    private static ArrayList<String> namesColumn;

    static ArrayList<String> getListNamesColumn() {
        if (namesColumn == null) {
            namesColumn = new ArrayList<>();
            namesColumn.add("Имя");
            namesColumn.add("Фамилия");
            namesColumn.add("Отчество");
            namesColumn.add("Возраст");
            namesColumn.add("Пол");
            namesColumn.add("Дата рождения");
            namesColumn.add("ИНН");
            namesColumn.add("Почтовый индекс");
            namesColumn.add("Страна");
            namesColumn.add("Область");
            namesColumn.add("Город");
            namesColumn.add("Улица");
            namesColumn.add("Дом");
            namesColumn.add("Квартира");
        }
        return namesColumn;
    }


}
