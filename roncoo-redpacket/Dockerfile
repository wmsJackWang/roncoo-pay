FROM 10.11.152.38:5000/wallet-redservice-lib:1.0_12
VOLUME /tmp
ADD target/wallet-redservice-1.0.0.jar  /workspace/wallet-redservice-1.0.0.jar
ADD start.sh /workspace/start.sh 
ADD stop.sh /workspace/stop.sh 
RUN yum install net-tools -y 
#RUN sh -c 'touch /wallet-redservice-1.0.0.jar'
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime  && echo 'Asia/Shanghai' >/etc/timezone 
ENV LANG en_US.UTF-8 
#ENTRYPOINT [ "sh", "-c" , " java -Xmx2g -Xms2g -Djavamelody.graphite-address=10.11.152.59:2003  -Djavamelody.resolution-seconds=10 -Dlog.path.prefix=/logs/wallet-redservice/$(ifconfig eth0 | grep 'inet '  | awk '{ print $2}')  -Djava.security.egd=file:/dev/./urandom -jar /workspace/wallet-redservice-1.0.0.jar -jar  " ]
ENTRYPOINT  java  ${JAVA_OPTS} -Dlog.path.prefix=/logs/wallet-redservice/$(date '+%Y-%m-%d_%H:%M:%S')_$(ifconfig eth0 | grep 'inet '  | awk '{ print $2}')  -Djava.security.egd=file:/dev/./urandom -jar /workspace/wallet-redservice-1.0.0.jar