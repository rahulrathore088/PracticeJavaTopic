package com.tutorial.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
	
	private KafkaTemplate<String, String> template;
	
	public KafkaProducerService(KafkaTemplate<String, String> template) {
		this.template = template;
	}
	
	public void sendMessage(String message) {
		LOGGER.info(String.format("Message Sent %s", message));
		template.send("Topic1", message);
	}

}
