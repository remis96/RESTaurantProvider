FROM openjdk:12-jdk-alpine
VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
RUN apk add --no-cache netcat-openbsd

COPY target/RESTaurant-0.0.1-SNAPSHOT.jar /app/app.jar
COPY wait-for.sh /app/wait-for.sh

RUN chmod +x /app/wait-for.sh

CMD ["-c", "/app/wait-for.sh postgres:5432 && java -jar /app/app.jar"]

ENTRYPOINT ["/bin/sh"]