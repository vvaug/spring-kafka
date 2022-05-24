package br.com.vvaug.deliverycenter.repository;

import br.com.vvaug.deliverycenter.entity.DeliveryMan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, String>{

}
