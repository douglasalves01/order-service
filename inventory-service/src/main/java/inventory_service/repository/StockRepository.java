package inventory_service.repository;

import inventory_service.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
