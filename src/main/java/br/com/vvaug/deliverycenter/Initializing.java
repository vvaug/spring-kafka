package br.com.vvaug.deliverycenter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.vvaug.deliverycenter.entity.DeliveryMan;
import br.com.vvaug.deliverycenter.producer.DeliveryProducer;
import br.com.vvaug.deliverycenter.repository.DeliveryManRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Initializing implements CommandLineRunner {

	private final DeliveryProducer producer;
	
	private final DeliveryManRepository repo;
	
	@Override
	public void run(String... args) throws Exception {
		
		repo.deleteAll();
		
		repo.save(DeliveryMan.builder()
				.available(true)
				.build());
		
	}

}
