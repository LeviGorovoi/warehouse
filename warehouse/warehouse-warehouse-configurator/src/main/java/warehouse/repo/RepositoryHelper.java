package warehouse.repo;
public interface RepositoryHelper {

	<G> int setGraftToRecipient(String recipient, G graft);

}
