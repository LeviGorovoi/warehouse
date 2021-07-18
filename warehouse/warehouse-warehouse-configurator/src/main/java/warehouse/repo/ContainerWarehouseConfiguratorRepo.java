package warehouse.repo;

import javax.validation.constraints.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.*;

public interface ContainerWarehouseConfiguratorRepo extends JpaRepository<Container, Long> {

	Container findByAddress(@NotEmpty String address);
	
	@Modifying
	@Query("update Container c set c.product=:product where c.containerId=:container_id")
	int setProductToContainer(@Param("container_id") long containerId, Product product);
	
	@Modifying
	@Query("update Container c set c.address=:new_address where c.containerId=:container_id")
	int changeContainerAddress(@Param("container_id") long containerId, @Param("new_address") String newAddress);
}
