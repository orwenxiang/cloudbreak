{%- from 'telemetry/settings.sls' import telemetry with context %}

create_test_cloud_storage_file:
  cmd.run:
    - name: echo testcloudstorage > /tmp/.test_cloud_storage_upload.txt
    - failhard: True

{% if "s3" in telemetry.testCloudStorageUploadParams %}
test_s3_put:
  cmd.run:
    - name: "cdp-telemetry storage {{ telemetry.testCloudStorageUploadParams }}"
    - failhard: True
{% elif "abfs" in telemetry.testCloudStorageUploadParams %}
test_abfs_put:
  cmd.run:
    - name: "cdp-telemetry storage {{ telemetry.testCloudStorageUploadParams }}"
    - failhard: True
{% elif "gcs" in telemetry.testCloudStorageUploadParams %}
test_gcs_put:
  cmd.run:
    - name: "cdp-telemetry storage {{ telemetry.testCloudStorageUploadParams }}"
    - failhard: True
{% endif %}

delete_test_cloud_storage_file:
  cmd.run:
    - name: rm -rf /tmp/.test_cloud_storage_upload.txt
