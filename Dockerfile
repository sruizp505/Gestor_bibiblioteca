# ETAPA 1: Construcción (Con límite de RAM)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Variable para limitar la memoria de Maven a 256MB (Vital para Render Free)
ENV MAVEN_OPTS="-Xmx256m -Xms256m"

COPY pom.xml .
# Descarga dependencias (modo offline para ahorrar red en el siguiente paso)
RUN mvn dependency:go-offline -B

COPY src ./src

# Empaquetamos saltando tests y usando el límite de memoria
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]