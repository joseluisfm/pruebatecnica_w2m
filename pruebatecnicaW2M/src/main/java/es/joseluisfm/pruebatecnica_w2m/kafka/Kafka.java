package es.joseluisfm.pruebatecnica_w2m.kafka;

import java.net.InetAddress;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Kafka {

	private static String applicationName;
	private static int serverPort;
	
	private static final Gson gson = new Gson();
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Kafka.class);
	
	public Kafka(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Value("${spring.application.name}")
	public void setStaticApplicationName(String value) {
		applicationName = value; // NOSONAR
	}

	@Value("${server.port}")
	public void setStaticServerPort(String value) {
		serverPort = Integer.parseInt(value); // NOSONAR
	}

	public void sendMessage(String topic, String msg) throws KafkaException {
		try {
			logger.info("Publishing in Kafka topic {} the message {}", topic, msg);
			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, msg);
			kafkaTemplate.send(producerRecord);
		} catch (Exception e) {
			throw new KafkaException("Error publishing in kafka", e);
		}
	}

	public void sendMessage(String topic, Object object) throws KafkaException {
		this.sendMessage(topic, gson.toJson(object));
	}

	/**
	 * To obtain a Unique Kafka GroupId. This is use for consume a kafka topic like
	 * a queue
	 * 
	 * Example of use:
	 * 
	 * groupId =
	 * "#{#{T(es.uma.informatica.smids.core.kafka.Kafka).getUniqueKafkaGroupId()}()}"
	 * 
	 * @return
	 * @throws KafkaException
	 */
	public static String getUniqueKafkaGroupId() throws KafkaException {
		try {
			return applicationName + ":" + InetAddress.getLocalHost().getHostName() + ":" + serverPort;
		} catch (Exception e) {
			throw new KafkaException("Error building a Unique Kafka GroupId", e);
		}
	}
}
