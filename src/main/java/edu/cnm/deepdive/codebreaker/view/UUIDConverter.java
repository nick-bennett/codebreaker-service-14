package edu.cnm.deepdive.codebreaker.view;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDConverter implements Converter<String, UUID> {

  private final UUIDStringifier stringifier;

  @Autowired
  public UUIDConverter(UUIDStringifier stringifier) {
    this.stringifier = stringifier;
  }

  @Override
  public UUID convert(String source) {
    return stringifier.fromString(source);
  }

}
