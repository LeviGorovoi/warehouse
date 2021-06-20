package warehouse;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;

import warehouse.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Product findByProductName(@NotEmpty String productName);

}
