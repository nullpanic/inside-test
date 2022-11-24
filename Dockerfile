FROM openjdk:18
ARG JAR_FILE=target/*.jar
ENV DB_USER_LOCAL=${DB_USER_LOCAL}
ENV DB_PASSWORD_LOCAL=${DB_PASSWORD_LOCAL}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${DB_PASSWORD_LOCAL}","-Dspring.datasource.username=${DB_USER_LOCAL}","-jar","/app.jar"]