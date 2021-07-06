package com.incra.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;

import static com.company.ConsumerCreator.createConsumer;

@Slf4j
public class AddressBookConsumer {

  public static void main(String[] args) throws Exception {
    String topicName = "users.registrations";
    Duration t = Duration.ofSeconds(1, 0);

    Consumer<Long, byte[]> consumer = createConsumer();

    consumer.subscribe(Collections.singletonList(topicName));

    try {
      while (true) {
        log.info("Poll");
        ConsumerRecords<Long, byte[]> records = consumer.poll(t);

        for (ConsumerRecord<Long, byte[]> record : records) {
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
