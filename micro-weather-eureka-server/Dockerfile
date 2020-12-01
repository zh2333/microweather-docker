FROM java:8

COPY *.jar /eureka-server.jar

CMD ["--server.port=8761"]

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
