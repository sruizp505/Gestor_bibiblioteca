# ETAPA 1: Construcción Ligera
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# 1. Copiamos solo el pom.xml primero para bajar librerías
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copiamos el código fuente
COPY src ./src

# 3. Empaquetamos SIN compilar tests (Ahorra mucha RAM)
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]