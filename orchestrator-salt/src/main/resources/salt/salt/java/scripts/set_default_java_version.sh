#!/usr/bin/env bash

set -e

if [ -n "$TARGET_JAVA_VERSION" ]; then
  CURRENT_JAVA_VERSION=$(java -version 2>&1 | grep -oP "version [^0-9]?(1\.)?\K\d+" || true)

  if [ $CURRENT_JAVA_VERSION -eq $TARGET_JAVA_VERSION ]; then
    echo "Current java version equals to the target version."
    exit 0;
  elif [ $CURRENT_JAVA_VERSION -eq 8 ] && [ $TARGET_JAVA_VERSION -eq 11 ]; then
    echo "Change java version from $CURRENT_JAVA_VERSION to $TARGET_JAVA_VERSION"

    alternatives --set java java-11-openjdk.x86_64
    ln -sfn /etc/alternatives/java_sdk_11 /usr/lib/jvm/java
    mkdir -p /etc/alternatives/java_sdk_11/jre/lib/security
    ln -sfn /etc/alternatives/java_sdk_11/conf/security/java.security /etc/alternatives/java_sdk_11/jre/lib/security/java.security
    ln -sfn /etc/pki/java/cacerts /etc/alternatives/java_sdk_11/jre/lib/security/cacerts

    #Regenerate the resolv.conf
    source /etc/dhcp/dhclient-enter-hooks
    generate_resolv_conf

    #The domain should be existed in the resolv.conf to avoid DNS resolution error.
    if ! grep -Fxq "domain" /etc/resolv.conf; then
      echo "Failed to set the domain in resolve.conf"
      exit 1
    fi
  else
    echo "Changing java version from $CURRENT_JAVA_VERSION to $TARGET_JAVA_VERSION is not supported"
    exit 1
  fi

  #Finally validate the current java version is the target one
  PREVIOUS_CURRENT_JAVA_VERSION=$CURRENT_JAVA_VERSION
  CURRENT_JAVA_VERSION=$(java -version 2>&1 | grep -oP "version [^0-9]?(1\.)?\K\d+" || true)
  if [ $CURRENT_JAVA_VERSION -eq $TARGET_JAVA_VERSION ]; then
    echo "Java version successfully changed from $PREVIOUS_CURRENT_JAVA_VERSION to $TARGET_JAVA_VERSION."
  else
    echo "Failed to change java version from $PREVIOUS_CURRENT_JAVA_VERSION to $TARGET_JAVA_VERSION. Java version is $CURRENT_JAVA_VERSION"
    exit 1
  fi
fi
