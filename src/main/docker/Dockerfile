FROM java:8-jdk

WORKDIR /home

COPY hibernate-remember-0.0.1-SNAPSHOT.jar /home/hibernate-remember.jar

EXPOSE 8080

CMD ["java", "-Djava.security.egd=file:/dev/urandom", "-Dspring.profiles.active=container", "-Dserver.contextPath=/api", "-jar", "/home/hibernate-remember.jar"]
