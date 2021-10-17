package com.company.kafka;

import com.company.kafka.constants.IKafkaConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

public class XmplProducer {

  public static void main(String[] args) throws Exception {

    String topicName = "users.registrations";

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);

    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            LongSerializer.class.getName());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroSerializer.class.getName());
    props.put("schema.registry.url", "http://localhost:8081");

    Producer<Long, com.kakfainaction.Alert> producer = new KafkaProducer<>(props);

    for (int i = 0; i < 10; i++) {
      com.kakfainaction.Alert alert = new com.kakfainaction.Alert();
      alert.setStatus("Pending");

      producer.send(new ProducerRecord<>(topicName, new Long(i), alert));

      System.out.println("Message sent successfully");
    }

    producer.close();
  }
}
