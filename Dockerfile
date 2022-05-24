FROM openjdk:11
ARG ARTIFACT="target/deliverycenter*.jar"
ARG FINAL="/app/delivery-center.jar"
COPY ${ARTIFACT} ${FINAL}
ENTRYPOINT java -jar /app/delivery-center.jar