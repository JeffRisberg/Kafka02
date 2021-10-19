package com.company.kafka;

import com.company.kafka.constants.IKafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

@Slf4j
public class XmplProducer {

  public static void main(String[] args) throws Exception {

    String topicName = "kafka02-alerts";

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);

    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            LongSerializer.class.getName());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroSerializer.class.getName());
    props.put("schema.registry.url", "http://localhost:8081");

    Producer<Long, com.kakfainaction.Alert> producer = new KafkaProducer<>(props);

    for (int i = 0; i < 20; i++) {
      com.kakfainaction.Alert alert = new com.kakfainaction.Alert();
      alert.setTitle("Alert number " + i);
      alert.setStatus("Pending");

      producer.send(new ProducerRecord<>(topicName, new Long(i), alert));

      log.info("Message sent successfully");
    }

    producer.close();
  }
}
