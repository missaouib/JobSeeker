#FROM tomcat:jre11-slim
FROM openjdk:11-jdk-alpine
ADD /build/libs/*.jar *.jar
ENTRYPOINT ["java","-jar","*.jar"]
EXPOSE 8080