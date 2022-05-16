package br.com.vvaug.deliverycenter.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.vvaug.deliverycenter.dto.DeliveryRequest;
import br.com.vvaug.deliverycenter.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "deliveries")
public class Delivery {

	@Id
	private String id;
	private BigDecimal totalPrice;
	private BigDecimal deliveryTax;
	private String customerName;
	private String fullAddress;
	private DeliveryMan deliveryMan;
	private DeliveryStatus deliveryStatus;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime deliveryTime;
	
	
	public Delivery(DeliveryRequest deliveryRequest) {
		this.setTotalPrice(deliveryRequest.getTotalPrice());
		this.setDeliveryTax(deliveryRequest.getDeliveryTax());
		this.setCustomerName(deliveryRequest.getCustomerName());
		this.setFullAddress(deliveryRequest.getFullAddress());
	}
}
