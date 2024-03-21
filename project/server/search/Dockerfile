FROM openjdk:17-jdk
ENV SPRING_PROFILE prod
ADD build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/app.jar"]
