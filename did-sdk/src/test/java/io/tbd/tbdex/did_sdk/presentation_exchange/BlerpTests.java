package io.tbd.tbdex.did_sdk.presentation_exchange;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import org.junit.jupiter.api.Test;

public class BlerpTests {

  @Test
  public void tryThings() {
    Predicate empty[] = {};
    JsonPath path = JsonPath.compile("$.termsOfUse.type", empty);

  }
}
