package com.example.blogapi.service;

import com.example.blogapi.model.Blog;
import com.example.blogapi.repository.BlogRepository;

import java.util.List;

public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

    public Blog findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.blog(id));
    }

    public Blog create(Blog blog){
        return blogRepository.save(blog);
    }

    public Blog update(Long id, Blog updatedBlog){
        return blogRepository.update(id, updatedBlog)
                .orElseThrow(() -> ResourceNotFoundException.blog(id));
    }

    public void delete(Long id){
        Blog existing = blogRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.blog(id));

        blogRepository.deleteById(existing.getId());
    }
}