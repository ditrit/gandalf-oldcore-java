#!/bin/bash

svc_name=$1
svc_version=$2
tmp_sh=/tmp/$1_$2.script
svc_sh=/usr/local/bin/manage_$1.sh
tmp_vars=/tmp/$1_$2.vars
lxc_host=192.168.3.205

usage() {
  echo Register a service 
  echo USAGE: $0 service_name service_version
  exit 1
}

[ -z "$svc_name" ] && usage
[ -z "$svc_version" ] && usage

svc_def=http://artifact-service.service.gandalf:8080/download/${svc_name}_${svc_version}.ini
wget -O $tmp_vars $svc_def

source $tmp_vars
[ -z "$NAME" ] && echo ERROR: Bad or No definition of the service $svc_name in version $svc_version && exit 1
artifact_tar=${ARTIFACT}_${VERSION}.tar.gz
svc_tar=http://artifact-service.service.gandalf:8080/download/$artifact_tar
tmp_tar=/tmp/$artifact_tar
wget -O $tmp_tar $svc_tar

echo "#!/bin/bash" > $tmp_sh
cat $tmp_vars >> $tmp_sh
cat /opt/orchestrator-service/classes/script/service.script >> $tmp_sh
chmod a+x $tmp_sh
ssh $lxc_host rm $svc_sh
ssh $lxc_host mkdir -p /tmp/$svc_name
scp $tmp_sh root@$lxc_host:$svc_sh
scp $tmp_tar root@$lxc_host:/tmp/$svc_name/$artifact_tar
