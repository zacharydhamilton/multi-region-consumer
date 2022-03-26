package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.confluent.connect.replicator.offsets.ConsumerTimestampsInterceptor;

/**
 * Whaddup world!
 *
 */
public class Consumer {
    public static void main( String[] args ) throws IOException {
        final String topic = (System.getenv("TOPIC") != null) ? System.getenv("TOPIC") : "topic";
        final String group = (System.getenv("GROUP_ID") != null) ? System.getenv("GROUP_ID") : "multi-region-consumer";
        final String config = (System.getenv("CONFIG_FILE") != null) ? System.getenv("CONFIG_FILE") : "/config/setup.properties";
        Properties props = new Properties();
        addPropsFromFile(props, config);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerTimestampsInterceptor.class.getName());
        
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Message consumed: '%s'\n", record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
    private static void addPropsFromFile(Properties props, String file) throws IOException {
        if (!Files.exists(Paths.get(file))) {
            throw new IOException("Consumer config file (" + file + ") does not exist.");
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            props.load(inputStream);
        }
    }
}
