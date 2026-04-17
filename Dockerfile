# ==========================================
# ESTÁGIO 1: Build (Compilação)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências (isso cria um cache para compilar mais rápido nas próximas vezes)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e compila o projeto ignorando os testes para ser mais rápido
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# ESTÁGIO 2: Run (Execução)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Pega apenas o arquivo .jar gerado no Estágio 1 e descarta todo o resto do código fonte e Maven
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]