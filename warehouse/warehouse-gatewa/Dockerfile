FROM openjdk:14.0.2
EXPOSE 9092
ADD ./target/warehouse-gatewa-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]