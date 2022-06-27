{% set monitoring = {} %}

{% set node_exporter_exists = salt['file.directory_exists' ]('/opt/node_exporter') %}
{% set blackbox_exporter_exists = salt['file.directory_exists' ]('/opt/blackbox_exporter') %}
{% if salt['pillar.get']('monitoring:enabled') %}
    {% set monitoring_enabled = True %}
{% else %}
    {% set monitoring_enabled = False %}
{% endif %}

{% if grains['init'] == 'upstart' %}
    {% set is_systemd = False %}
{% else %}
    {% set is_systemd = True %}
{% endif %}

{% set use_dev_stack = salt['pillar.get']('monitoring:useDevStack') %}
{% set type = salt['pillar.get']('monitoring:type') %}
{% set cm_username = salt['pillar.get']('monitoring:cmUsername') %}
{% set cm_password = salt['pillar.get']('monitoring:cmPassword') %}
{% set username = salt['pillar.get']('monitoring:username') %}
{% set password = salt['pillar.get']('monitoring:password') %}
{% set token = salt['pillar.get']('monitoring:token') %}
{% set agent_user = salt['pillar.get']('monitoring:agentUser', 'vmagent') %}
{% set agent_port = salt['pillar.get']('monitoring:agentPort', 8429) %}
{% set agent_max_disk_usage = salt['pillar.get']('monitoring:agentMaxDiskUsage', '4GB') %}
{% set retention_min_time = salt['pillar.get']('monitoring:retentionMinTime', '5m') %}
{% set retention_max_time = salt['pillar.get']('monitoring:retentionMaxTime', '4h') %}
{% set wal_truncate_frequency = salt['pillar.get']('monitoring:walTruncateFrequency', '2h') %}
{% set node_exporter_user = salt['pillar.get']('monitoring:nodeExporterUser', 'nodeuser') %}
{% set node_exporter_port = salt['pillar.get']('monitoring:nodeExporterPort', 9100) %}
{% set node_exporter_collectors = salt['pillar.get']('monitoring:nodeExporterCollectors', []) %}
{% set blackbox_exporter_user = salt['pillar.get']('monitoring:blackboxExporterUser', 'blackboxuser') %}
{% set blackbox_exporter_port = salt['pillar.get']('monitoring:blackboxExporterPort', 9115) %}
{% set local_password = salt['pillar.get']('monitoring:localPassword') %}
{% set scrape_interval_seconds = salt['pillar.get']('monitoring:scrapeIntervalSeconds') %}
{% set cm_metrics_exporter_port = salt['pillar.get']('monitoring:cmMetricsExporterPort', 61010) %}

{% set request_signer_enabled = salt['pillar.get']('monitoring:requestSignerEnabled', False) %}
{% set request_signer_port = salt['pillar.get']('monitoring:requestSignerPort', 61095) %}
{% set request_signer_use_token = salt['pillar.get']('monitoring:requestSignerUseToken', True) %}
{% set request_signer_token_validity_min = salt['pillar.get']('monitoring:requestSignerTokenValidityMin', 60) %}
{% set request_signer_user = salt['pillar.get']('monitoring:requestSignerUser', 'signer') %}

{% if salt['pillar.get']('databus:enabled') %}
    {% set databus_enabled = True %}
{% else %}
    {% set databus_enabled = False %}
{% endif %}
{% set databus_access_key_id = salt['pillar.get']('databus:accessKeyId') %}
{% set databus_access_key_secret = salt['pillar.get']('databus:accessKeySecret') %}

{%- if use_dev_stack %}
  {%- if salt['pillar.get']('cloudera-manager:address') %}
    {% set remote_write_url = "http://" + salt['pillar.get']('cloudera-manager:address') + ":9090/api/v1/write" %}
  {%- else %}
    {% set remote_write_url = "http://" + salt['grains.get']('master')[0] + ":9090/api/v1/write" %}
  {%- endif %}
{%- else %}
  {% set remote_write_url = salt['pillar.get']('monitoring:remoteWriteUrl') %}
{%- endif %}

{% if salt['pillar.get']('telemetry:clusterType') == "datalake" %}
  {% set cm_cluster_type = "BASE_CLUSTER" %}
{% else %}
  {% set cm_cluster_type = "COMPUTE_CLUSTER" %}
{% endif %}

{% do monitoring.update({
    "enabled": monitoring_enabled,
    "is_systemd": is_systemd,
    "type": type,
    "username": username,
    "password": password,
    "token": token,
    "remoteWriteUrl": remote_write_url,
    "scrapeIntervalSeconds": scrape_interval_seconds,
    "cmUsername": cm_username,
    "cmPassword": cm_password,
    "cmClusterType": cm_cluster_type,
    "cmMetricsExporterPort": cm_metrics_exporter_port,
    "agentUser": agent_user,
    "agentPort": agent_port,
    "agentMaxDiskUsage": agent_max_disk_usage,
    "retentionMinTime": retention_min_time,
    "retentionMaxTime": retention_max_time,
    "walTruncateFrequency": wal_truncate_frequency,
    "nodeExporterUser": node_exporter_user,
    "nodeExporterPort": node_exporter_port,
    "nodeExporterCollectors": node_exporter_collectors,
    "nodeExporterExists": node_exporter_exists,
    "blackboxExporterUser": blackbox_exporter_user,
    "blackboxExporterPort": blackbox_exporter_port,
    "blackboxExporterExists" : blackbox_exporter_exists,
    "localPassword": local_password,
    "useDevStack": use_dev_stack,
    "requestSignerEnabled" : request_signer_enabled,
    "requestSignerPort" : request_signer_port,
    "requestSignerUseToken" : request_signer_use_token,
    "requestSignerTokenValidityMin" : request_signer_token_validity_min,
    "requestSignerUser" : request_signer_user,
    "databusEnabled": databus_enabled,
    "databusAccessKeyId": databus_access_key_id,
    "databusAccessKeySecret": databus_access_key_secret
}) %}