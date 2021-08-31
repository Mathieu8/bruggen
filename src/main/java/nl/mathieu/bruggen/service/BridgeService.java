package nl.mathieu.bruggen.service;

import nl.mathieu.bruggen.model.Bridge;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BridgeService {
    Flux<Bridge> findAll();
    Mono<Bridge> findById(Integer id);
}
