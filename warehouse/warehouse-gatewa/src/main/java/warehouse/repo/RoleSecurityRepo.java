package warehouse.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import warehouse.entities.Operator;
import warehouse.entities.Role;


public interface RoleSecurityRepo extends JpaRepository<Role, Long> {

	Role findByRole(String role);
	
@Query("select r.operator from Role r where r.role=:role")
	Operator getOperatorsFromOperatorRole(String role);

@Modifying
@Query("update Role r set r.operator=:operator where r.role=:role")
void setOperatorToRole(String role, Operator operator);


}
