package com.company.kafka;

import com.company.kafka.constants.IKafkaConstants;
import com.kakfainaction.Alert;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class XmplConsumer {

  public static void main(String[] args) throws Exception {
    String topicName = "kafka02-alerts";
    Duration t = Duration.ofSeconds(1, 0);

    final Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);

    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroDeserializer.class.getName());
    props.put("schema.registry.url", "http://localhost:8081");

    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    Consumer<Long, Alert> consumer = new KafkaConsumer<Long, Alert>(props);

    consumer.subscribe(Collections.singletonList(topicName));

    try {
      while (true) {
        log.info("Poll");
        ConsumerRecords<Long, Alert> records = consumer.poll(t);

        for (ConsumerRecord<Long, Alert> record : records) {
          log.info(
              String.format(
                  "topic = %s, partition = %s, offset = %d, key = %d, value = %s",
                  record.topic(),
                  record.partition(),
                  record.offset(),
                  record.key(),
                  record.value()));
        }
      }
    } finally {
      consumer.close();
    }
  }
}
