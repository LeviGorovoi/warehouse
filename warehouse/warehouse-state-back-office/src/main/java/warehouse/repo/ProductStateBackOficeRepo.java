package warehouse.repo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import warehouse.entities.Product;

public interface ProductStateBackOficeRepo extends JpaRepository<Product, Long> {

	Product findByProductName(@NotEmpty String productName);

}
