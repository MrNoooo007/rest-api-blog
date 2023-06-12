package com.congdat.springbootrestapi.service;

import com.congdat.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);
}
