package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.ParentDto;
import warehouse.dto.container.*;

import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.*;
import warehouse.repo.ContainerStateBackOficeRepo;
import warehouse.repo.OperatorStateBackOficeRepo;
import warehouse.repo.ProductStateBackOficeRepo;
import warehouse.repo.RoleStateBackOficeRepo;
import warehouse.service.interfaces.StateBackOficeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.junit.jupiter.api.*;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StateBackOficeTests {


}
