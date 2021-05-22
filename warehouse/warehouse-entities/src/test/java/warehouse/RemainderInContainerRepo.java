package warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import warehouse.entities.RemainderInContainer;

public interface RemainderInContainerRepo extends JpaRepository<RemainderInContainer, Long> {

}
