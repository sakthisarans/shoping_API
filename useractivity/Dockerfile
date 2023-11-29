FROM maven:3.9.5 As build
ENV productport=80
ENV productdomain=product
ENV applicationport=80
ENV dbdomain=localhost
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-oracle
LABEL authors="sakth"
EXPOSE 80

COPY --from=build /home/app/target/useractivity-0.0.1.jar  app.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar