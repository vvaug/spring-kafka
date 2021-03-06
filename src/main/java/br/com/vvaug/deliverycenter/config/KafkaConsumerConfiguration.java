package br.com.vvaug.deliverycenter.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.vvaug.deliverycenter.exception.RequestWasNotDeliveredException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Bean
	public ConsumerFactory<String, String> consumerFactory(){

		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		props.put(ConsumerConfig.GROUP_ID_CONFIG, "delivery-center-group");

		props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "true");

		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){

		ConcurrentKafkaListenerContainerFactory<String, String> factory =
		          new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactory());

		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
		return factory;
	}


	@Bean
	public RetryTopicConfiguration retryTopicConfiguration(KafkaTemplate<String, String> template) {

		List<Class<? extends Throwable>> throwableList =
				Arrays.asList(
						IllegalArgumentException.class,
						IllegalAccessException.class
					);

		return RetryTopicConfigurationBuilder
				.newInstance()
				.exponentialBackoff(10000, 2, 20000)
				.maxAttempts(20)
				.notRetryOn(throwableList)
				.doNotAutoCreateRetryTopics()
				.listenerFactory(kafkaListenerContainerFactory())
				.setTopicSuffixingStrategy(TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
				.create(template);
	}

}
