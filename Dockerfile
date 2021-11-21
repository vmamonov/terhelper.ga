# syntax=docker/dockerfile:1

FROM centos:centos8.3.2011

RUN mkdir -p /apps/terhelper/src/
WORKDIR /apps/terhelper
COPY src /apps/terhelper/src/
COPY .gitignore .gitignore
COPY Dockerfile Dockerfile
COPY .dockerignore .dockerignore
COPY pom.xml pom.xml
COPY app-config.xml app-config.xml

RUN yum install -y java-1.8.0-openjdk
#RUN yum install -y make
RUN yum install -y maven

#CMD ["make", "start"]






































#RUN mkdir -p /opt/tomcat/latest
#WORKDIR /opt/tomcat
#RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.71/bin/apache-tomcat-8.5.71.tar.gz
#RUN tar xvfz apache*.tar.gz
#RUN mv apache-tomcat-8.5.71/* /opt/tomcat/latest
#RUN rm -r apache-tomcat-8.5.71
#RUN yum install -y make
#RUN yum install -y redis
#RUN yum install -y mc
#WORKDIR /opt/tomcat/webapps
#RUN pwd
#RUN curl -O -L https://github.com/AKSarav/SampleWebApp/raw/master/dist/SampleWebApp.war
#RUN ls -l
#EXPOSE 8080
#WORKDIR /sites/terhelper
#ENTRYPOINT ["make", "start"]
#CMD ["make", "start"]
#CMD ["/opt/tomcat/latest/bin/catalina.sh", "run"]
#RUN export #CLASSPATH=/sites/terhelper/target/classes:/root/.m2/repository/
#ENV CLASSPATH "/sites/terhelper/target/classes:/root/.m2/repository/"

#COPY Makefile Makefile