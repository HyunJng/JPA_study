package com.jojoIdu.book.aws_jpa.web.dto;

import com.jojoIdu.book.aws_jpa.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

/* 안 쓴 이유? 이렇게 하면 author에는 기본 값이 들어가게 되서?
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .build();
    }*/
}
