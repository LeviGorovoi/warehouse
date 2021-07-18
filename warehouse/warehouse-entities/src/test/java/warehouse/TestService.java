package warehouse;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import warehouse.entities.Operator;
import warehouse.entities.Role;

@Service
public class TestService {
	@Autowired
	OperatorRepo operatorRepo;
	@Autowired
	RoleRepo roleRepo;
	
	@Transactional
	public void createAndSaveOperator(String role, String email, String name) {
	Set<Role> roles = new HashSet<Role>();
	Role operatorRoleWithId = roleRepo.findByRole(role);
	roles.add(operatorRoleWithId);
	Operator operator = Operator.builder().operatorEmail(email).operatorName(name)
			.roles(roles).build();
	operatorRepo.save(operator);
	}
	@Transactional
	public void setOperatorToRole(String role, Operator operator) {
	roleRepo.setOperatorToRole(role, operator);
}
}