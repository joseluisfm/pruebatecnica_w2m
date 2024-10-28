package es.joseluisfm.pruebatecnica_w2m.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
	
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Subscriber.class);
	
	@KafkaListener(topics = "PRUEBA", groupId = "pruebatecnica_w2m")
	public void subscriber(String message) {
		LOGGER.info("New event: {}", message);
	}
}
