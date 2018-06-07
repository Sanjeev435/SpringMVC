FROM java:8
FROM tomcat:8.0.52-jre8

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code
ADD pom.xml /code/pom.xml
ADD src/main/webapp/WEB-INF/web.xml /codes/rc/main/webapp/WEB-INF/web.xml
RUN ["mvn", "dependency:resolve"]

ADD src /code/src
RUN ["mvn", "package"]
CMD ["usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-war", "target/springMVC.war"]

COPY /target/springMVC.war /usr/local/tomcat/webapps/springMVC.war