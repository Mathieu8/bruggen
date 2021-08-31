package nl.mathieu.bruggen.repository;

import nl.mathieu.bruggen.model.Maintenance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MaintenanceRepository extends ReactiveCrudRepository<Maintenance, Integer> {
}
