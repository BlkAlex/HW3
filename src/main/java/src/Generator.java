package src;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class Generator {
    public static int getRand(int min,int max){
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;
        return i;
    }
    public static String getRandomStringFromList(ArrayList<String> inputList){

        return inputList.get(getRand(0,inputList.size()-1));
    }
    public static LocalDate getRandomDate(int minYear){
        Calendar calendar = new GregorianCalendar();
        int rYear = getRand(minYear,Calendar.getInstance().get(Calendar.YEAR));
        int rMonth = (rYear == Calendar.getInstance().get(Calendar.YEAR))?
                getRand(0,Calendar.getInstance().get(Calendar.MONTH)):
                getRand(0,11);
        int rDay = (rYear == Calendar.getInstance().get(Calendar.YEAR) && ( rMonth == Calendar.getInstance().get(Calendar.MONTH)) )?
                getRand(0,Calendar.getInstance().get(Calendar.DAY_OF_MONTH)):
                getRand(0,30);
        calendar.set(rYear,
                rMonth,
                rDay
        );
        return  calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static int getAgeByDate(LocalDate date){
        Period period = Period.between ( date,LocalDate.now() );
        return period.getYears ();
    }

    public static String getRandomINN(int nRegion){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nRegion);
        stringBuilder.append(String.format("%02d",getRand(10, 99)));
        stringBuilder.append(String.format("%06d",getRand(1, 999999)));


        int[] COEF_1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        int[] COEF_2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

        long n11 = 0;
        for (int i = 0 ; i<stringBuilder.length();i++){
            n11 += COEF_1[i]*Character.getNumericValue(stringBuilder.charAt(i));
        }
        n11 = (n11 % 11) % 10;
        stringBuilder.append(n11);
        long n12 = 0;
        for (int i = 0 ; i<stringBuilder.length();i++){
            n12 += COEF_2[i]*Character.getNumericValue(stringBuilder.charAt(i));
        }
        n12 = (n12 % 11) % 10;


        stringBuilder.append(n12);

        return stringBuilder.toString();
    }
    public static SEX getRandomSex(){

        return (getRand(0,Integer.MAX_VALUE-1) % 2 == 0)?SEX.MALE:SEX.FEMALE;
    }
    public static String getRandomNumberHouse(int maxNumberHouse){
        int nHouse = getRand(0,maxNumberHouse-1);
        // сделаю вероятность выпадения дробного номера дома 1 к 5
        if (nHouse % 5 == 0){
            int i1 = getRand(0, maxNumberHouse-1);
            int i2 = getRand(0, i1);
            return  i1 + " / "+ i2;
        }
        return String.valueOf(getRand(0, maxNumberHouse-1));
    }
}
