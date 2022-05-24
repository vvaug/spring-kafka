package br.com.vvaug.deliverycenter.resource;

import br.com.vvaug.deliverycenter.dto.DeliveryRequest;
import br.com.vvaug.deliverycenter.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryResource {

    private final DeliveryService deliveryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody DeliveryRequest deliveryRequest){
        deliveryService.createDeliveryRequest(deliveryRequest);
    }
}
