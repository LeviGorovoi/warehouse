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
	void setProductToContainer(@Param("container_id") long containerId, Product product);
}
