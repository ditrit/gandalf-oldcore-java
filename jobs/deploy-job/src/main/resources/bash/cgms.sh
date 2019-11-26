#!/bin/bash

usage() {
	echo cgms : Create  a new Gandalf Micro-Service 
	echo USAGE : lxc_name service_name jar_file
	exit
}

lxc_name=$1
[ -z "$lxc_name" ] && usage
svc_name=$2
[ -z "$svc_name" ] && usage
jar=$2
[ -z "$jar" ] && usage

lxc stop $1
lxc delete $1
lxc launch gandalfimg $1

cat > $1.sh <<EOF
#!/bin/bash

cat > $2.hcl <<EOB
{
  "service": {
    "id": "$2",
    "name": "$2",
  }
}
EOB

cat > /usr/local/bin/$2 << EOB
#!/bin/bash

java -jar /opt/$2/*.jar
EOB

chmod a+rx /usr/local/bin/$2
chmod a+r /opt/$2

cat > /etc/systemd/system/$2-gandalf.service << EOB
[Unit]
Description=My Webapp Java REST Service
[Service]
User=root
# The configuration file application.com.ditrit.gandalf.gandalfjava.com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.core.workercore.controller.properties should be here:

#change this to your workspace
WorkingDirectory=/root/

#path to executable. 
ExecStart=/usr/local/bin/$2

SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
EOB

sleep 10
consul services register $2.hcl

systemctl enable $2-gandalf
systemctl start $2-gandalf

EOF

lxc file push $3 $1/opt/ -p -r
echo lxc exec $1 mv /opt/`basename $3` /opt/$2 
lxc exec $1 mv /opt/`basename $3` /opt/$2 
lxc file push $1.sh $1/root/
lxc exec $1 bash $1.sh 
