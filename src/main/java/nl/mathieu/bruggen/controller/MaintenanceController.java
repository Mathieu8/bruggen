package nl.mathieu.bruggen.controller;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.model.Maintenance;
import nl.mathieu.bruggen.service.BridgeService;
import nl.mathieu.bruggen.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequestMapping("/api/v1/bridge")
public class MaintenanceController {

    private final BridgeService bridgeService;
    private final MaintenanceService maintenanceService;

    private WebDataBinder webDataBinder;

    public MaintenanceController(BridgeService bridgeService, MaintenanceService maintenanceService) {
        this.bridgeService = bridgeService;
        this.maintenanceService = maintenanceService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping
    public ResponseEntity<Flux<Bridge>> findAll() {
        Flux<Bridge> bridges = bridgeService.findAll();
        return ResponseEntity.ok(bridges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Bridge>> getBrug(@PathVariable int id) {
        Mono<Bridge> bridge = bridgeService.findById(id);
        return ResponseEntity.ok(bridge);
    }

    @PostMapping("/{id}/maintenance")
    public ResponseEntity<Maintenance> addMaintaince(@PathVariable int id, @ModelAttribute("Maintenance") Maintenance maintenance) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
        }

        maintenance.setBridgeId(id);

        Mono<Maintenance> savedMaintenance = maintenanceService.saveMaintenance(maintenance);
        //TODO block weghalen
        return ResponseEntity.status(CREATED).body(savedMaintenance.block());
    }
}
