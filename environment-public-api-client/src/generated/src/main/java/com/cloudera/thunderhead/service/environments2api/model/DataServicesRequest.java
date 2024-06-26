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
import com.cloudera.thunderhead.service.environments2api.model.AzureDataServicesParametersRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Data Services parameters request of the environment.
 */
@JsonPropertyOrder({
  DataServicesRequest.JSON_PROPERTY_AZURE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class DataServicesRequest {
  public static final String JSON_PROPERTY_AZURE = "azure";
  private AzureDataServicesParametersRequest azure;

  public DataServicesRequest() { 
  }

  public DataServicesRequest azure(AzureDataServicesParametersRequest azure) {
    this.azure = azure;
    return this;
  }

   /**
   * Get azure
   * @return azure
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_AZURE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public AzureDataServicesParametersRequest getAzure() {
    return azure;
  }


  @JsonProperty(JSON_PROPERTY_AZURE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAzure(AzureDataServicesParametersRequest azure) {
    this.azure = azure;
  }


  /**
   * Return true if this DataServicesRequest object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataServicesRequest dataServicesRequest = (DataServicesRequest) o;
    return Objects.equals(this.azure, dataServicesRequest.azure);
  }

  @Override
  public int hashCode() {
    return Objects.hash(azure);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataServicesRequest {\n");
    sb.append("    azure: ").append(toIndentedString(azure)).append("\n");
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

    // add `azure` to the URL query string
    if (getAzure() != null) {
      joiner.add(getAzure().toUrlQueryString(prefix + "azure" + suffix));
    }

    return joiner.toString();
  }
}

