package br.com.vvaug.deliverycenter.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.vvaug.deliverycenter.entity.DeliveryMan;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String>{

	List<DeliveryMan> findByAvaiableTrue();
	
}
