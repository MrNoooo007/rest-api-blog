package com.congdat.springbootrestapi.service.implement;

import com.congdat.springbootrestapi.entity.Post;
import com.congdat.springbootrestapi.payload.PostDto;
import com.congdat.springbootrestapi.repository.PostRepository;
import com.congdat.springbootrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());

        Post savedPost = postRepository.save(post);

        PostDto postResponse = new PostDto();
        postResponse.setId(savedPost.getId());
        postResponse.setTitle(savedPost.getTitle());
        postResponse.setContent(savedPost.getContent());
        postResponse.setDescription(savedPost.getDescription());

        return postResponse;
    }
}
