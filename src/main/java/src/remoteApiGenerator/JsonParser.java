package src.remoteApiGenerator;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
public class JsonParser {

    public static UserPojo getHuman(String response) throws IOException {

        //String testJsonString = "{\"lname\":\"Шмидт\",\"fname\":\"Федор\",\"patronymic\":\"Данилович\",\"gender\":\"m\",\"date\":\"15 апреля 1985\",\"postcode\":\"452995\",\"city\":\"Челно-Вершины\",\"street\":\"Трофимова\",\"house\":27,\"apartment\":79,\"phone\":\"8-944-571-24-60\",\"login\":\"ShmidtFedor249\",\"password\":\"pR3XIwM7gCpK\",\"color\":\"Чёрный\",\"userpic\":\"https://randus.org/avatars/m/myAvatar14.png\"}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response,UserPojo.class);
    }
}