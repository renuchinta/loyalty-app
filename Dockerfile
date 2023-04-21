FROM amazoncorretto:17
ADD target/loyalty-0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]