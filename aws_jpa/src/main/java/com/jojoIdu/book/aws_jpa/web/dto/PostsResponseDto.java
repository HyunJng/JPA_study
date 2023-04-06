package com.jojoIdu.book.aws_jpa.web.dto;

import com.jojoIdu.book.aws_jpa.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) { // Entity가 DB접근해서 받아온 것이므로
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
    }
}
