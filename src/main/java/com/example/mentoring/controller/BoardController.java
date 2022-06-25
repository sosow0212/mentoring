package com.example.mentoring.controller;

import com.example.mentoring.entity.Board;
import com.example.mentoring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller 는 클라이언트(사용자)의 요청을 받는 클래스입니다.
 * Controller에서는 서비스 클래스 (기능 구현된 클래스) 를 불러서 사용자의 요청을 처리합니다.
 *
 * @Controller 는 템플릿 엔진(JSP, Thymeleaf 등등) 사용할 때 주로 쓰이고,
 * @RestController 는 API 서버를 만들 때 주로 사용됩니다. 저희는 API 서버를 만드니 RestController 로 진행합니다.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    // 게시글 전체 조회
    @GetMapping("/boards")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(boardService.findAllBoard(), HttpStatus.OK);
    }

    // 게시글 단건 조회
    @GetMapping("/boards/{id}")
    public ResponseEntity<?> findBoard(@PathVariable("id") int id) {
        return new ResponseEntity<>(boardService.findBoard(id), HttpStatus.OK);
    }

    // 게시글 작성
    @PostMapping("/boards")
    public ResponseEntity<?> writeBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.writeBoard(board), HttpStatus.OK);
    }

}
