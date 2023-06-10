package com.congdat.springbootrestapi.service;

import com.congdat.springbootrestapi.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
