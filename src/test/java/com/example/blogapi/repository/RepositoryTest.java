package com.example.blogapi.repository;

import com.example.blogapi.model.Blog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private BlogRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BlogRepository();
    }

    @Test
    void findAll_returnsMockData() {
        List<Blog> posts = repository.findAll();
        assertEquals(2, posts.size());
    }

    @Test
    void save_addsNewPostWithId() {
        Blog newPost = new Blog("Test", "Innehåll", "Testare");
        Blog saved = repository.save(newPost);

        assertNotNull(saved.getId());
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void findById_returnsCorrectPost() {
        Optional<Blog> found = repository.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("Första inlägget", found.get().getTitle());
    }

    @Test
    void deleteById_removesPost() {
        boolean deleted = repository.deleteById(1L);
        assertTrue(deleted);
        assertEquals(1, repository.findAll().size());
    }
}