package com.congdat.springbootrestapi.service;

import com.congdat.springbootrestapi.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
