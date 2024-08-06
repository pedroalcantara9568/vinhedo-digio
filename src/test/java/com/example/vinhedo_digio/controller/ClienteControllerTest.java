package com.example.vinhedo_digio.controller;

import com.example.vinhedo_digio.model.MaioresComprasDTO;
import com.example.vinhedo_digio.model.Produto;
import com.example.vinhedo_digio.service.ClienteService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.lang.model.type.ReferenceType;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ClienteController.class, ClienteService.class})
@EnableWebMvc
@TestPropertySource(properties = {
        "server.port=8082"
})
class ClienteControllerTest {

    private static final EasyRandom RANDOM = new EasyRandom();
    public static final String RECOMENDACAO_CLIENTE_TIPO_123 = "/recomendacao/cliente/tipo/123";
    public static final String CLIENTES_FIEIS = "/clientes-fieis";

    private MockMvc mockMvc;
    @MockBean
    private ClienteService service;

    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
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
        var expected = List.of(RANDOM.nextObject(MaioresComprasDTO.class));

        when(service.obterClientesFieis()).thenReturn(expected);

        ResultActions res = mockMvc.perform(
                get(CLIENTES_FIEIS).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        var actual = mapper.readValue(res.andReturn().getResponse().getContentAsString(), new TypeReference<List<MaioresComprasDTO>>(){});

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void obterRecomendacao() throws Exception {
        var expected = RANDOM.nextObject(Produto.class);

        when(service.obterRecomendacao(any(String.class))).thenReturn(expected);

        ResultActions res = mockMvc.perform(
                get(RECOMENDACAO_CLIENTE_TIPO_123).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        var actual = mapper.readValue(res.andReturn().getResponse().getContentAsString(), Produto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}