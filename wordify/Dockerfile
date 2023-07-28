FROM tomcat:10-jdk17-corretto

ENV DB_HOST=db
ENV DB_USER=api
ENV DB_PASSWORD=api_password

ADD ./target/wordify-api-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/wordify-api-1.0-SNAPSHOT.war
COPY config/server.xml /usr/local/tomcat/conf/server.xml
COPY config/context.xml /usr/local/tomcat/conf/context.xml
EXPOSE 8080

CMD ["catalina.sh","run"]