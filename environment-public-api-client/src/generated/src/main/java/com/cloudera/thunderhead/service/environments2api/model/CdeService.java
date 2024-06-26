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
import com.cloudera.thunderhead.service.environments2api.model.CdeVc;
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
 * The CDE service.
 */
@JsonPropertyOrder({
  CdeService.JSON_PROPERTY_NAME,
  CdeService.JSON_PROPERTY_CLUSTER_ID,
  CdeService.JSON_PROPERTY_ENVIRONMENT_NAME,
  CdeService.JSON_PROPERTY_STATUS,
  CdeService.JSON_PROPERTY_CDE_VCS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class CdeService {
  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_CLUSTER_ID = "clusterId";
  private String clusterId;

  public static final String JSON_PROPERTY_ENVIRONMENT_NAME = "environmentName";
  private String environmentName;

  public static final String JSON_PROPERTY_STATUS = "status";
  private String status;

  public static final String JSON_PROPERTY_CDE_VCS = "cdeVcs";
  private List<CdeVc> cdeVcs = new ArrayList<>();

  public CdeService() { 
  }

  public CdeService name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the CDE service
   * @return name
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }


  public CdeService clusterId(String clusterId) {
    this.clusterId = clusterId;
    return this;
  }

   /**
   * Cluster Id of the CDE Service.
   * @return clusterId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CLUSTER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getClusterId() {
    return clusterId;
  }


  @JsonProperty(JSON_PROPERTY_CLUSTER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }


  public CdeService environmentName(String environmentName) {
    this.environmentName = environmentName;
    return this;
  }

   /**
   * The name of the service&#39;s environment.
   * @return environmentName
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ENVIRONMENT_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getEnvironmentName() {
    return environmentName;
  }


  @JsonProperty(JSON_PROPERTY_ENVIRONMENT_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEnvironmentName(String environmentName) {
    this.environmentName = environmentName;
  }


  public CdeService status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the CDE service.
   * @return status
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getStatus() {
    return status;
  }


  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStatus(String status) {
    this.status = status;
  }


  public CdeService cdeVcs(List<CdeVc> cdeVcs) {
    this.cdeVcs = cdeVcs;
    return this;
  }

  public CdeService addCdeVcsItem(CdeVc cdeVcsItem) {
    if (this.cdeVcs == null) {
      this.cdeVcs = new ArrayList<>();
    }
    this.cdeVcs.add(cdeVcsItem);
    return this;
  }

   /**
   * List of virtual clusters associated with the CDE service.
   * @return cdeVcs
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CDE_VCS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<CdeVc> getCdeVcs() {
    return cdeVcs;
  }


  @JsonProperty(JSON_PROPERTY_CDE_VCS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCdeVcs(List<CdeVc> cdeVcs) {
    this.cdeVcs = cdeVcs;
  }


  /**
   * Return true if this CdeService object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CdeService cdeService = (CdeService) o;
    return Objects.equals(this.name, cdeService.name) &&
        Objects.equals(this.clusterId, cdeService.clusterId) &&
        Objects.equals(this.environmentName, cdeService.environmentName) &&
        Objects.equals(this.status, cdeService.status) &&
        Objects.equals(this.cdeVcs, cdeService.cdeVcs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, clusterId, environmentName, status, cdeVcs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CdeService {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    clusterId: ").append(toIndentedString(clusterId)).append("\n");
    sb.append("    environmentName: ").append(toIndentedString(environmentName)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    cdeVcs: ").append(toIndentedString(cdeVcs)).append("\n");
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

    // add `name` to the URL query string
    if (getName() != null) {
      joiner.add(String.format("%sname%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `clusterId` to the URL query string
    if (getClusterId() != null) {
      joiner.add(String.format("%sclusterId%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getClusterId()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `environmentName` to the URL query string
    if (getEnvironmentName() != null) {
      joiner.add(String.format("%senvironmentName%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getEnvironmentName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `status` to the URL query string
    if (getStatus() != null) {
      joiner.add(String.format("%sstatus%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getStatus()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `cdeVcs` to the URL query string
    if (getCdeVcs() != null) {
      for (int i = 0; i < getCdeVcs().size(); i++) {
        if (getCdeVcs().get(i) != null) {
          joiner.add(getCdeVcs().get(i).toUrlQueryString(String.format("%scdeVcs%s%s", prefix, suffix,
          "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}

