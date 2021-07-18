package warehouse.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.Operator;


public interface OperatorWarehouseConfiguratorRepo extends JpaRepository<Operator, Long> {
	Operator findByOperatorName(@Param("operator_name") String operator);
	
	@Query("select o from Operator o where o.operatorName=:operator_name")
	Operator findOperatorWithRoles(@Param("operator_name") String operator);
	
	@Query("select r.role from Role r join r.operator o where o.operatorName=:operator_name")
	List<String> getRolesFromOperator(@Param("operator_name") String operatorName);

	@Query("select o.operatorId from Operator o where o.username=:username")
	long findOperatorIdByUsername(String username);

	@Modifying
	@Query("update Operator o set o.operatorName=:new_operator_name where o.operatorId=:operator_id")
	int changeOperatorName(@Param("operator_id") long operatorId, @Param("new_operator_name") String newOperatorName);

	@Modifying
	@Query("update Operator o set o.operatorEmail=:new_operator_email where o.operatorId=:operator_id")
	int changeOperatorEmail(@Param("operator_id") long operatorId, @Param("new_operator_email") String newOperatorEmail);

	

}
