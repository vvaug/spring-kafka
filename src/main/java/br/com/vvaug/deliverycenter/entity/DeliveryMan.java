package br.com.vvaug.deliverycenter.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "delivery_man")
public class DeliveryMan {

	@Id
	private String id;
	private Boolean avaiable;
}
