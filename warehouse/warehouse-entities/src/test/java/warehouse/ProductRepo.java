package warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import warehouse.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
