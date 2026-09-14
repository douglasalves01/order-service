package inventory_service.service;

import inventory_service.domain.Stock;
import inventory_service.domain.exception.StockNotFoundException;
import inventory_service.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryService {
    private final StockRepository stockRepository;

    public InventoryService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void reserveStock(UUID productId, Integer quantity) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() ->
                new StockNotFoundException(productId));

        stock.reserve(quantity);

    }
}
