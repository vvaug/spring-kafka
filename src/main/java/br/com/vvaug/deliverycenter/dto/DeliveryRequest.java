package br.com.vvaug.deliverycenter.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeliveryRequest {

	private BigDecimal totalPrice;
	private BigDecimal deliveryTax;
	private String customerName;
	private String fullAddress;
}
