package br.com.vvaug.deliverycenter.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vvaug.deliverycenter.entity.Delivery;
import br.com.vvaug.deliverycenter.service.DeliveryService;
import br.com.vvaug.deliverycenter.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryListener {
 
	private final DeliveryService deliveryService;

	@KafkaListener(topics = "delivery-request", groupId = "delivery-center-group")
	public void receiveDeliveryRequest(String request) throws JsonProcessingException {
		log.info("Received new delivery request: {}", request);
		deliveryService.receiveDeliveryRequest(request);
	}
	
	@KafkaListener(topics = "processing-delivery-request", groupId = "delivery-center-group")
	public void processDeliveryRequest(String request){
		deliveryService.processDeliveryRequest(request);
	}
	
	@KafkaListener(topics = "delivery-on-route", groupId = "delivery-center-group")
	
	public void checkOnRouteDeliveries(String request){
		
		var delivery = MapperUtils.toObject(request, Delivery.class);
		
//		log.info("Checking if request was delivered: {}", delivery);
//		
//		if(delivery.getDeliveryTime().isBefore(LocalDateTime.now()) ||
//				delivery.getDeliveryTime().equals(LocalDateTime.now())) {
//			deliveryService.delivery(delivery);
//		}
//		
//		throw new RequestWasNotDeliveredException("Request was not delivered yet.");
		
		deliveryService.delivery(delivery);
		
		/*TODO
		 * Implements retry logic
		 * that consumes the message until it gets delivered.
		 */
	}
}