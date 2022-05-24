package br.com.vvaug.deliverycenter.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.vvaug.deliverycenter.dto.DeliveryRequest;
import br.com.vvaug.deliverycenter.entity.Delivery;
import br.com.vvaug.deliverycenter.entity.DeliveryMan;
import br.com.vvaug.deliverycenter.enums.DeliveryStatus;
import br.com.vvaug.deliverycenter.producer.DeliveryProducer;
import br.com.vvaug.deliverycenter.repository.DeliveryManRepository;
import br.com.vvaug.deliverycenter.repository.DeliveryRepository;
import br.com.vvaug.deliverycenter.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
	
	private final DeliveryRepository deliveryRepository;
	
	private final DeliveryProducer deliveryProducer;
	
	private final DeliveryManRepository deliveryManRepository;
	
	public void receiveDeliveryRequest(String request) throws JsonProcessingException {
		
		var deliveryRequest = MapperUtils.toObject(request, DeliveryRequest.class);
		
		var delivery = new Delivery(deliveryRequest);
		
		delivery.setDeliveryStatus(DeliveryStatus.RECEIVED);
		
		var entity = deliveryRepository.save(delivery);
		
		log.info("Entity: {}", entity.toString());
		
		log.info("Processing delivery");
		
		deliveryProducer.proccessDelivery(entity);
	}	
	
	public void processDeliveryRequest(String request){
		
		var delivery = MapperUtils.toObject(request, Delivery.class);
		
		delivery.setDeliveryStatus(DeliveryStatus.PROCESSING);
		
		var deliveryMan = findAvaiableDeliveryMan();
		
		delivery.setDeliveryMan(deliveryMan);
		delivery.setDeliveryStatus(DeliveryStatus.ON_ROUTE);
		delivery.setDeliveryTime(LocalDateTime.now().plusMinutes(1L));
		deliveryMan.setAvailable(false);
		
		deliveryManRepository.save(deliveryMan);
		
		deliveryRepository.save(delivery);
		
		deliveryProducer.sendDeliveryToOnRoute(delivery);
	}

	public DeliveryMan findAvaiableDeliveryMan() {

		log.info("Searching for a delivery man");

		var deliveryMan = deliveryManRepository
				.findAll()
				.stream()
				.filter(DeliveryMan::getAvailable)
				.findFirst();

		if (deliveryMan.isPresent()){
			log.info("Found a delivery man available.");
			return deliveryMan.get();
		}

		log.error("There is no available delivery man now.");

		throw new RuntimeException("There is no available delivery man now.");
	}

	public void delivery(Delivery delivery) {
		
		delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
			
		delivery.getDeliveryMan().setAvailable(true);
			
		deliveryManRepository.save(delivery.getDeliveryMan());
			
		deliveryRepository.save(delivery);

		log.info("Request was delivered successfully.");
	}

	public void createDeliveryRequest(DeliveryRequest deliveryRequest){
		deliveryProducer.createDeliveryRequest(deliveryRequest);
	}
}
