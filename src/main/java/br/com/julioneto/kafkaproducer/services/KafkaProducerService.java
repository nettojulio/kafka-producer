package br.com.julioneto.kafkaproducer.services;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerService {
    public static void sendMessage(String key, String value) throws InterruptedException, ExecutionException {

        var producer = new KafkaProducer<String,String>(properties());
        var record = new ProducerRecord<String, String>(System.getenv("KAFKA_TOPIC"), key, value);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("------------------------------------------");
            System.out.println("\n\nMensagem enviada com sucesso para: " + data.topic() + "\npartition " + data.partition() + "\noffset " + data.offset() + "\ntempo " + data.timestamp() + "\n\n");
            System.out.println("------------------------------------------");
        };

        producer.send(record, callback).get();
        producer.close();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_HOST"));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
