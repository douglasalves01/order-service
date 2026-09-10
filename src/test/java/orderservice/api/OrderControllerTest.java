package orderservice.api;

import orderservice.application.OrderService;
import orderservice.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void shouldCreateOrderAndReturn201() throws Exception {
        UUID productId = UUID.randomUUID();
        BigDecimal totalAmount = new BigDecimal("199.90");

        Order order = new Order(
                productId,
                2,
                totalAmount
        );

        when(orderService.createOrder(
                eq(productId),
                eq(2),
                eq(totalAmount)
        )).thenReturn(order);

        String requestBody = """
                {
                  "productId": "%s",
                  "quantity": 2,
                  "totalAmount": 199.90
                }
                """.formatted(productId);

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(jsonPath("$.id")
                        .value(order.getId().toString()))
                .andExpect(jsonPath("$.productId")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.quantity")
                        .value(2))
                .andExpect(jsonPath("$.totalAmount")
                        .value(199.90))
                .andExpect(jsonPath("$.status")
                        .value("PENDING"));

        verify(orderService).createOrder(
                eq(productId),
                eq(2),
                eq(totalAmount)
        );
    }

    @Test
    void shouldReturn400WhenQuantityIsInvalid() throws Exception {
        UUID productId = UUID.randomUUID();

        String requestBody = """
                {
                  "productId": "%s",
                  "quantity": 0,
                  "totalAmount": 199.90
                }
                """.formatted(productId);

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(orderService, never())
                .createOrder(
                        eq(productId),
                        eq(0),
                        eq(new BigDecimal("199.90"))
                );
    }
}