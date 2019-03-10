package HumanCreator.generators.remoteApiGenerator;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import HumanCreator.model.UserPojo;

public class JsonParser {

    public static UserPojo getHuman(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response,UserPojo.class);
    }
}