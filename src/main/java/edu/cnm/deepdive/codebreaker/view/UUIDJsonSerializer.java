package edu.cnm.deepdive.codebreaker.view;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UUIDJsonSerializer extends JsonSerializer<UUID> {

  private final UUIDStringifier stringifier;

  @Autowired
  public UUIDJsonSerializer(UUIDStringifier stringifier) {
    this.stringifier = stringifier;
  }

  @Override
  public void serialize(UUID uuid, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(stringifier.toString(uuid));
  }
}
