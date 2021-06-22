package warehouse.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import warehouse.entities.Operator;

public interface OperatorSecurityRepo extends JpaRepository<Operator, Long> {

	Operator findByOperatorName(String operatorName);

	@Modifying
	@Query("update Operator o set o.userName=:user_name, o.password=:password, o.passwordExpiration=:password_expiration where o.operatorName=:operator_name")
	int register(@Param("operator_name") String operatorName, @Param("user_name") String userName, String password,
			@Param("password_expiration") LocalDate passwordExpiration);

}
