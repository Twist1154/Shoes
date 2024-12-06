FROM openjdk:17-jdk

COPY target/shoe_store.jar .

EXPOSE 8080

entrypoint ["java", "-jar", "shoe_store.jar"]