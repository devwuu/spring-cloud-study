# spring-cloud-study
인프런 Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA) study

# 서비스 실행 순서
* 유레카 서버
* config service 서버
* gateway service 서버
* 유레카 클라이언트 서버

[//]: # (todo key 만드는 법 문서화)

# Spring Atuator란
* 애플리케이션의 상태를 모니터링, Metric 수집, 환경 설정 정보 업데이트 등을 위한 Http Endpoint를 제공해주는 모듈이다.
* 참고 문서 
  * https://ksl2950.tistory.com/147

# Spring cloud bus란
* 환경 설정 정보가 변경되었을 때 각각의 애플리케이션에 대해서 refresh 하지 않고 bus를 통해 환경 설정 정보를 일괄 업데이트 할 수 있도록 해줌
* cloud bus는 설정 정보 애플리케이션을 퍼블리셔로 설정하고 그 외 마이크로 서비스들을 구독자로 설정하여서  
  설정 정보 애플리케이션이 설정 정보가 업데이트 되었다고 메세지를 퍼블리싱하면 그것을 구독하는 마이크로 서비스가 환경 설저 정보를 업데이트 할 수 있도록 한다.
* 이번 프로젝트에서는 rabbitMQ를 사용했다 (다른 MQ를 사용할 수도 있다.)
* 참고 문서
  * https://happycloud-lee.tistory.com/211



# kafka 예제
* 공식 문서
  * https://docs.confluent.io/platform/current/installation/docker/config-reference.html
  * https://github.com/confluentinc/cp-all-in-one/blob/7.6.1-post/cp-all-in-one/docker-compose.yml
  * https://github.com/confluentinc/demo-scene/blob/master/kafka-connect-zero-to-hero/docker-compose.yml#L82-L87
* 참고 문서
  * https://velog.io/@ksh9409255/카프카-커넥트
  * https://velog.io/@dm911/Kafka-Spooldir-Source-Connector
* connector 등록 예제
  ```json
  {
  "name": "jdbc-source-connector",
  "config": {
      "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
      "tasks.max": "1",
      "connection.url": "jdbc:mariadb://user-service-db:3306/user_sys",
      "connection.user": "root",
      "connection.password": "qwerty",
      "mode": "incrementing",
      "incrementing.column.name": "id",
      "topic.prefix": "user_sys_", // topic 이름은 prefix+whitelist == user_sys_member
      "poll.interval.ms": "10",
      "table.whitelist" : "member"
    }
  }
  ```

  ```json
  {
    "name": "jdbc-sink-connector",
    "config": {
      "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
      "tasks.max": "1",
      "topics": "user_sys_member", // 연결할 topic, source connector에서 생성한 topic 이름과 동일해야 함
      "connection.url": "jdbc:mariadb://user-service-db:3306/user_sys",
      "connection.user": "root",
      "connection.password": "qwerty",
      "auto.create": "true", // table을 자동 생성
      "auto.evolve": "true",
      "key.converter": "org.apache.kafka.connect.json.JsonConverter",
      "value.converter": "org.apache.kafka.connect.json.JsonConverter",
      "table.name.format": "user_sync", // table 이름, default는 topics
      "insert.mode": "insert",
      "pk.mode": "none"
    }
  }
  ```


* kafka 명령어 예제
  ```zsh
  $ kafka-topics --bootstrap-server localhost:9092 --list
  $ kafka-topics --bootstrap-server localhost:9092 --create --topic hello-world-events --partitions 1
  $ kafka-console-producer --broker-list localhost:9092 --topic hello-world-events
  $ kafka-console-consumer --bootstrap-server  localhost:9092 --topic hello-world-events --from-beginning
  ```

  ```zsh
  [appuser@broker ~]$ kafka-console-consumer --bootstrap-server  localhost:9092 --topic quickstart_member --from-beginning
  {"schema":{"type":"struct","fields":[{"type":"int64","optional":false,"field":"id"},{"type":"string","optional":false,"field":"email"},{"type":"string","optional":false,"field":"encrypted_pwd"},{"type":"string","optional":false,"field":"user_id"}],"optional":false,"name":"member"},"payload":{"id":1,"email":"user1@test.com","encrypted_pwd":"","user_id":"6b2a13f2-0970-4ea7-967a-475c6265e52a"}}
  {"schema":{"type":"struct","fields":[{"type":"int64","optional":false,"field":"id"},{"type":"string","optional":false,"field":"email"},{"type":"string","optional":false,"field":"encrypted_pwd"},{"type":"string","optional":false,"field":"user_id"}],"optional":false,"name":"member"},"payload":{"id":2,"email":"test@gmai.com","encrypted_pwd":"","user_id":"new_member"}}
  ```
  
# Tracing
* spring-cloud-sleuth 의 마지막 마이너 버전은 3.1 이후 Micrometer Tracing 프로젝트로 이전  
  따라서 최신 버전과 연동을 위해서 Micrometer를 채택하여 사용
* 공식 문서
  * https://spring.io/projects/spring-cloud-sleuth
  * https://docs.spring.io/spring-boot/reference/actuator/tracing.html#actuator.micrometer-tracing
  * https://docs.micrometer.io/tracing/reference/index.html
  * https://github.com/micrometer-metrics/micrometer-samples/blob/main/micrometer-samples-boot3-web/build.gradle
  * https://github.com/spring-cloud/spring-cloud-openfeign/issues/812
* 참고 문서
  * https://easywritten.com/post/using-spring-boot-3-with-zipkin/

# Monitoring
* 프로메테우스 : **Metrics를 수집**하고 모니터링 및 알람에 사용
* 그라파나 : 데이터 시각화, 모니터링 및 분석을 위함. 시계열 데이터를 시각화 하기 위한 **대시보드** 제공.
  * 프로메테우스 서버 URL : http://prometheus:9090
* 공식 문서
  * https://prometheus.io/docs/prometheus/latest/installation/
* 참고 문서
  * https://semtul79.tistory.com/21
  * https://kimjingo.tistory.com/239

# docker 관련 명령어
* 이미지 빌드
  ```zsh
  docker build -t devwuu/config-service:1.0
  ```
* 프로파일 설정
  ```zsh
  docker compose --profile docker up -d
  ```