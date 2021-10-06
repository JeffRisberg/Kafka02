package com.company.kafka;

import static com.company.kafka.ConsumerCreator.createConsumer;

import java.time.Duration;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;

@Slf4j
public class XmplConsumer {

  public static void main(String[] args) throws Exception {
    String topicName = "quickstart-events";
    Duration t = Duration.ofSeconds(1, 0);

    Consumer<Long, String> consumer = createConsumer();

    consumer.subscribe(Collections.singletonList(topicName));

    try {
      while (true) {
        log.info("Poll");
        ConsumerRecords<Long, String> records = consumer.poll(t);

        for (ConsumerRecord<Long, String> record : records) {
          log.info(String.format("topic = %s, partition = %s, offset = %d, key = %d, value = %s",
              record.topic(), record.partition(), record.offset(),
              record.key(), record.value()));
        }
      }
    } finally {
      consumer.close();
    }
  }
}
