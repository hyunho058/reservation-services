package com.reservation.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.application.performance.PerformanceService;
import com.reservation.application.performance.ReservationService;
import com.reservation.application.user.UserService;
import com.reservation.presentation.performance.BusinessReservationController;
import com.reservation.presentation.performance.CustomerReservationController;
import com.reservation.presentation.performance.PerformanceController;
import com.reservation.presentation.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        UserController.class,
        PerformanceController.class,
        CustomerReservationController.class,
        BusinessReservationController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected PerformanceService performanceService;

    @MockBean
    protected ReservationService reservationService;
}
