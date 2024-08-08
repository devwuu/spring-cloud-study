FROM openjdk:17
# FROM <베이스 이미지>

VOLUME /tmp
# VOLUME <컨테이너 내부의 dir>

COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "order-service.jar"]