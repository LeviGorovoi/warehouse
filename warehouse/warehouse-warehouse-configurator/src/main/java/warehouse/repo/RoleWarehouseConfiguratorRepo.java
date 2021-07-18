package warehouse.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.Operator;
import warehouse.entities.Role;

public interface RoleWarehouseConfiguratorRepo extends JpaRepository<Role, Long> {

	Role findByRole(String role);

	@Query("select r.operator from Role r where r.role=:role")
	Operator getOperatorsFromRole(String role);

	@Modifying
	@Query("update Role r set r.operator=:operator where r.roleId=:role_id")
	int setOperatorToRole(@Param("role_id") long roleId, Operator operator);

	@Modifying
	@Query("update Role r set r.role=:new_role where r.roleId=:role_id")
	int changeRole(@Param("role_id") long roleId, @Param("new_role") String newRole);

}
