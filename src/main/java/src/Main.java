package src;

import com.itextpdf.text.DocumentException;
import src.localGenerator.Generator;
import src.remoteApiGenerator.ApiReader;
import src.remoteApiGenerator.JsonParser;
import src.remoteApiGenerator.UserPojo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main {
    private static Map<String,ArrayList<String>> glossary;
    public static void initGlossary(){

        glossary.put("maleNames",FileLoader.getListByFileName(InputParameters.getFileMaleNames()));
        glossary.put("femaleNames",FileLoader.getListByFileName(InputParameters.getFileFemaleNames()));
        glossary.put("maleSurnames", FileLoader.getListByFileName(InputParameters.getFileMaleSurnames()));
        glossary.put("femaleSurnames",FileLoader.getListByFileName(InputParameters.getFileFemaleSurnames()));
        glossary.put("malePatronymics",FileLoader.getListByFileName(InputParameters.getFileMalePatronymic()));
        glossary.put("femalePatronymics",FileLoader.getListByFileName(InputParameters.getFileFemalePatronymic()));
        glossary.put("countries",FileLoader.getListByFileName(InputParameters.getFileCountries()));
        glossary.put("towns",FileLoader.getListByFileName(InputParameters.getFileTowns()));
        glossary.put("streets",FileLoader.getListByFileName(InputParameters.getFileStreets()));
        glossary.put("regions",FileLoader.getListByFileName(InputParameters.getFileRegions()));
    }
    public static void main(String[] args) {
        int countHumans = Generator.getRand(InputParameters.getMinCountUsers(), InputParameters.getMaxCountUsers());
        glossary = new HashMap<>();
        initGlossary();


        ArrayList<Human> humans = new ArrayList<>();


        for (int i = 0 ; i < countHumans ; i++){
            String response;
            try {
                response = ApiReader.get();

            }catch (IOException ex){
                break;
            }

            UserPojo upj;
            try {
                upj = JsonParser.getHuman(response);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            humans.add(convertUserPojoToHuman(upj));
        }



        for (int i = 0; i < countHumans - humans.size(); i++) {
            humans.add(fillHuman());
        }
        ExcelCreator.createExcelTable(humans, InputParameters.getListNamesColumn());

        try {
            PdfCreator.createPdfDocument(humans, InputParameters.getListNamesColumn());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    public static Human fillHuman(){
        Human human = new Human();
        human.setSex(Generator.getRandomSex());
        human.setName(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("maleNames") : glossary.get("femaleNames")));
        human.setSurname(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("maleSurnames") : glossary.get("femaleSurnames")));
        human.setPatronymic(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("malePatronymics"): glossary.get("femalePatronymics")));
        human.setCountry(Generator.getRandomStringFromList(glossary.get("countries")));
        human.setRegion(Generator.getRandomStringFromList(glossary.get("regions")));
        human.setTown(Generator.getRandomStringFromList(glossary.get("towns")));
        human.setStreet(Generator.getRandomStringFromList(glossary.get("streets")));
        human.setBirthDay(Generator.getRandomDate(InputParameters.getMinYearOfBirth()));
        human.setAge(Generator.getAgeByDate(human.getBirthDay()));
        human.setInn(Generator.getRandomINN(InputParameters.getRegionINN()));
        human.setMailIndex(Generator.getRand(InputParameters.getStartRangeMailIndex(), InputParameters.getEndRangeMailIndex()));
        human.setNumberHouse(Generator.getRandomNumberHouse(InputParameters.getMaxNumberHouse()));
        human.setNumberFlat(Generator.getRand(1, InputParameters.getMaxNumberFlat()));
        return human;
    }

    public static Human fillHuman(Human human){

        if (human.getSex() == null)
            human.setSex(Generator.getRandomSex());
        if (human.getName() == null)
            human.setName(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("maleNames") : glossary.get("femaleNames")));
        if (human.getSurname() == null)
            human.setSurname(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("maleSurnames") : glossary.get("femaleSurnames")));
        if  (human.getPatronymic() == null)
            human.setPatronymic(Generator.getRandomStringFromList((human.getSex() == SEX.MALE) ? glossary.get("malePatronymics"): glossary.get("femalePatronymics")));
        if (human.getCountry() == null)
            human.setCountry(Generator.getRandomStringFromList(glossary.get("countries")));
        if (human.getRegion() == null)
            human.setRegion(Generator.getRandomStringFromList(glossary.get("regions")));
        if (human.getTown() == null)
            human.setTown(Generator.getRandomStringFromList(glossary.get("towns")));
        if (human.getStreet() == null)
            human.setStreet(Generator.getRandomStringFromList(glossary.get("streets")));
        if (human.getBirthDay() == null)
            human.setBirthDay(Generator.getRandomDate(InputParameters.getMinYearOfBirth()));
        //if (human.getAge() == null)
        human.setAge(Generator.getAgeByDate(human.getBirthDay()));
        if (human.getInn() == null)
            human.setInn(Generator.getRandomINN(InputParameters.getRegionINN()));
        if (human.getMailIndex() == 0)
            human.setMailIndex(Generator.getRand(InputParameters.getStartRangeMailIndex(), InputParameters.getEndRangeMailIndex()));
        if (human.getNumberHouse() == null)
            human.setNumberHouse(Generator.getRandomNumberHouse(InputParameters.getMaxNumberHouse()));
        if (human.getNumberFlat() == 0)
            human.setNumberFlat(Generator.getRand(1, InputParameters.getMaxNumberFlat()));
        return human;
    }

    public static Human convertUserPojoToHuman(UserPojo userPojo){
        Human human = new Human();
        human.setName(userPojo.getFname());
        human.setSurname(userPojo.getLname());
        human.setPatronymic(userPojo.getPatronymic());
        human.setCountry(userPojo.getCounty());
        human.setAge(userPojo.getAge()==null?-1:Integer.valueOf(userPojo.getAge()));
        human.setBirthDay(LocalDate.parse(userPojo.getDate(),DateTimeFormatter.ofPattern("d MMMM yyyy")));
        human.setInn(userPojo.getInn());
        human.setSex(userPojo.getGender().toLowerCase().equals("m")?SEX.MALE:
                userPojo.getGender().toLowerCase().equals("w")?SEX.FEMALE:null);
        human.setMailIndex(Integer.valueOf(userPojo.getPostcode()));
        human.setRegion(userPojo.getRegion());
        human.setTown(userPojo.getCity());
        human.setStreet(userPojo.getStreet());
        human.setNumberHouse(String.valueOf(userPojo.getHouse()));
        human.setNumberFlat(userPojo.getApartment());
        human = fillHuman(human);
        return human;
    }
}
