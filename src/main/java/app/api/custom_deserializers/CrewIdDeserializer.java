package app.api.custom_deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrewIdDeserializer extends JsonDeserializer<List<Integer>> { //TODO: figure out the magic behind this
    @Override
    public List<Integer> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<Integer> ids = new ArrayList<>();

        // Traverse the "results" array and extract "id"
        if (node.isArray()) {
            for (JsonNode crewNode : node) {
                int id = crewNode.get("id").asInt();
                ids.add(id);
            }
        }

        return ids;
    }
}
