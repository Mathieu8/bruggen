package nl.mathieu.bruggen.controller;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.model.Maintenance;
import nl.mathieu.bruggen.repository.BridgeRepository;
import nl.mathieu.bruggen.service.BridgeService;
import nl.mathieu.bruggen.service.MaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest
    (controllers = MaintenanceController.class)
@ExtendWith(SpringExtension.class)
class MaintenanceControllerTest {
    private final int id1 = 1;
    private final int id2 = 2;
    private final String name1 = "bridge1";
    private final String name2 = "bridge2";
    private final Bridge bridge1 = Bridge.builder().id(id1).name(name1).build();
    private final Bridge bridge2 = Bridge.builder().id(id2).name(name2).build();

    @MockBean
    BridgeRepository bridgeRepository;

    @Autowired
    WebTestClient webTestClient;//= WebTestClient.bindToServer().build();

    @MockBean
    private BridgeService bridgeService;

    @MockBean
    private MaintenanceService maintenanceService;

    MaintenanceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        controller = new MaintenanceController(bridgeService, maintenanceService);
    }


    @Test
    void getListOfBridges() throws Exception {
        Bridge[] bruggen = {bridge1, bridge2};
        when(bridgeService.findAll())
            .thenReturn(Flux.fromArray(bruggen));

        var temp = webTestClient.get().uri("/api/v1/bridge");
        temp.exchange().expectStatus().isOk();
        temp.exchange().expectBody().jsonPath("$.bridge", hasSize(2));
        temp.exchange().expectBody().jsonPath("$.bridge[0].id", equalTo(id1));
        temp.exchange().expectBody().jsonPath("$.bridge[0].name", equalTo(name1));
        temp.exchange().expectBody().jsonPath("$.bridge[1].id", equalTo(id2));
        temp.exchange().expectBody().jsonPath("$.bridge[1].name", equalTo(name2));

        verify(bridgeService, times(6)).findAll();
    }

    @Test
    void getById() {
        when(bridgeService.findById(1)).thenReturn(Mono.just(bridge1));

        var temp = webTestClient.get().uri("/api/v1/bridge/1");
        temp.exchange().expectStatus().isOk();
        temp.exchange().expectBody().jsonPath("$.bridge.id", equalTo(id1));
        temp.exchange().expectBody().jsonPath("$.bridge.name", equalTo(name1));

        verify(bridgeService, times(3)).findById(anyInt());
    }

    @Test
    @DisplayName("createMaintenance: GIVEN maintenance doesn't exist THEN creates")
    void createMaintenance() {
        Maintenance maintenance = new Maintenance(3, bridge1.getId(), 4, LocalTime.now(), false);

//        when(bridgeService.findById(3)).thenReturn(Mono.just(bridge1));
        when(maintenanceService.saveMaintenance(any(Maintenance.class))).thenReturn(Mono.just(maintenance));

        webTestClient.post().uri("/api/v1/bridge/3/maintenance")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(maintenance), Maintenance.class)
            .exchange()
            .expectStatus().isCreated()//.isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.id").isEqualTo(3)
            .jsonPath("$.priority").isEqualTo(4)
            .jsonPath("$.isDone").isEqualTo(false)
            .jsonPath("$.bridgeId").isEqualTo(bridge1.getId());

        verify(maintenanceService).saveMaintenance(maintenance);
    }
}