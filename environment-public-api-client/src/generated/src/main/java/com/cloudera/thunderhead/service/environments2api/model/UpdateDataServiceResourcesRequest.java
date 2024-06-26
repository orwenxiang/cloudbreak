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
import com.cloudera.thunderhead.service.environments2api.model.DataServicesRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Update Data Services parameters request of the environment.
 */
@JsonPropertyOrder({
  UpdateDataServiceResourcesRequest.JSON_PROPERTY_ENVIRONMENT,
  UpdateDataServiceResourcesRequest.JSON_PROPERTY_DATA_SERVICES
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class UpdateDataServiceResourcesRequest {
  public static final String JSON_PROPERTY_ENVIRONMENT = "environment";
  private String environment;

  public static final String JSON_PROPERTY_DATA_SERVICES = "dataServices";
  private DataServicesRequest dataServices;

  public UpdateDataServiceResourcesRequest() { 
  }

  public UpdateDataServiceResourcesRequest environment(String environment) {
    this.environment = environment;
    return this;
  }

   /**
   * The name or CRN of the environment.
   * @return environment
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ENVIRONMENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getEnvironment() {
    return environment;
  }


  @JsonProperty(JSON_PROPERTY_ENVIRONMENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEnvironment(String environment) {
    this.environment = environment;
  }


  public UpdateDataServiceResourcesRequest dataServices(DataServicesRequest dataServices) {
    this.dataServices = dataServices;
    return this;
  }

   /**
   * Get dataServices
   * @return dataServices
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DATA_SERVICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public DataServicesRequest getDataServices() {
    return dataServices;
  }


  @JsonProperty(JSON_PROPERTY_DATA_SERVICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDataServices(DataServicesRequest dataServices) {
    this.dataServices = dataServices;
  }


  /**
   * Return true if this UpdateDataServiceResourcesRequest object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateDataServiceResourcesRequest updateDataServiceResourcesRequest = (UpdateDataServiceResourcesRequest) o;
    return Objects.equals(this.environment, updateDataServiceResourcesRequest.environment) &&
        Objects.equals(this.dataServices, updateDataServiceResourcesRequest.dataServices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(environment, dataServices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateDataServiceResourcesRequest {\n");
    sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
    sb.append("    dataServices: ").append(toIndentedString(dataServices)).append("\n");
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

    // add `environment` to the URL query string
    if (getEnvironment() != null) {
      joiner.add(String.format("%senvironment%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getEnvironment()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `dataServices` to the URL query string
    if (getDataServices() != null) {
      joiner.add(getDataServices().toUrlQueryString(prefix + "dataServices" + suffix));
    }

    return joiner.toString();
  }
}

