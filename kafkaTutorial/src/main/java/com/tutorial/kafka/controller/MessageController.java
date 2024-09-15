package com.tutorial.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.kafka.service.KafkaProducerService;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {
	
	private KafkaProducerService producerService;

	public MessageController(KafkaProducerService producerService) {
		this.producerService = producerService;
	}
	
	@GetMapping("/publish")
	public ResponseEntity<String> publish(@RequestParam("message") String message){
		producerService.sendMessage(message);
		return ResponseEntity.ok("Message Sent to topic.");
	}
	
	

}
