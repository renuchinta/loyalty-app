FROM openjdk:11
EXPOSE 8080
ADD target/loyalty-app.jar loyalty-app.jar
ENTRYPOINT ["java","-jar","loyalty-app.jar"]