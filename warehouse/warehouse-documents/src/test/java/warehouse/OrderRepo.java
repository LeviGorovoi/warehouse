package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.Order;

public interface OrderRepo extends ReactiveMongoRepository<Order, ObjectId> {

}
