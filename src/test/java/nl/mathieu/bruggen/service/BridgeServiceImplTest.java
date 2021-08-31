package nl.mathieu.bruggen.service;

import nl.mathieu.bruggen.model.Bridge;
import nl.mathieu.bruggen.repository.BridgeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BridgeServiceImplTest {
    @Mock
    BridgeRepository bridgeRepository;

    BridgeService bridgeService;

    private final int id1 = 1;
    private final int id2 = 2;
    private final String name1 = "bridge1";
    private final String name2 = "bridge2";
    private final Bridge bridge1 = Bridge.builder().id(id1).name( name1).build();
    private final Bridge bridge2 = Bridge.builder().id(id2).name( name2).build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bridgeService = new BridgeServiceImpl(bridgeRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }
}