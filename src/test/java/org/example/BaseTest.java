package org.example;

import org.example.configuration.WebSocketConfiguration;
import org.example.message.handler.MultipleMessageParser;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import jakarta.websocket.server.ServerContainer;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class BaseTest {
    @TestConfiguration
    protected static class TestConfig {
        @Bean
        protected ServerEndpointExporter serverEndpointExporter() {
            return Mockito.mock(ServerEndpointExporter.class);
        }

        @Bean
        protected ServerContainer serverContainer() {
            return Mockito.mock(ServerContainer.class);
        }
    }
}
