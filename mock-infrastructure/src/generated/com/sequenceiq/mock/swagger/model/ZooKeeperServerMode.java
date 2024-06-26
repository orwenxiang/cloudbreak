package com.sequenceiq.mock.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The state of the Zookeeper server.
 */
public enum ZooKeeperServerMode {

  STANDALONE("STANDALONE"),

  REPLICATED_FOLLOWER("REPLICATED_FOLLOWER"),

  REPLICATED_LEADER("REPLICATED_LEADER"),

  REPLICATED_LEADER_ELECTION("REPLICATED_LEADER_ELECTION"),

  REPLICATED_OBSERVER("REPLICATED_OBSERVER"),

  UNKNOWN("UNKNOWN");

  private String value;

  ZooKeeperServerMode(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ZooKeeperServerMode fromValue(String text) {
    for (ZooKeeperServerMode b : ZooKeeperServerMode.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

