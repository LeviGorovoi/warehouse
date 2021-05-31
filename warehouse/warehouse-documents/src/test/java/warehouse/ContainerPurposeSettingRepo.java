package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.ContainerPurposeSetting;

public interface ContainerPurposeSettingRepo extends ReactiveMongoRepository<ContainerPurposeSetting, ObjectId> {

}
