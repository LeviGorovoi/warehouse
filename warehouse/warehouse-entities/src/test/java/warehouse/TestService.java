package warehouse;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import warehouse.entities.Operator;
import warehouse.entities.OperatorRole;

@Service
public class TestService {
	@Autowired
	OperatorRepo operatorRepo;
	@Autowired
	OperatorRoleRepo operatorRoleRepo;
	
	@Transactional
	public void createAndSaveOperator(String role, String email, String name) {
	Set<OperatorRole> operatorRoles = new HashSet<OperatorRole>();
	OperatorRole operatorRoleWithId = operatorRoleRepo.findByRole(role);
	operatorRoles.add(operatorRoleWithId);
	Operator operator = Operator.builder().operatorEmail(email).operatorName(name)
			.operatorRoles(operatorRoles).build();
	operatorRepo.save(operator);
	}
	@Transactional
	public void setOperatorToRole(String role, Operator operator) {
	operatorRoleRepo.setOperatorToRole(role, operator);
}
}