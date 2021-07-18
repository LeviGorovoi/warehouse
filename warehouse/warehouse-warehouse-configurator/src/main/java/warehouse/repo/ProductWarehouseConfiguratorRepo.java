package warehouse.repo;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.Product;

public interface ProductWarehouseConfiguratorRepo extends JpaRepository<Product, Long> {

	Product findByProductName(@NotEmpty String productName);
	
	@Modifying
	@Query("update Product p set p.productName=:new_product_name where p.productId=:product_id")
	int changeProductName(@Param("product_id") long productId, @Param("new_product_name") String newProductName);

	@Modifying
	@Query("update Product p set p.transportSupply=:transport_supply where p.productId=:product_id")
	int setTransportSupply(@Param("product_id") long productId, @Param("transport_supply") Integer transportSupply);

	@Modifying
	@Query("update Product p set p.irreducibleBalance=:irreducible_balance where p.productId=:product_id")
	int setIrreducibleBalance(@Param("product_id") long productId, @Param("irreducible_balance") Integer irreducibleBalanceAmount);
}
