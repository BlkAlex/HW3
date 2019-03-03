package src;

import lombok.Getter;

import java.util.ArrayList;

class InputParameters {

    @Getter
    final static String fileMaleNames = "resources/MaleNames.txt";
    @Getter
    final static String fileFemaleNames = "resources/FemaleNames.txt";
    @Getter
    final static String fileMaleSurnames = "resources/MaleSurnames.txt";
    @Getter
    final static String fileFemaleSurnames = "resources/FemaleSurnames.txt";
    @Getter
    final static String fileMalePatronymic = "resources/MalePatronymic.txt";
    @Getter
    final static String fileFemalePatronymic = "resources/FemalePatronymic.txt";
    @Getter
    final static String fileCountries = "resources/Countries.txt";
    @Getter
    final static String fileRegions = "resources/Regions.txt";
    @Getter
    final static String fileTowns = "resources/Towns.txt";
    @Getter
    final static String fileStreets = "resources/Streets.txt";

    @Getter
    final static int startRangeMailIndex = 100000;

    @Getter
    final static int endRangeMailIndex = 200000;
    //Для большего реализма данных добавлены границы генерируемых дат рождения, номера дома, квартиры
    @Getter
    final static int minYearOfBirth = 1900;

    @Getter
    final static int maxNumberHouse = 2000;

    @Getter
    final static int maxNumberFlat = 2000;

    @Getter
    final static int minCountUsers = 1;

    @Getter
    final static int maxCountUsers = 30;

    @Getter
    final static int RegionINN = 77;
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

    @Getter
    final static String maleString = "М";


    @Getter
    final static String femaleString = "Ж";
}
