package com.reservation;

import com.reservation.config.TestContainerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(TestContainerConfig.class)
@Transactional
@SpringBootTest
public class IntegrationTestSupport {

}
