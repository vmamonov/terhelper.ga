start:
	/opt/tomcat/latest/bin/catalina.sh run
	redis-server
	cd /sites/terhelper/ && mvn install -Pprod