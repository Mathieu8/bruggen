package nl.mathieu.bruggen.repository;

import nl.mathieu.bruggen.model.Bridge;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BridgeRepository extends ReactiveCrudRepository<Bridge, Integer> {
}
