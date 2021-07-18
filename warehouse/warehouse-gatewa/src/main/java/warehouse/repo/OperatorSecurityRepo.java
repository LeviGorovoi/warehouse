package warehouse.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.dto.security.UserDataWithoutRoles;
import warehouse.entities.Operator;

public interface OperatorSecurityRepo extends JpaRepository<Operator, Long> {

	Operator findByOperatorName(String operatorName);

	@Modifying
	@Query("update Operator o set o.username=:username, o.password=:password, o.passwordExpiration=:password_expiration"
			+ " where o.operatorName=:operator_name")
	int register(@Param("operator_name") String operatorName, String username, String password,
			@Param("password_expiration") LocalDate passwordExpiration);
	
	@Query("select o.username as username, o.password as password, "
			+ "o.passwordExpiration as passwordExpiration  from Operator o where o.username=:username")
	UserDataWithoutRoles getUserData(String username);
	
	@Query("select r.role from Role r where r.operator.username=:username")
	String[] getRoles(String username);

	@Modifying
	@Query("update Operator o set o.password=:new_password, o.passwordExpiration=:password_expiration where o.username=:username")
	int updatePassword(String username, @Param("new_password")String newPassword, @Param("password_expiration") LocalDate passwordExpiration);
	
	@Query("select o.password from Operator o where o.username=:username")
	String findPasswordByUsername(String username);

	@Query("select o.operatorId from Operator o where o.username=:username")
	long findOperatorIdByUsername(String username);


}
