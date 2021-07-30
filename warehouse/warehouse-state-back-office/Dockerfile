FROM openjdk:14.0.2
EXPOSE 9091
ADD ./target/warehouse-state-back-office-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]