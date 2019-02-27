package src;

import lombok.Getter;

import java.util.ArrayList;

public class InputParameters {

    @Getter
    final static String fileMaleNames = "resources/MaleNames";
    @Getter
    final static String fileFemaleNames = "resources/FemaleNames";
    @Getter
    final static String fileMaleSurnames = "resources/MaleSurnames";
    @Getter
    final static String fileFemaleSurnames = "resources/FemaleSurnames";
    @Getter
    final static String fileMalePatronymic = "resources/MalePatronymic";
    @Getter
    final static String fileFemalePatronymic = "resources/FemalePatronymic";
    @Getter
    final static String fileCountries = "resources/Countries";
    @Getter
    final static String fileRegions = "resources/Regions";
    @Getter
    final static String fileTowns = "resources/Towns";
    @Getter
    final static String fileStreets = "resources/Streets";

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
    final static  int RegionINN = 77;
    private static ArrayList<String> namesColumn ;
    public static ArrayList<String> getListNamesColumn(){
        if (namesColumn == null){
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
