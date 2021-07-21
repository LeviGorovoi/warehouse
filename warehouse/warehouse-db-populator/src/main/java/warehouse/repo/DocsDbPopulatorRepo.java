package warehouse.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import warehouse.doc.WarehoseDoc;

public interface DocsDbPopulatorRepo extends ReactiveMongoRepository<WarehoseDoc, ObjectId> {

}
