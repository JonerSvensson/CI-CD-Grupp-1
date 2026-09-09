package com.example.blogapi.service;

import com.example.blogapi.Service.BlogService;
import com.example.blogapi.Service.ResourceNotFoundException;
import com.example.blogapi.model.Blog;
import com.example.blogapi.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlogServiceTest {

    private BlogRepository blogRepository;
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        blogRepository = Mockito.mock(BlogRepository.class);
        blogService = new BlogService(blogRepository);
    }

    @Test
    void findAll_returnsListOfBlogs() {
        Blog blog = new Blog(1L, "Title", "Body", "Author", LocalDateTime.now());
        when(blogRepository.findAll()).thenReturn(List.of(blog));

        List<Blog> result = blogService.findAll();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    void findById_returnsBlog_whenExists() {
        Blog blog = new Blog(1L, "Title", "Body", "Author", LocalDateTime.now());
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        Blog result = blogService.findById(1L);

        assertEquals("Title", result.getTitle());
    }

    @Test
    void findById_throwsException_whenNotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> blogService.findById(1L));
    }

    @Test
    void create_savesBlog() {
        Blog blog = new Blog(1L, "Title", "Body", "Author", LocalDateTime.now());
        when(blogRepository.save(blog)).thenReturn(blog);

        Blog result = blogService.create(blog);

        assertEquals("Title", result.getTitle());
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void update_updatesExistingBlog() {
        Blog existing = new Blog(1L, "Old", "OldBody", "OldAuthor", LocalDateTime.now());
        Blog updated = new Blog(1L, "New", "NewBody", "NewAuthor", LocalDateTime.now());

        when(blogRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(blogRepository.update(existing)).thenReturn(existing);

        Blog result = blogService.update(1L, updated);

        assertEquals("New", result.getTitle());
        assertEquals("NewBody", result.getBody());
        assertEquals("NewAuthor", result.getAuthor());
    }

    @Test
    void update_throwsException_whenNotFound() {
        Blog updated = new Blog(1L, "New", "NewBody", "NewAuthor", LocalDateTime.now());
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> blogService.update(1L, updated));
    }

    @Test
    void delete_removesBlog() {
        Blog existing = new Blog(1L, "Title", "Body", "Author", LocalDateTime.now());
        when(blogRepository.findById(1L)).thenReturn(Optional.of(existing));

        blogService.delete(1L);

        verify(blogRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_throwsException_whenNotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> blogService.delete(1L));
    }
}
