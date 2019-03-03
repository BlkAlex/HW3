package src;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

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

    public static String getRandomStringFromList(ArrayList<String> inputList) {
        if (inputList.isEmpty())
            return "";
        return inputList.get(getRand(0, inputList.size() - 1));
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

    static SEX getRandomSex() {
        return (getRand(0, Integer.MAX_VALUE - 1) % 2 == 0) ? SEX.MALE : SEX.FEMALE;
    }

    static String getRandomNumberHouse(int maxNumberHouse) {
        int nHouse = getRand(1, maxNumberHouse - 1);
        // сделаю вероятность выпадения дробного номера дома 1 к 5
        if (nHouse % 5 == 0) {
            int iHouse = getRand(1, maxNumberHouse - 1);
            int iHouseSecond = getRand(1, iHouse);
            return iHouse + " / " + iHouseSecond;
        }
        if (nHouse % 7 == 0) {
            int iHouse = getRand(0, maxNumberHouse - 1);
            int iHouseBuilding = getRand(1, 50);// 50 - максимальный номер корпуса из возможных
            return iHouse + " к. " + iHouseBuilding;
        }
        return String.valueOf(getRand(0, maxNumberHouse - 1));
    }
}
