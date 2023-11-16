FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 9093
COPY target/orderapp-docker.jar /app/orderapp-docker.jar
CMD ["java","-jar","orderapp-docker.jar]

