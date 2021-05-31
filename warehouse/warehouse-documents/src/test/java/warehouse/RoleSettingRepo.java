package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.RoleSetting;

public interface RoleSettingRepo extends ReactiveMongoRepository<RoleSetting, ObjectId> {

}
