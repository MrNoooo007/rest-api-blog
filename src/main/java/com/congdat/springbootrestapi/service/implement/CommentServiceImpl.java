package com.congdat.springbootrestapi.service.implement;

import com.congdat.springbootrestapi.entity.Comment;
import com.congdat.springbootrestapi.entity.Post;
import com.congdat.springbootrestapi.exception.BlogApiException;
import com.congdat.springbootrestapi.exception.ResourceNotFoundException;
import com.congdat.springbootrestapi.payload.CommentDto;
import com.congdat.springbootrestapi.repository.CommentRepository;
import com.congdat.springbootrestapi.repository.PostRepository;
import com.congdat.springbootrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private ModelMapper mapper;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postExist(postId);
        Comment comment = commentExist(commentId);
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postExist(postId);
        Comment comment = commentExist(commentId);
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postExist(postId);
        Comment comment = commentExist(commentId);
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }
        commentRepository.delete(comment);
    }

    private Post postExist(Long postId) {
        return postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment commentExist(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment", "id", commentId));
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
        return mapper.map(comment, CommentDto.class);
    }
}
