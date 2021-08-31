package nl.mathieu.bruggen.service;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.repository.BridgeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BridgeServiceImpl implements BridgeService {
    private final BridgeRepository bridgeRepository;

    public BridgeServiceImpl(BridgeRepository bridgeRepository) {
        this.bridgeRepository = bridgeRepository;
    }

    public Flux<Bridge> findAll() {
        return bridgeRepository.findAll();
    }

    public Mono<Bridge> findById(Integer id){
        return bridgeRepository.findById(id);
    }

}
