FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/loyalty-0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]