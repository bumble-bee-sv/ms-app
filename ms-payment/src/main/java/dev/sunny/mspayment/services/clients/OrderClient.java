package dev.sunny.mspayment.services.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderClient {

    private final RestClient restClient;

    public void notifyOrderService(UUID orderId, String status) {
        String endpoint = "/orders/" + orderId + "/status";

        restClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(endpoint)
                        .queryParam("status", status)
                        .build(orderId))
                .retrieve()
                .toBodilessEntity();
    }

}
