package com.congdat.springbootrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "at 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "at 10")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
