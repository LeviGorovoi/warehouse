package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.TransportSupplySetting;

public interface TransportSupplySettingRepo extends ReactiveMongoRepository<TransportSupplySetting, ObjectId> {

}
