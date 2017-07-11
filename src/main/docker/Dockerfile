FROM java:8-jdk

WORKDIR /home

COPY yoga-db-0.0.1-SNAPSHOT.jar /home/app.jar

EXPOSE 8080

CMD ["java", "-Djava.security.egd=file:/dev/urandom", "-Dspring.profiles.active=container", "-Dserver.contextPath=/api", "-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n", "-jar", "/home/app.jar"]
