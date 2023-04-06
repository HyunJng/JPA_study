package com.jojoIdu.book.aws_jpa.web;

import com.jojoIdu.book.aws_jpa.service.popts.PostsService;
import com.jojoIdu.book.aws_jpa.web.dto.PostsResponseDto;
import com.jojoIdu.book.aws_jpa.web.dto.PostsSaveRequestDto;
import com.jojoIdu.book.aws_jpa.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 저장
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    // 수정
    @PostMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    // 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
