FROM adoptopenjdk:11-jre-hotspot
ARG JAR=target/*.jar
COPY ${JAR} webshop.jar
EXPOSE 8080
ENV DATABASE_HOST localhost
ENV DATABASE_PORT 5432
ENV DATABASE_USER postgres
ENV DATABASE_PASSWORD admin
ENV DATABASE_NAME postgres
ENTRYPOINT ["java","-jar","/webshop.jar"]