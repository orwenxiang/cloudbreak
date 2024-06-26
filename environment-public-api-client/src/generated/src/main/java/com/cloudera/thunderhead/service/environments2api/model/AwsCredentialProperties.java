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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The credential properties that closely related to those that have been created on AWS.
 */
@JsonPropertyOrder({
  AwsCredentialProperties.JSON_PROPERTY_ROLE_ARN,
  AwsCredentialProperties.JSON_PROPERTY_GOV_CLOUD
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class AwsCredentialProperties {
  public static final String JSON_PROPERTY_ROLE_ARN = "roleArn";
  private String roleArn;

  public static final String JSON_PROPERTY_GOV_CLOUD = "govCloud";
  private Boolean govCloud;

  public AwsCredentialProperties() { 
  }

  public AwsCredentialProperties roleArn(String roleArn) {
    this.roleArn = roleArn;
    return this;
  }

   /**
   * The AWS role arn for the given credential.
   * @return roleArn
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ROLE_ARN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getRoleArn() {
    return roleArn;
  }


  @JsonProperty(JSON_PROPERTY_ROLE_ARN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRoleArn(String roleArn) {
    this.roleArn = roleArn;
  }


  public AwsCredentialProperties govCloud(Boolean govCloud) {
    this.govCloud = govCloud;
    return this;
  }

   /**
   * Flag that indicates that the given AWS credential is GovCloud specfic.
   * @return govCloud
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GOV_CLOUD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getGovCloud() {
    return govCloud;
  }


  @JsonProperty(JSON_PROPERTY_GOV_CLOUD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGovCloud(Boolean govCloud) {
    this.govCloud = govCloud;
  }


  /**
   * Return true if this AwsCredentialProperties object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AwsCredentialProperties awsCredentialProperties = (AwsCredentialProperties) o;
    return Objects.equals(this.roleArn, awsCredentialProperties.roleArn) &&
        Objects.equals(this.govCloud, awsCredentialProperties.govCloud);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roleArn, govCloud);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AwsCredentialProperties {\n");
    sb.append("    roleArn: ").append(toIndentedString(roleArn)).append("\n");
    sb.append("    govCloud: ").append(toIndentedString(govCloud)).append("\n");
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

    // add `roleArn` to the URL query string
    if (getRoleArn() != null) {
      joiner.add(String.format("%sroleArn%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getRoleArn()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `govCloud` to the URL query string
    if (getGovCloud() != null) {
      joiner.add(String.format("%sgovCloud%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getGovCloud()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    return joiner.toString();
  }
}

