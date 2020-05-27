FROM java:8-jdk-alpine

COPY . /Servicios-RESTful-con-Spring-Boot

RUN javac Servicios-RESTful-con-Spring-Boot/src/main/java/com/restfulcompleto/restfulspringboot/Client.java

EXPOSE 8080
CMD ["java", "Client"]
