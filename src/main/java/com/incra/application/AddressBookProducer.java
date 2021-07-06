package com.incra.application;

import com.company.addressbook.AddressBookProtos;
import com.google.protobuf.ByteString;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.company.ProducerCreator.createProducer;

public class AddressBookProducer {

  public static void main(String[] args) throws Exception {

    String topicName = "users.registrations";

    Producer<Long, byte[]> producer = createProducer();

    for (int i = 0; i < 10; i++) {
      String message = "This is record " + i;
      AddressBookProtos.AddressBook addressBook =
          AddressBookProtos.AddressBook.newBuilder().build();

      byte[] ba = addressBook.toByteArray();
      producer.send(new ProducerRecord<>(topicName, new Long(i), ba));

      System.out.println("Message sent successfully");
    }

    producer.close();
  }
}
