FROM openjdk:17
# FROM <베이스 이미지>

VOLUME /tmp
# VOLUME <컨테이너 내부의 dir>

COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]