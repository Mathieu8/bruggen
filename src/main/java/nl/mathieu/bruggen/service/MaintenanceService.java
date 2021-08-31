package nl.mathieu.bruggen.service;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.model.Maintenance;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MaintenanceService {
    Mono<Maintenance> saveMaintenance(Maintenance maintenance);

    Flux<Maintenance> findAll();

    Mono<Maintenance> findById(Mono<Integer>  anyInt);
}
