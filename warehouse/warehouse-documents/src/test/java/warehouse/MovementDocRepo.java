package warehouse;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.MovementDoc;

public interface MovementDocRepo extends ReactiveMongoRepository<MovementDoc, ObjectId> {

}
