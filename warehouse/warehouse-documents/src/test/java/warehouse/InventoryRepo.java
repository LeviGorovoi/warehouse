package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.Inventory;

public interface InventoryRepo extends ReactiveMongoRepository<Inventory, ObjectId> {

}
