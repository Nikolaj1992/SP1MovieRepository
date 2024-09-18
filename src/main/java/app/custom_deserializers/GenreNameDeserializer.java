package app.custom_deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenreNameDeserializer extends JsonDeserializer<List<String>> { //TODO: figure out the magic behind this
    @Override
    public List<String> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<String> genreNames = new ArrayList<>();

        // Loop through each genre and extract the name
        if (node.isArray()) {
            for (JsonNode genreNode : node) {
                String genreName = genreNode.get("name").asText();
                genreNames.add(genreName);
            }
        }

        return genreNames;
    }
}
