{% set configure_remote_db = salt['pillar.get']('postgres:configure_remote_db', 'None') %}

include:
  - postgresql.upgrade

backup_postgresql_db:
  cmd.run:
{%- if 'None' != configure_remote_db %}
    - name: /opt/salt/scripts/backup_db.sh -h {{salt['pillar.get']('postgres:clouderamanager:remote_db_url')}} -p {{salt['pillar.get']('postgres:clouderamanager:remote_db_port')}} -u {{salt['pillar.get']('postgres:clouderamanager:remote_admin')}}
{%- else %}
    - name: /opt/salt/scripts/backup_db.sh -h localhost -p 5432 -u postgres
{%- endif %}
    - require:
        - sls: postgresql.upgrade
