FROM openjdk:17
# FROM <베이스 이미지>

VOLUME /tmp
# VOLUME <컨테이너 내부의 dir>

COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]