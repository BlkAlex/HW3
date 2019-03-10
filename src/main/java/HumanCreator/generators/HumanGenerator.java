package HumanCreator.generators;

import HumanCreator.InputParameters;
import HumanCreator.enums.Gender;
import HumanCreator.model.Human;
import HumanCreator.model.UserPojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HumanGenerator {
    public static Human getHuman(){
        Gender gender = Generator.getRandomGender();
        LocalDate birthdayDate = Generator.getRandomDate(InputParameters.MIN_YEAR_OF_BIRTH);
        Human human = new Human.Builder()
                .setGender(gender)
                .setName(Generator.getRandomStringFromList((gender == Gender.MALE) ? Generator.KeysGlossary.MALE_NAMES : Generator.KeysGlossary.FEMALE_NAMES))
                .setSurname(Generator.getRandomStringFromList((gender == Gender.MALE) ? Generator.KeysGlossary.MALE_SURNAMES : Generator.KeysGlossary.FEMALE_SURNAMES))
                .setPatronymic(Generator.getRandomStringFromList((gender == Gender.MALE) ? Generator.KeysGlossary.MALE_PATRONYMIC : Generator.KeysGlossary.FEMALE_PATRONYMIC))
                .setCountry(Generator.getRandomStringFromList(Generator.KeysGlossary.COUNTRIES))
                .setRegion(Generator.getRandomStringFromList(Generator.KeysGlossary.REGIONS))
                .setTown(Generator.getRandomStringFromList(Generator.KeysGlossary.TOWNS))
                .setStreet(Generator.getRandomStringFromList(Generator.KeysGlossary.STREETS))
                .setBirthday(birthdayDate)
                .setAge(Generator.getAgeByDate(birthdayDate))
                .setInn(Generator.getRandomINN(InputParameters.REGION_INN))
                .setMailIndex(Generator.getRand(InputParameters.START_RANGE_MAIL_INDEX, InputParameters.END_RANGE_MAIL_INDEX))
                .setNumberHouse(Generator.getRandomNumberHouse(InputParameters.MAX_NUMBER_HOUSE))
                .setNumberFlat(Generator.getRand(1, InputParameters.MAX_NUMBER_FLAT))
                .build();
        return human;
    }
    public static Human getHumanFromUserPojo(UserPojo userPojo){
        Human localGeneratedHuman = getHuman();
        Gender gender = userPojo.getGender().toLowerCase().equals("m")? Gender.MALE:
                userPojo.getGender().toLowerCase().equals("w")? Gender.FEMALE:null;
        if (gender == null)
            gender = localGeneratedHuman.getGender();
        Human human = new Human.Builder()
                .setGender(gender)
                .setName(userPojo.getFname() == null ? localGeneratedHuman.getName() : userPojo.getFname())
                .setSurname(userPojo.getLname() == null? localGeneratedHuman.getSurname() : userPojo.getLname())
                .setPatronymic(userPojo.getPatronymic() == null ? localGeneratedHuman.getPatronymic() : userPojo.getPatronymic())
                .setCountry(userPojo.getCounty() == null ? localGeneratedHuman.getCountry() : userPojo.getCounty())
                .setRegion(userPojo.getRegion() == null ? localGeneratedHuman.getRegion() : userPojo.getRegion())
                .setTown(userPojo.getCity() == null ? localGeneratedHuman.getTown() : userPojo.getCity())
                .setStreet(userPojo.getStreet() == null ? localGeneratedHuman.getStreet() : userPojo.getStreet())
                .setBirthday(userPojo.getDate() == null ? localGeneratedHuman.getBirthDay() :LocalDate.parse(userPojo.getDate(), DateTimeFormatter.ofPattern("d MMMM yyyy")))
                .setAge(userPojo.getAge() == null ? localGeneratedHuman.getAge() : Integer.valueOf(userPojo.getAge()))
                .setInn(userPojo.getInn() == null ? localGeneratedHuman.getInn() : userPojo.getInn())
                .setMailIndex(
                        (userPojo.getPostcode() == null || (Integer.valueOf(userPojo.getPostcode()) > InputParameters.END_RANGE_MAIL_INDEX || Integer.valueOf(userPojo.getPostcode()) < InputParameters.START_RANGE_MAIL_INDEX))
                                ? localGeneratedHuman.getMailIndex() : Integer.valueOf(userPojo.getPostcode())
                )
                .setNumberHouse(userPojo.getHouse() == 0 ? localGeneratedHuman.getNumberHouse() : String.valueOf(userPojo.getHouse()))
                .setNumberFlat(userPojo.getApartment() == 0 ? localGeneratedHuman.getNumberFlat() : userPojo.getApartment() )
                .build();
        return human;
    }
}
