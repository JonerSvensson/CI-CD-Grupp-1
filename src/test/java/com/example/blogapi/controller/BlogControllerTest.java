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
                "My title",
                "Some body text",
                "Anna",
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("GET all returns 200 and a list")
    void getAll_returns200() throws Exception {
        when(blogService.findAll()).thenReturn(List.of(blog));

        mockMvc.perform(get("/api/blogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("My title"))
                .andExpect(jsonPath("$[0].author").value("Anna"));

        verify(blogService).findAll();
    }

    @Test
    @DisplayName("GET all returns 200 even when the list is empty")
    void getAll_emptyList_returns200() throws Exception {
        when(blogService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/blogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(blogService).findAll();
    }

    @Test
    @DisplayName("GET by id returns 200 when the post exists")
    void getById_found_returns200() throws Exception {
        when(blogService.findById(1L)).thenReturn(blog);

        mockMvc.perform(get("/api/blogs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("My title"))
                .andExpect(jsonPath("$.author").value("Anna"));

        verify(blogService).findById(1L);
    }

    @Test
    @DisplayName("GET by id returns 404 when the post is missing")
    void getById_notFound_returns404() throws Exception {
        when(blogService.findById(99L))
                .thenThrow(ResourceNotFoundException.blog(99L));

        mockMvc.perform(get("/api/blogs/99"))
                .andExpect(status().isNotFound());

        verify(blogService).findById(99L);
    }

    @Test
    @DisplayName("POST returns 201 and a Location header")
    void create_valid_returns201() throws Exception {
        Blog input = new Blog(
                null,
                "New title",
                "New text",
                "Bo",
                null
        );

        when(blogService.create(any(Blog.class))).thenReturn(blog);

        mockMvc.perform(post("/api/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        endsWith("/api/blogs/1")
                ))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("My title"))
                .andExpect(jsonPath("$.author").value("Anna"));

        verify(blogService).create(any(Blog.class));
    }

    @Test
    @DisplayName("POST with a blank title returns 400")
    void create_blankTitle_returns400() throws Exception {
        Blog invalid = new Blog(
                null,
                "",
                "New text",
                "Bo",
                null
        );

        mockMvc.perform(post("/api/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        verify(blogService, never()).create(any());
    }

    @Test
    @DisplayName("PUT returns 200 when the post exists")
    void update_found_returns200() throws Exception {
        Blog input = new Blog(
                null,
                "Updated",
                "New text",
                "Anna",
                null
        );

        Blog updatedBlog = new Blog(
                1L,
                "Updated",
                "New text",
                "Anna",
                LocalDateTime.now()
        );

        when(blogService.update(eq(1L), any(Blog.class)))
                .thenReturn(updatedBlog);

        mockMvc.perform(put("/api/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated"))
                .andExpect(jsonPath("$.body").value("New text"))
                .andExpect(jsonPath("$.author").value("Anna"));

        verify(blogService).update(eq(1L), any(Blog.class));
    }

    @Test
    @DisplayName("PUT returns 404 when the post is missing")
    void update_notFound_returns404() throws Exception {
        Blog input = new Blog(
                null,
                "Updated",
                "New text",
                "Anna",
                null
        );

        when(blogService.update(eq(99L), any(Blog.class)))
                .thenThrow(ResourceNotFoundException.blog(99L));

        mockMvc.perform(put("/api/blogs/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());

        verify(blogService).update(eq(99L), any(Blog.class));
    }

    @Test
    @DisplayName("PUT with a blank title returns 400 and never calls the service")
    void update_blankTitle_returns400() throws Exception {
        Blog invalid = new Blog(
                null,
                "",
                "New text",
                "Anna",
                null
        );

        mockMvc.perform(put("/api/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        verify(blogService, never())
                .update(anyLong(), any(Blog.class));
    }

    @Test
    @DisplayName("DELETE returns 204 with no body")
    void delete_found_returns204() throws Exception {
        doNothing().when(blogService).delete(1L);

        mockMvc.perform(delete("/api/blogs/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(blogService).delete(1L);
    }

    @Test
    @DisplayName("DELETE returns 404 when the post is missing")
    void delete_notFound_returns404() throws Exception {
        doThrow(ResourceNotFoundException.blog(99L))
                .when(blogService)
                .delete(99L);

        mockMvc.perform(delete("/api/blogs/99"))
                .andExpect(status().isNotFound());

        verify(blogService).delete(99L);
    }
}