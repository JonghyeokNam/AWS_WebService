package com.jojoldu.book.freelec_springboot_websevice.web;

import com.jojoldu.book.freelec_springboot_websevice.service.posts.PostsService;
import com.jojoldu.book.freelec_springboot_websevice.web.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot_websevice.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelec_springboot_websevice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}
