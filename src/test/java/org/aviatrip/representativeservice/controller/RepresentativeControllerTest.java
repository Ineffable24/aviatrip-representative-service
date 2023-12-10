package org.aviatrip.representativeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aviatrip.representativeservice.dto.mapper.AirplaneMapper;
import org.aviatrip.representativeservice.dto.request.AirplanePassengerSectionRequestTest;
import org.aviatrip.representativeservice.dto.request.AirplaneRequestTest;
import org.aviatrip.representativeservice.service.airplanemanagement.AirplaneService;
import org.aviatrip.representativeservice.service.airplanemanagement.AirplaneValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirplaneManagementController.class)
class RepresentativeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AirplaneService airplaneService;
    @MockBean
    private AirplaneValidator airplaneValidator;
    @MockBean
    private AirplaneMapper airplaneMapper;

    @Autowired
    AirplaneManagementController controller;

    @Test
    void givenAirplaneDataWhenSentThenResponseStatusIsOk() throws Exception {

        AirplaneRequestTest airplane = AirplaneRequestTest.builder()
                .model("Model")
                .sections(
                        List.of(
                                AirplanePassengerSectionRequestTest.builder()
                                        .seatClass("First class")
                                        .seatCount(12)
                                        .seatRowCount(3)
                                        .build(),
                                AirplanePassengerSectionRequestTest.builder()
                                        .seatClass("Economy")
                                        .seatCount(50)
                                        .seatRowCount(5)
                                        .build()
                        )
                )
                .build();

        String jsonBodyData = new ObjectMapper().writeValueAsString(airplane);

         mockMvc.perform(post("/company/airplanes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBodyData)
                .header("subject", "2a7afe6e-b729-4991-9362-2f78a0b3b9f4"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void givenInvalidAirplaneDataWhenSentThenResponseStatusIsBadRequest() throws Exception {

        AirplaneRequestTest airplane = AirplaneRequestTest.builder()
                .model("Model")
                .sections(
                        List.of(
                                AirplanePassengerSectionRequestTest.builder()
                                        .seatClass("Firstclass")
                                        .seatCount(12)
                                        .seatRowCount(5)
                                        .build(),
                                AirplanePassengerSectionRequestTest.builder()
                                        .seatClass("Economy")
                                        .seatCount(50)
                                        .seatRowCount(5)
                                        .build()
                        )
                )
                .build();

        String jsonBodyData = new ObjectMapper().writeValueAsString(airplane);

        mockMvc.perform(post("/company/airplanes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBodyData)
                        .header("subject", "2a7afe6e-b729-4991-9362-2f78a0b3b9f4"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}