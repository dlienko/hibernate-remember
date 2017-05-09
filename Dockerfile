FROM java:8-jre

WORKDIR /home

COPY target/hibernate-remember-0.0.1-SNAPSHOT.jar /home/hibernate-remember.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/hibernate-remember.jar"]
