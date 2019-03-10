package HumanCreator.generators.remoteApiGenerator;

import HumanCreator.model.UserPojo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonParser {

    public static UserPojo getHuman(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, UserPojo.class);
    }
}