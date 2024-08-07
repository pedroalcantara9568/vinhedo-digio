package com.example.vinhedo_digio.controller;

import com.example.vinhedo_digio.model.MaioresCompras;
import com.example.vinhedo_digio.service.ComprasService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ComprasController.class, ComprasService.class})
@EnableWebMvc
@TestPropertySource(properties = {
        "server.port=8082"
})
class ComprasControllerTest {

    private static final EasyRandom RANDOM = new EasyRandom();
    public static final String COMPRAS_123 = "/compras/123";
    public static final String COMPRAS = "/compras";

    private MockMvc mockMvc;
    @MockBean
    private ComprasService service;

    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(
                        (request, response, chain) -> {
                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            chain.doFilter(request, response);
                        }, "/*").build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void obterClientesFieis() throws Exception {
        var expected = List.of(RANDOM.nextObject(MaioresCompras.class));

        when(service.obterMaioresCompras()).thenReturn(expected);

        ResultActions res = mockMvc.perform(
                get(COMPRAS).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        var actual = mapper.readValue(res.andReturn().getResponse().getContentAsString(), new TypeReference<List<MaioresCompras>>() {
        });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void obterRecomendacao() throws Exception {
        var expected = RANDOM.nextObject(MaioresCompras.class);

        when(service.obterMaiorCompraPorAno(any(Long.class))).thenReturn(expected);

        ResultActions res = mockMvc.perform(
                get(COMPRAS_123).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        var actual = mapper.readValue(res.andReturn().getResponse().getContentAsString(), MaioresCompras.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}