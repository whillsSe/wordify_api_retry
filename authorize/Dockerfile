FROM tomcat:9.0

ENV DB_HOST=db
ENV DB_USER=auth
ENV DB_PASSWORD=auth_password


ADD target/wordify-auth-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/wordify-auth-1.0-SNAPSHOT.war
COPY config/context.xml /usr/local/tomcat/conf/context.xml

EXPOSE 8081

CMD ["catalina.sh","run"]