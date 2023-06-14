package com.congdat.springbootrestapi.service;

import com.congdat.springbootrestapi.entity.Post;
import com.congdat.springbootrestapi.payload.PostDto;
import com.congdat.springbootrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String orderBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);
}
