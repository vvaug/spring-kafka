package br.com.vvaug.deliverycenter.producer;

import java.math.BigDecimal;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import br.com.vvaug.deliverycenter.dto.DeliveryRequest;
import br.com.vvaug.deliverycenter.entity.Delivery;
import br.com.vvaug.deliverycenter.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class DeliveryProducer {

	public final KafkaTemplate<String, String> kafkaTemplate;
		
	public void send(){
		log.info("Sending message");
		
		var deliveryRequest = DeliveryRequest.builder()
					.customerName("Victor")
					.deliveryTax(BigDecimal.TEN)
					.totalPrice(BigDecimal.valueOf(150.00))
					.fullAddress("St Hollywood 564, XPTO - NY")
					.build();
		
		var message = MapperUtils.toJson(deliveryRequest);
		
		kafkaTemplate.send("delivery-request", message);
	}
	
	public void proccessDelivery(Delivery delivery){
		
		var message = MapperUtils.toJson(delivery);
		
		kafkaTemplate.send("processing-delivery-request", message);
	}
	
	public void sendDeliveryToOnRoute(Delivery delivery){
		
		var message = MapperUtils.toJson(delivery);
		
		kafkaTemplate.send("delivery-on-route", message);
	}
}
