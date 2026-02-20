# construimos
FROM maven:3.9.6-eclipse-temurin-21 AS build

# definimos a pasta de trabalho no linux do container
WORKDIR /app

# Copia o arquivo de dependências primeiro (para aproveitar cache do Docker)
COPY pom.xml .

# Baixa as dependências (Internet -> Container)
# Isso faz com que, se você só mudar o código, não precise baixar tudo denovo e denovo
RUN mvn dependency:go-offline

# Copia o resto do código fonte (src)
COPY src ./src

# Compila o projeto e cria o arquivo .jar
# O comando 'clean package' gera o arquivo na pasta target/
RUN mvn clean package -DskipTests

# ====================================================

# RUNTIME
# uma imagem mais leve apenas para rodar (sem o Maven)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Agora o arquivo exato que definimos no finalName
COPY --from=build /app/target/app.jar app.jar

# Expõe a porta que o Java usa (só para documentação, quem manda é o docker-compose)
EXPOSE 8080

# O comando que faz o Java rodar quando o container inicia
CMD ["java", "-jar", "app.jar"]