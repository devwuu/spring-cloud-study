# spring-cloud-study
인프런 Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA) study

# 서비스 실행 순서
* 유레카 서버
* config service 서버
* gateway service 서버
* 유레카 클라이언트 서버

# kafka 
* docker ps 
  * 컨테이너 아이디 조회
* docker exec -it {컨테이너ID} /bin/bash
  * 컨테이너 접속
* https://github.com/bitnami/containers/blob/main/bitnami/kafka/README.md#example-create-a-replicated-topic
  * bin 폴더 위치


```zsh
I have no name!@4e13e1ea7a5c:/opt/bitnami/kafka/bin$ ls
connect-distributed.sh	      kafka-console-consumer.sh    kafka-get-offsets.sh		 kafka-replica-verification.sh	     kafka-verifiable-producer.sh
connect-mirror-maker.sh       kafka-console-producer.sh    kafka-jmx.sh			 kafka-run-class.sh		     trogdor.sh
connect-plugin-path.sh	      kafka-consumer-groups.sh	   kafka-leader-election.sh	 kafka-server-start.sh		     windows
connect-standalone.sh	      kafka-consumer-perf-test.sh  kafka-log-dirs.sh		 kafka-server-stop.sh		     zookeeper-security-migration.sh
kafka-acls.sh		      kafka-delegation-tokens.sh   kafka-metadata-quorum.sh	 kafka-storage.sh		     zookeeper-server-start.sh
kafka-broker-api-versions.sh  kafka-delete-records.sh	   kafka-metadata-shell.sh	 kafka-streams-application-reset.sh  zookeeper-server-stop.sh
kafka-client-metrics.sh       kafka-dump-log.sh		   kafka-mirror-maker.sh	 kafka-topics.sh		     zookeeper-shell.sh
kafka-cluster.sh	      kafka-e2e-latency.sh	   kafka-producer-perf-test.sh	 kafka-transactions.sh
kafka-configs.sh	      kafka-features.sh		   kafka-reassign-partitions.sh  kafka-verifiable-consumer.sh
```

```zsh
I have no name!@6a9a253f2c5f:/opt/bitnami/kafka/bin$ kafka-topics.sh --bootstrap-server localhost:9092 --list
__consumer_offsets
new-topic
quickstart-events
```

```zsh
I have no name!@6a9a253f2c5f:/opt/bitnami/kafka/bin$ kafka-topics.sh --bootstrap-server localhost:9092 --create --topic hello-world-events --partitions 1
Created topic hello-world-events.
```

```zsh
I have no name!@6a9a253f2c5f:/opt/bitnami/kafka/bin$ kafka-console-producer.sh --broker-list localhost:9092 --topic hello-world-events
>hello, world!
>hi, there!
```

```zsh
I have no name!@6a9a253f2c5f:/opt/bitnami/kafka/bin$ kafka-console-consumer.sh --bootstrap-server  localhost:9092 --topic hello-world-events --from-beginning
hello, world!
hi, there!

```