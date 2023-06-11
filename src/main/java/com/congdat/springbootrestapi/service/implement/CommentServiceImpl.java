package com.congdat.springbootrestapi.service.implement;

import com.congdat.springbootrestapi.entity.Comment;
import com.congdat.springbootrestapi.entity.Post;
import com.congdat.springbootrestapi.exception.ResourceNotFoundException;
import com.congdat.springbootrestapi.payload.CommentDto;
import com.congdat.springbootrestapi.repository.CommentRepository;
import com.congdat.springbootrestapi.repository.PostRepository;
import com.congdat.springbootrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }
}
