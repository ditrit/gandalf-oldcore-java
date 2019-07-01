#!/bin/bash

svc_name=$2
svc_cmd=$1

usage() {
  echo Manage a service 
  echo USAGE: $0 deploy|undeploy|start|stop|scale_up|scaledown svc_name
  exit 1
}

[ -z "$svc_name" ] && usage
[ -z "$svc_cmd" ] && usage

ssh root@192.168.3.206 /usr/local/bin/manage_$svc_name.sh $svc_cmd
echo $?

