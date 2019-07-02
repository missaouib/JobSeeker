FROM tomcat:jre11-slim
#FROM openjdk:11-jre
#WORKDIR /home/backend
#COPY . .
#RUN gradle bootJar
COPY /build/libs/*.jar *.jar
CMD ["java","-jar","*.jar"]