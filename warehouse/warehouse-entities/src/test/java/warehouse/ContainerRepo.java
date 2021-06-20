package warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import warehouse.entities.Container;

public interface ContainerRepo extends JpaRepository<Container, Long> {

	Container findByAddress(String string);

}
