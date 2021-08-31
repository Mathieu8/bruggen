package nl.mathieu.bruggen.service;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.model.Maintenance;
import nl.mathieu.bruggen.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final BridgeService bridgeService;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, BridgeService bridgeService) {
        this.maintenanceRepository = maintenanceRepository;
        this.bridgeService = bridgeService;
    }

    @Override
    public Mono<Maintenance> saveMaintenance(Maintenance maintenance) {
        Mono<Bridge> bridge = bridgeService.findById(maintenance.getBridgeId());
        bridge.subscribe(b -> b.getMaintenance().add(maintenance));
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Flux<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Mono<Maintenance> findById(Mono<Integer>  id) {
        return maintenanceRepository.findById(id);
    }
}
