package com.tutorial.kafka;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;

@SpringBootApplication
public class KafkaTutorial2Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTutorial2Application.class, args);
	}

	@Bean
	public KafkaAdmin admin(){
		Map<String,Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		return new KafkaAdmin(configs);
	}
	
	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("thing1").build();
	}
	
	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name("thing2").build();
	}
	
    @Bean
    public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context,
            ProducerFactory<Object, Object> pf) {

        // Clone the PF with a different Serializer, register with Spring for shutdown
        Map<String, Object> configs = new HashMap<>(pf.getConfigurationProperties());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        DefaultKafkaProducerFactory<Object, Object> bytesPF = new DefaultKafkaProducerFactory<>(configs);
        context.registerBean("bytesPF", DefaultKafkaProducerFactory.class, () -> bytesPF);

        Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
        map.put(Pattern.compile("two"), bytesPF);
        map.put(Pattern.compile(".+"), pf); // Default PF with StringSerializer
        return new RoutingKafkaTemplate(map);
    }

    @Bean
    public ApplicationRunner runner(RoutingKafkaTemplate routingTemplate) {
        return args -> {
            routingTemplate.send("one", "thing1");
            routingTemplate.send("two", "thing2".getBytes());
        };
    }

}
