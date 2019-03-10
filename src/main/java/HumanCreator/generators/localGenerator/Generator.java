package HumanCreator.generators.localGenerator;

import HumanCreator.InputParameters;
import HumanCreator.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class Generator {
    public static int getRand(int minValue, int maxValue) {
        if (maxValue < minValue)
            return -1;
        int diff = maxValue - minValue;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += minValue;
        return i;
    }

    public static String getRandomStringFromList(KeysGlossary keys) {
        ArrayList<String> list = glossary.get(keys.getValue());
        if (list.isEmpty())
            return "";
        return list.get(getRand(0, list.size() - 1));
    }

    public static LocalDate getRandomDate(int minYear) {
        Calendar calendar = new GregorianCalendar();
        int randomYear = getRand(minYear, Calendar.getInstance().get(Calendar.YEAR));
        int randomMonth = (randomYear == Calendar.getInstance().get(Calendar.YEAR)) ?
                getRand(0, Calendar.getInstance().get(Calendar.MONTH)) :
                getRand(0, 11);
        int randomDay = (randomYear == Calendar.getInstance().get(Calendar.YEAR) && (randomMonth == Calendar.getInstance().get(Calendar.MONTH))) ?
                getRand(0, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) :
                getRand(0, 30);
        calendar.set(randomYear,
                randomMonth,
                randomDay
        );
        return calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static int getAgeByDate(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }


    public static String getRandomINN(int nRegion) {
        StringBuilder stringBuilderINN = new StringBuilder();
        stringBuilderINN.append(nRegion);
        stringBuilderINN.append(String.format("%02d", getRand(10, 99)));
        stringBuilderINN.append(String.format("%06d", getRand(1, 999999)));

        int[] COEF_1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        int[] COEF_2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

        long n11 = 0;
        for (int i = 0; i < stringBuilderINN.length(); i++) {
            n11 += COEF_1[i] * Character.getNumericValue(stringBuilderINN.charAt(i));
        }
        n11 = (n11 % 11) % 10;
        stringBuilderINN.append(n11);
        long n12 = 0;
        for (int i = 0; i < stringBuilderINN.length(); i++) {
            n12 += COEF_2[i] * Character.getNumericValue(stringBuilderINN.charAt(i));
        }
        n12 = (n12 % 11) % 10;
        stringBuilderINN.append(n12);
        return stringBuilderINN.toString();
    }

    public static Gender getRandomGender() {
        return (getRand(0, Integer.MAX_VALUE - 1) % 2 == 0) ? Gender.MALE : Gender.FEMALE;
    }

    public static String getRandomNumberHouse(int maxNumberHouse) {
        int nHouse = getRand(1, maxNumberHouse - 1);
        if (nHouse % 5 == 0) {
            int iHouse = getRand(1, maxNumberHouse - 1);
            int iHouseSecond = getRand(1, iHouse);
            return iHouse + " / " + iHouseSecond;
        }
        if (nHouse % 7 == 0) {
            int iHouse = getRand(0, maxNumberHouse - 1);
            int iHouseBuilding = getRand(1, 50);
            return iHouse + " к. " + iHouseBuilding;
        }
        return String.valueOf(getRand(0, maxNumberHouse - 1));
    }


    private static Map<String, ArrayList<String>> glossary;

    public static void initGlossary() {
        glossary = new HashMap<>();
        glossary.put(KeysGlossary.MALE_NAMES.getValue(), FileLoader.getListByFileName(InputParameters.FILE_MALE_NAMES));
        glossary.put(KeysGlossary.FEMALE_NAMES.getValue(), FileLoader.getListByFileName(InputParameters.FILE_FEMALE_NAMES));
        glossary.put(KeysGlossary.MALE_SURNAMES.getValue(), FileLoader.getListByFileName(InputParameters.FILE_MALE_SURNAMES));
        glossary.put(KeysGlossary.FEMALE_SURNAMES.getValue(), FileLoader.getListByFileName(InputParameters.FILE_FEMALE_SURNAMES));
        glossary.put(KeysGlossary.MALE_PATRONYMIC.getValue(), FileLoader.getListByFileName(InputParameters.FILE_MALE_PATRONYMIC));
        glossary.put(KeysGlossary.FEMALE_PATRONYMIC.getValue(), FileLoader.getListByFileName(InputParameters.FILE_FEMALE_PATRONYMIC));
        glossary.put(KeysGlossary.COUNTRIES.getValue(), FileLoader.getListByFileName(InputParameters.FILE_COUNTRIES));
        glossary.put(KeysGlossary.TOWNS.getValue(), FileLoader.getListByFileName(InputParameters.FILE_TOWNS));
        glossary.put(KeysGlossary.STREETS.getValue(), FileLoader.getListByFileName(InputParameters.FILE_STREETS));
        glossary.put(KeysGlossary.REGIONS.getValue(), FileLoader.getListByFileName(InputParameters.FILE_REGIONS));
    }

    public enum KeysGlossary {
        MALE_NAMES("maleNames"),
        FEMALE_NAMES("femaleNames"),
        MALE_SURNAMES("maleSurnames"),
        FEMALE_SURNAMES("femaleSurnames"),
        MALE_PATRONYMIC("malePatronymics"),
        FEMALE_PATRONYMIC("femalePatronymics"),
        COUNTRIES("countries"),
        TOWNS("towns"),
        STREETS("streets"),
        REGIONS("regions");

        @Getter
        private final String value;

        KeysGlossary(String value) {
            this.value = value;
        }
    }
}
