/*
 * FreeIPA Healthcheck API
 * API of the FreeIPA healtcheck service
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.sequenceiq.freeipa.healthagent.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sequenceiq.freeipa.healthagent.model.CheckResult;
import com.sequenceiq.freeipa.healthagent.model.PluginCheckEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * ClusterCheckResult
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-05-07T16:30:36.099586-05:00[America/Chicago]")
public class ClusterCheckResult {
  @SerializedName("host")
  private String host = null;

  /**
   * Overall status of the cluster.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    HEALTHY("HEALTHY"),
    UNHEALTHY("UNHEALTHY"),
    DEGRADED("DEGRADED"),
    ERROR("ERROR");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("replicas")
  private List<CheckResult> replicas = null;

  @SerializedName("plugin_stat")
  private List<PluginCheckEntry> pluginStat = new ArrayList<PluginCheckEntry>();

  public ClusterCheckResult host(String host) {
    this.host = host;
    return this;
  }

   /**
   * The host the check is running on.
   * @return host
  **/
  @Schema(required = true, description = "The host the check is running on.")
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public ClusterCheckResult status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Overall status of the cluster.
   * @return status
  **/
  @Schema(required = true, description = "Overall status of the cluster.")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ClusterCheckResult replicas(List<CheckResult> replicas) {
    this.replicas = replicas;
    return this;
  }

  public ClusterCheckResult addReplicasItem(CheckResult replicasItem) {
    if (this.replicas == null) {
      this.replicas = new ArrayList<CheckResult>();
    }
    this.replicas.add(replicasItem);
    return this;
  }

   /**
   * Array of Replica checks.
   * @return replicas
  **/
  @Schema(description = "Array of Replica checks.")
  public List<CheckResult> getReplicas() {
    return replicas;
  }

  public void setReplicas(List<CheckResult> replicas) {
    this.replicas = replicas;
  }

  public ClusterCheckResult pluginStat(List<PluginCheckEntry> pluginStat) {
    this.pluginStat = pluginStat;
    return this;
  }

  public ClusterCheckResult addPluginStatItem(PluginCheckEntry pluginStatItem) {
    this.pluginStat.add(pluginStatItem);
    return this;
  }

   /**
   * Array of Plugin Check Entries.
   * @return pluginStat
  **/
  @Schema(required = true, description = "Array of Plugin Check Entries.")
  public List<PluginCheckEntry> getPluginStat() {
    return pluginStat;
  }

  public void setPluginStat(List<PluginCheckEntry> pluginStat) {
    this.pluginStat = pluginStat;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClusterCheckResult clusterCheckResult = (ClusterCheckResult) o;
    return Objects.equals(this.host, clusterCheckResult.host) &&
        Objects.equals(this.status, clusterCheckResult.status) &&
        Objects.equals(this.replicas, clusterCheckResult.replicas) &&
        Objects.equals(this.pluginStat, clusterCheckResult.pluginStat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, status, replicas, pluginStat);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClusterCheckResult {\n");
    
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    replicas: ").append(toIndentedString(replicas)).append("\n");
    sb.append("    pluginStat: ").append(toIndentedString(pluginStat)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
