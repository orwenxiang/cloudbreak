/*
 * Cloudera Environments Service
 * Cloudera Environments Service is a web service that manages cloud provider access.
 *
 * The version of the OpenAPI document: __API_VERSION__
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.cloudera.thunderhead.service.environments2api.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import com.cloudera.thunderhead.service.environments2api.model.AnonymizationRuleRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Request object for testing text input against provided account telemetry anonymization rules.
 */
@JsonPropertyOrder({
  TestAccountTelemetryRulesRequest.JSON_PROPERTY_TEST_INPUT,
  TestAccountTelemetryRulesRequest.JSON_PROPERTY_RULES
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class TestAccountTelemetryRulesRequest {
  public static final String JSON_PROPERTY_TEST_INPUT = "testInput";
  private String testInput;

  public static final String JSON_PROPERTY_RULES = "rules";
  private List<AnonymizationRuleRequest> rules = new ArrayList<>();

  public TestAccountTelemetryRulesRequest() { 
  }

  public TestAccountTelemetryRulesRequest testInput(String testInput) {
    this.testInput = testInput;
    return this;
  }

   /**
   * Text input that will be tested against the provided account telemetry anonymization rules.
   * @return testInput
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TEST_INPUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTestInput() {
    return testInput;
  }


  @JsonProperty(JSON_PROPERTY_TEST_INPUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTestInput(String testInput) {
    this.testInput = testInput;
  }


  public TestAccountTelemetryRulesRequest rules(List<AnonymizationRuleRequest> rules) {
    this.rules = rules;
    return this;
  }

  public TestAccountTelemetryRulesRequest addRulesItem(AnonymizationRuleRequest rulesItem) {
    if (this.rules == null) {
      this.rules = new ArrayList<>();
    }
    this.rules.add(rulesItem);
    return this;
  }

   /**
   * List of anonymization rules that are applied on logs that are shipped to Cloudera
   * @return rules
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RULES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<AnonymizationRuleRequest> getRules() {
    return rules;
  }


  @JsonProperty(JSON_PROPERTY_RULES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRules(List<AnonymizationRuleRequest> rules) {
    this.rules = rules;
  }


  /**
   * Return true if this TestAccountTelemetryRulesRequest object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestAccountTelemetryRulesRequest testAccountTelemetryRulesRequest = (TestAccountTelemetryRulesRequest) o;
    return Objects.equals(this.testInput, testAccountTelemetryRulesRequest.testInput) &&
        Objects.equals(this.rules, testAccountTelemetryRulesRequest.rules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(testInput, rules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestAccountTelemetryRulesRequest {\n");
    sb.append("    testInput: ").append(toIndentedString(testInput)).append("\n");
    sb.append("    rules: ").append(toIndentedString(rules)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `testInput` to the URL query string
    if (getTestInput() != null) {
      joiner.add(String.format("%stestInput%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getTestInput()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `rules` to the URL query string
    if (getRules() != null) {
      for (int i = 0; i < getRules().size(); i++) {
        if (getRules().get(i) != null) {
          joiner.add(getRules().get(i).toUrlQueryString(String.format("%srules%s%s", prefix, suffix,
          "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}

