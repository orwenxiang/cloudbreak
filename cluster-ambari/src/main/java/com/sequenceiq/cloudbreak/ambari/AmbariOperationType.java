package com.sequenceiq.cloudbreak.ambari;

public enum AmbariOperationType {

    SMOKE_TEST_AMBARI_PROGRESS_STATE,
    INSTALL_AMBARI_PROGRESS_STATE,
    START_AMBARI_PROGRESS_STATE,
    STOP_AMBARI_PROGRESS_STATE,
    RESTART_AMBARI_PROGRESS_STATE,
    UPSCALE_AMBARI_PROGRESS_STATE,
    DECOMMISSION_AMBARI_PROGRESS_STATE,
    INIT_SERVICES_AMBARI_PROGRESS_STATE,
    REGENERATE_KERBEROS_KEYTABS_AMBARI_PROGRESS_STATE,
    INSTALL_SERVICES_AMBARI_PROGRESS_STATE,
    START_SERVICES_AMBARI_PROGRESS_STATE,
    STOP_SERVICES_AMBARI_PROGRESS_STATE,
    DECOMMISSION_SERVICES_AMBARI_PROGRESS_STATE,
    ENABLE_SERVICES_AMBARI_PROGRESS_STATE,
    START_OPERATION_STATE,
    PREPARE_DEKERBERIZING,
    DISABLE_KERBEROS_STATE
}
