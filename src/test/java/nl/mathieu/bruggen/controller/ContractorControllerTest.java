package nl.mathieu.bruggen.controller;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.model.Maintenance;
import nl.mathieu.bruggen.service.MaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest
    (controllers = ContractorController.class)
@ExtendWith(SpringExtension.class)
class ContractorControllerTest {
    private final int id1 = 1;
    private final int id2 = 2;
    private final String name1 = "bridge1";
    private final String name2 = "bridge2";
    private final Bridge bridge1 = Bridge.builder().id(id1).name(name1).build();
    private final Bridge bridge2 = Bridge.builder().id(id2).name(name2).build();
    private final Maintenance maintenance1 = Maintenance.builder().id(id1).bridgeId(bridge1.getId()).priority(1).build();
    private final Maintenance maintenance2 = Maintenance.builder().id(id2).bridgeId(bridge2.getId()).priority(0).build();


    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private MaintenanceService maintenanceService;

    ContractorController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        controller = new ContractorController(maintenanceService);
    }

    @Test
    @DisplayName("findAll; GIVEN request for all maintenance THEN return all")
    void findAll() {
        when(maintenanceService.findAll()).thenReturn(Flux.just(maintenance1, maintenance2));

        var temp = webTestClient.get().uri("/api/v1/contractor/");
        temp.exchange().expectStatus().isOk();
        temp.exchange().expectBody().jsonPath("$.Maintenance[0].id", id1);
        temp.exchange().expectBody().jsonPath("$.Maintenance[1].id", id2);

        verify(maintenanceService, times(3)).findAll();

    }

    @Test
    @DisplayName("findAll; GIVEN request for all maintenance WHEN there are no maintenance THEN return empty list")
    void findAll_emptyList() {
        when(maintenanceService.findAll()).thenReturn(Flux.empty());

        var temp = webTestClient.get().uri("/api/v1/contractor/");
        temp.exchange().expectStatus().isOk();
        temp.exchange().expectBody().jsonPath("$.Maintenance", null);

        verify(maintenanceService, times(2)).findAll();
    }

    @Test
    @DisplayName("GIVEN valid request to finish maintenance THEN closes maintenance")
    void closeMaintenance() {
        when(maintenanceService.findById(Mono.just(1))).thenReturn(Mono.just(maintenance1));
//        doNothing().when(Maintenance)

        var temp = webTestClient.patch().uri("/api/v1/contractor/");
//        temp.exchange().expectStatus().(); //TODO
        temp.exchange().expectBody().jsonPath("$.Maintenance.isDone", true);

        verify(maintenanceService, times(2)).findById(ArgumentMatchers.any());


    }

    @Test
    @DisplayName("GIVEN WHEN THEN")
    void closeMaintenance_() {

    }

    @Test
    @DisplayName("GIVEN request to see a maintenance THEN maintenance")
    void seeMaintenance() {

    }
}