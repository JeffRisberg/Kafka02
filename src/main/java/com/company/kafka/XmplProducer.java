package com.company.kafka;

import static com.company.kafka.ProducerCreator.createProducer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class XmplProducer {

  public static void main(String[] args) throws Exception {

    String topicName = "users.registrations";

    Producer<Long, String> producer = createProducer();

    for (int i = 0; i < 10; i++) {
      String message = "This is record " + i;

      producer.send(new ProducerRecord<>(topicName, new Long(i), message));

      System.out.println("Message sent successfully");
    }

    producer.close();
  }
}
