package warehouse.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import warehouse.entities.Operator;
import warehouse.entities.OperatorRole;


public interface OperatorRoleStateBackOficeRepo extends JpaRepository<OperatorRole, Long> {

	OperatorRole findByRole(String role);
	
@Query("select r.operator from OperatorRole r where r.role=:role")
	Set<Operator> getOperatorsFromOperatorRole(String role);

@Modifying
@Query("update OperatorRole r set r.operator=:operator where r.role=:role")
void setOperatorToRole(String role, Operator operator);


}
