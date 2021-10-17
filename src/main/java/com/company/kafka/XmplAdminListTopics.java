package com.company.kafka;

import com.company.kafka.constants.IKafkaConstants;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Slf4j
public class XmplAdminListTopics {

  public static void main(String[] args) throws Exception {

    final Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);

    AdminClient kac = AdminClient.create(props);

    ListTopicsResult ltr = kac.listTopics();

    for (TopicListing listing : ltr.listings().get()) {
      log.info(listing.name());
    }
  }

}
