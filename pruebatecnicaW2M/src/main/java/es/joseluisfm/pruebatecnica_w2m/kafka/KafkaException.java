package es.joseluisfm.pruebatecnica_w2m.kafka;

public class KafkaException extends Exception {

	private static final long serialVersionUID = 1728265263434097201L;
	
	public KafkaException(String error, Exception e) {
		super(error, e);
	}

}
