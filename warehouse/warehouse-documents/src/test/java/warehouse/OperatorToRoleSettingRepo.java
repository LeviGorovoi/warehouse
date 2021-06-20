package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.OperatorToRoleSetting;

public interface OperatorToRoleSettingRepo extends ReactiveMongoRepository<OperatorToRoleSetting, ObjectId> {

}
