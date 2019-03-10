package HumanCreator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lname",
        "fname",
        "patronymic",
        "gender",
        "date",
        "age",
        "postcode",
        "city",
        "street",
        "house",
        "apartment",
        "phone",
        "login",
        "password",
        "color",
        "userpic",
        "inn",
        "region",
        "country"
})
public class UserPojo {

    @JsonProperty("lname")
    private String lname;
    @JsonProperty("fname")
    private String fname;
    @JsonProperty("patronymic")
    private String patronymic;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date")
    private String age;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("street")
    private String street;
    @JsonProperty("house")
    private int house;
    @JsonProperty("apartment")
    private int apartment;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
    @JsonProperty("color")
    private String color;
    @JsonProperty("userpic")
    private String userpic;

    @JsonProperty("inn")
    private String inn;
    @JsonProperty("region")
    private String region;
    @JsonProperty("country")
    private String country;


    @JsonProperty("lname")
    public String getLname() {
        return lname;
    }

    @JsonProperty("lname")
    public void setLname(String lname) {
        this.lname = lname;
    }

    @JsonProperty("fname")
    public String getFname() {
        return fname;
    }

    @JsonProperty("fname")
    public void setFname(String fname) {
        this.fname = fname;
    }

    @JsonProperty("patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    @JsonProperty("patronymic")
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("age")
    public void setAge(String age) {
        this.age = date;
    }

    @JsonProperty("age")
    public String getAge() {
        return age;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }


    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("house")
    public int getHouse() {
        return house;
    }

    @JsonProperty("house")
    public void setHouse(int house) {
        this.house = house;
    }

    @JsonProperty("apartment")
    public int getApartment() {
        return apartment;
    }

    @JsonProperty("apartment")
    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("userpic")
    public String getUserpic() {
        return userpic;
    }

    @JsonProperty("userpic")
    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }


    @JsonProperty("inn")
    public String getInn() {
        return inn;
    }

    @JsonProperty("inn")
    public void setInn(String inn) {
        this.inn = inn;
    }

    @JsonProperty("county")
    public String getCounty() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("userpic")
    public void setRegion(String region) {
        this.region = region;
    }
}