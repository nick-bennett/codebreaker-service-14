package edu.cnm.deepdive.codebreaker.view;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import edu.cnm.deepdive.codebreaker.service.UUIDStringifier;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UUIDJsonDeserializer extends JsonDeserializer<UUID> {

  private final UUIDStringifier stringifier;

  @Autowired
  public UUIDJsonDeserializer(UUIDStringifier stringifier) {
    this.stringifier = stringifier;
  }

  @Override
  public UUID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    return stringifier.fromString(jsonParser.readValueAs(String.class));
  }

}
