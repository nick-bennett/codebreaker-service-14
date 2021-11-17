package edu.cnm.deepdive.codebreaker.view;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UserSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User user, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {

  }

}
