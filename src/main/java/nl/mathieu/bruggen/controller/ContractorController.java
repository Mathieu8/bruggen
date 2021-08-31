package nl.mathieu.bruggen.controller;

import nl.mathieu.bruggen.model.Maintenance;
import nl.mathieu.bruggen.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/api/v1/contractor/")
public class ContractorController {
    private final MaintenanceService maintenanceService;

    public ContractorController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }


    @GetMapping
    public ResponseEntity<Flux<Maintenance>> findAll() {
        Flux<Maintenance> maintenanceFlux = maintenanceService.findAll();
        return ResponseEntity.ok(maintenanceFlux);
    }

}
