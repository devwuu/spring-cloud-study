# spring-cloud-study
인프런 Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA) study

# 서비스 실행 순서
* 유레카 서버
* config service 서버
* gateway service 서버
* 유레카 클라이언트 서버

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
      "topic.prefix": "my_topic_",
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
      "topics": "my_topic_member",
      "connection.url": "jdbc:mariadb://user-service-db:3306/user_sys",
      "connection.user": "root",
      "connection.password": "qwerty",
      "auto.create": "true",
      "auto.evolve": "true",
      "key.converter": "org.apache.kafka.connect.json.JsonConverter",
      "value.converter": "org.apache.kafka.connect.json.JsonConverter",
      "table.name.format": "my_topic_member",
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