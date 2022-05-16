package br.com.vvaug.deliverycenter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.vvaug.deliverycenter.entity.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery, String>{

}
