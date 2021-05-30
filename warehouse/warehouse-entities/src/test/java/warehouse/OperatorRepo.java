package warehouse;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.Operator;


public interface OperatorRepo extends JpaRepository<Operator, Long> {
	Operator findByOperatorName(@Param("operator_name") String operator);
	
	@Query("select o from Operator o where o.operatorName=:operator_name")
	Operator findOperatorWithRoles(@Param("operator_name") String operator);
	
	@Query("select r.role from OperatorRole r join r.operator o where o.operatorName=:operator_name")
	Set<String> getOperatorRolesFromOperator(@Param("operator_name") String operatorName);

}
