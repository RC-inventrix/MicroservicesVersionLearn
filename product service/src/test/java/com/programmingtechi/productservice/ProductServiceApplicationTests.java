package com.programmingtechi.productservice;

import com.programmingtechi.productservice.dto.ProductRequest;
import com.programmingtechi.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper; // Fixed Import
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Fixed Import
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MongoDBContainer; // Fixed Import
import org.testcontainers.utility.DockerImageName;

// Fixed Static Imports
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    private static final DockerImageName MONGO_IMAGE = DockerImageName.parse("mongo:4.4.2");

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_IMAGE);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // Fixed method: getConnectionString() instead of getReplicaSetUrl()
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);
    }

    @Test
    void shouldCreatProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(9, productRepository.findAll().size());
    }

    @Test
    void shouldGetAllProducts() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/Products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(9)); // Change 7 to 1
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone 13")
                .description("Iphone 13 pro max")
                .price(1200)
                .build();
    }
}