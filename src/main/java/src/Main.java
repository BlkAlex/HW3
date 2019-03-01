package src;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        int countHumans = Generator.getRand(1,30);
        ArrayList<String> maleNames =           fileLoader.getListByFileName(InputParameters.getFileMaleNames());
        ArrayList<String> femaleNames =         fileLoader.getListByFileName(InputParameters.getFileFemaleNames());
        ArrayList<String> maleSurnames =        fileLoader.getListByFileName(InputParameters.getFileMaleSurnames());
        ArrayList<String> femaleSurnames =      fileLoader.getListByFileName(InputParameters.getFileFemaleSurnames());
        ArrayList<String> malePatronymics =     fileLoader.getListByFileName(InputParameters.getFileMalePatronymic());
        ArrayList<String> femalePatronymics =   fileLoader.getListByFileName(InputParameters.getFileFemalePatronymic());
        ArrayList<String> countries =           fileLoader.getListByFileName(InputParameters.getFileCountries());
        ArrayList<String> towns =               fileLoader.getListByFileName(InputParameters.getFileTowns());
        ArrayList<String> streets =             fileLoader.getListByFileName(InputParameters.getFileStreets());
        ArrayList<String> regions =             fileLoader.getListByFileName(InputParameters.getFileRegions());


        ArrayList<Human> humans = new ArrayList<>();
        for (int i = 0 ; i < countHumans; i++){
            Human human = new Human();
            human.setSex        (Generator.getRandomSex());
            human.setName       (Generator.getRandomStringFromList((human.getSex() == SEX.MALE)?maleNames:femaleNames));
            human.setSurname    (Generator.getRandomStringFromList((human.getSex() == SEX.MALE)?maleSurnames:femaleSurnames));
            human.setPatronymic (Generator.getRandomStringFromList((human.getSex() == SEX.MALE)?malePatronymics:femalePatronymics));
            human.setCountry    (Generator.getRandomStringFromList(countries));
            human.setRegion     (Generator.getRandomStringFromList(regions));
            human.setTown       (Generator.getRandomStringFromList(towns));
            human.setStreet     (Generator.getRandomStringFromList(streets));
            human.setBithdayDate(Generator.getRandomDate(InputParameters.getMinYearOfBirth()));
            human.setAge        (Generator.getAgeByDate(human.getBithdayDate()));
            human.setInn        (Generator.getRandomINN(InputParameters.getRegionINN()));
            human.setMailIndex  (Generator.getRand(InputParameters.getStartRangeMailIndex(),InputParameters.getEndRangeMailIndex()));
            human.setNumberHouse(Generator.getRandomNumberHouse(InputParameters.getMaxNumberHouse()));
            human.setNumberFlat (Generator.getRand(1,InputParameters.getMaxNumberFlat()));
            humans.add(human);
        }
        ExcelCreator.createExcelTable(humans,InputParameters.getListNamesColumn());

        try {
            PdfCreator.createPdfDocument(humans,InputParameters.getListNamesColumn());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
