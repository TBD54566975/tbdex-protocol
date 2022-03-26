package io.tbd.tbdex.did_sdk.presentation_exchange;

import com.google.gson.*;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import org.everit.json.schema.Schema;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InputDescriptorConstraintField {
  private List<JsonPath> path;
  private String id;
  private String purpose;
  private Schema filter;

  InputDescriptorConstraintField(JsonPath ... path) {
    this.path = List.of(path);
  }

  public static class Builder {
    InputDescriptorConstraintField instance;

    public Builder(JsonPath ... path) {
      this.instance = new InputDescriptorConstraintField(path);
    }

    public Builder id(String id) {
      this.instance.id = id;

      return this;
    }

    public Builder purpose(String purpose) {
      this.instance.purpose = purpose;

      return this;
    }

    public Builder filter(Schema filter) {
      this.instance.filter = filter;

      return this;
    }

    public InputDescriptorConstraintField build() {
      return this.instance;
    }
  }

  public static class Deserializer implements JsonDeserializer<InputDescriptorConstraintField> {

    @Override
    public InputDescriptorConstraintField deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {

      JsonObject constraintFieldJson = json.getAsJsonObject();
      JsonArray pathsJson = constraintFieldJson.getAsJsonArray("path");

      List<JsonPath> jsonPaths = new ArrayList<>();
      Predicate empty[] = {};

      for (JsonElement pathJson : pathsJson) {
        JsonPath jsonPath = JsonPath.compile(pathJson.getAsString(), empty);
        jsonPaths.add(jsonPath);
      }

      return null;

    }
  }
}
