package com.example.blogapi.controller;

import com.example.blogapi.model.Blog;
import com.example.blogapi.service.BlogService;
import com.example.blogapi.service.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BlogService blogService;

    private Blog blog;

    @BeforeEach
    void setUp() {
        blog = new Blog(
                1L,
                "Min titel",
                "text här",
                "Anna",
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("GET alla returnerar 200 och en lista")
    void getAll_returns200() throws Exception {
        when(blogService.findAll()).thenReturn(List.of(blog));

        mockMvc.perform(get("/api/blogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Min titel"))
                .andExpect(jsonPath("$[0].author").value("Anna"));

        verify(blogService).findAll();
    }
}