package HumanCreator.model;

import HumanCreator.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Human {
    @Getter
    private String name;
    @Getter
    private String surname;

    @Getter
    private String patronymic;

    @Getter
    private String country;

    @Getter
    private int age;

    @Getter
    private LocalDate birthDay;

    @Getter
    private String inn;

    @Getter
    private Gender gender;

    @Getter
    private int mailIndex;

    @Getter
    private String region;

    @Getter
    private String town;

    @Getter
    private String street;

    @Getter
    private String numberHouse;

    @Getter
    private int numberFlat;

    private Human() {
    }

    public static class Builder {
        final Human human;

        public Builder() {
            human = new Human();
        }

        public Builder setName(String name) {
            human.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            human.surname = surname;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            human.patronymic = patronymic;
            return this;
        }

        public Builder setCountry(String country) {
            human.country = country;
            return this;
        }

        public Builder setBirthday(LocalDate birthday) {
            human.birthDay = birthday;
            return this;
        }

        public Builder setAge(int age) {
            human.age = age;
            return this;
        }

        public Builder setInn(String inn) {
            human.inn = inn;
            return this;
        }

        public Builder setGender(Gender gender) {
            human.gender = gender;
            return this;
        }

        public Builder setMailIndex(int mailIndex) {
            human.mailIndex = mailIndex;
            return this;
        }

        public Builder setRegion(String region) {
            human.region = region;
            return this;
        }

        public Builder setTown(String town) {
            human.town = town;
            return this;
        }

        public Builder setStreet(String street) {
            human.street = street;
            return this;
        }

        public Builder setNumberHouse(String numberHouse) {
            human.numberHouse = numberHouse;
            return this;
        }

        public Builder setNumberFlat(int numberFlat) {
            human.numberFlat = numberFlat;
            return this;
        }

        public Human build() {
            return human;
        }
    }
}

