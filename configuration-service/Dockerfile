FROM openjdk:17
# FROM <베이스 이미지>

VOLUME /tmp
# VOLUME <컨테이너 내부의 dir>

COPY build/libs/configuration-service-0.0.1-SNAPSHOT.jar configuration-service.jar
COPY build/libs/encryptKey.jks encryptKey.jks

ENTRYPOINT ["java", "-jar", "configuration-service.jar"]