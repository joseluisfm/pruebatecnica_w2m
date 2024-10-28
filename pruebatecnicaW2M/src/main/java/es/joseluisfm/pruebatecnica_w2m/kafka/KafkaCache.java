package es.joseluisfm.pruebatecnica_w2m.kafka;


import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.joseluisfm.pruebatecnica_w2m.utils.JsonUtils;
import io.kcache.Cache;
import io.kcache.KafkaCacheConfig;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class KafkaCache {

	@Value(value = "${kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	private static String applicationName;
	
	@Value("${spring.application.name}")
	public void setStaticApplicationName(String value) {
		applicationName = value; //NOSONAR
	}

	private Cache<String, String> cache;

	@PostConstruct
	public void init() throws KafkaException {
		try {
			Properties props = new Properties();
			props.setProperty("kafkacache.bootstrap.servers", bootstrapAddress);
			props.setProperty("kafkacache.topic", applicationName + "_cache");
			cache = new io.kcache.KafkaCache<>(new KafkaCacheConfig(props), Serdes.String(), Serdes.String());
			cache.init();
		} catch (Exception e) {
			throw new KafkaException("Error in KafkaCache Bean's PostConstruct method", e);
		}

	}

	@PreDestroy
	public void onDestroy() throws KafkaException {
		try {
			cache.close();
		} catch (Exception e) {
			throw new KafkaException("Error destroying KafkaCache Bean", e);
		}
	}

	public void setValue(String key, String value) {
		if (StringUtils.isNotBlank(key)) {
			cache.put(key, value);
		}
	}
	
	public void setValue(String key, Object value) throws KafkaException {
		if (StringUtils.isNotBlank(key)) {
			try {
				cache.put(key, JsonUtils.toJson(value));
			} catch (Exception e) {
				throw new KafkaException("Error setting value in cache", e);
			}
		}
	}

	public String getValue(String key) {
		if (StringUtils.isNotBlank(key)) {
			return cache.get(key);
		} else {
			return null;
		}
	}
	
	public Object getValue(String key, Class<?> clazz) throws KafkaException {
		if (StringUtils.isNotBlank(key)) {
			try {
				String cacheValue = cache.get(key);
				if(StringUtils.isNotBlank(cacheValue)) {
					return JsonUtils.fromJson(cacheValue, clazz);
				} else {
					return null;
				}
			} catch (Exception e) {
				throw new KafkaException("Error getting value in cache", e);
			}  
		} else {
			return null;
		}
	}

	public void remove(String key) {
		if (StringUtils.isNotBlank(key)) {
			cache.remove(key);
		}
	}

}

