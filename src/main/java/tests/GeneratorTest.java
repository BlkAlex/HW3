package tests;

import org.junit.Test;
import src.Generator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GeneratorTest {

    @Test
    public void getRand() {
        assertTrue(isRandCorrect(0, 0));
        assertTrue(isRandCorrect(0, 1));
        assertFalse(isRandCorrect(5, 4));
        assertTrue(isRandCorrect(1, 87));
        assertTrue(isRandCorrect(-1, 87));
        assertTrue(isRandCorrect(100000, 200000));
        assertTrue(isRandCorrect(100000, Integer.MAX_VALUE));
    }

    private boolean isRandCorrect(int min, int max) {
        int valRand = Generator.getRand(min, max);
        return ((valRand >= min) && (valRand <= max));
    }

    @Test
    public void getRandomStringFromList() {
        ArrayList<String> listWithData = new ArrayList<>();
        listWithData.add(" ");
        listWithData.add("11");
        listWithData.add("22 ");
        listWithData.add("/0");
        listWithData.add("SomeString");
        listWithData.add("Moscow");
        listWithData.add("Peterburg");
        listWithData.add("Тикси");
        listWithData.add("Dkflbvbhjdbx");
        listWithData.add("Владимирович");
        listWithData.add("AnotherString");
        listWithData.add("Goto");

        assertTrue(listWithData.contains(Generator.getRandomStringFromList(listWithData)));
        assertNotSame(" ", (Generator.getRandomStringFromList(new ArrayList<>())));

    }

    @Test
    public void getRandomDate() {
        assertFalse(Period.between(Generator.getRandomDate(1200), LocalDate.now()).isNegative());
        assertFalse(Period.between(Generator.getRandomDate(1450), LocalDate.now()).isNegative());
        assertFalse(Period.between(Generator.getRandomDate(1900), LocalDate.now()).isNegative());
        assertFalse(Period.between(Generator.getRandomDate(2019), LocalDate.now()).isNegative());
        assertFalse(Period.between(Generator.getRandomDate(2000), LocalDate.now()).isNegative());
    }


    @Test
    public void getAgeByDate() {
        assertEquals(Period.between(LocalDate.of(2000, 1, 1), LocalDate.now()).getYears(),
                Generator.getAgeByDate(LocalDate.of(2000, 1, 1)));
        assertEquals(Period.between(LocalDate.of(2019, 4, 1), LocalDate.now()).getYears(),
                Generator.getAgeByDate(LocalDate.of(2019, 4, 1)));
        assertEquals(Period.between(LocalDate.of(2020, 3, 25), LocalDate.now()).getYears(),
                Generator.getAgeByDate(LocalDate.of(2020, 3, 25)));
        assertEquals(Period.between(LocalDate.of(1900, 1, 12), LocalDate.now()).getYears(),
                Generator.getAgeByDate(LocalDate.of(1900, 1, 12)));
        assertEquals(Period.between(LocalDate.of(1956, 1, 11), LocalDate.now()).getYears(),
                Generator.getAgeByDate(LocalDate.of(1956, 1, 11)));

    }

    @Test
    public void getRandomINN() {
        String inn = Generator.getRandomINN(77);
        long n11 = getN11fromINN(inn);
        long n12 = getN12fromINN(inn);
        assertTrue(
                inn.substring(0, 2).equals(String.valueOf(77)) &&
                        Character.getNumericValue(inn.charAt(10)) == n11 &&
                        Character.getNumericValue(inn.charAt(11)) == n12);

    }

    private static long getN11fromINN(String inn) {
        long n11 = 0;
        int[] COEF_1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        for (int i = 0; i < 10; i++) {
            n11 += COEF_1[i] * Character.getNumericValue(inn.charAt(i));
        }
        n11 = (n11 % 11) % 10;
        return n11;
    }

    private static long getN12fromINN(String inn) {
        long n12 = 0;
        int[] COEF_2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        for (int i = 0; i < 11; i++) {
            n12 += COEF_2[i] * Character.getNumericValue(inn.charAt(i));
        }
        n12 = (n12 % 11) % 10;
        return n12;
    }
}