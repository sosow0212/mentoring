package com.example.mentoring.controller;

import com.example.mentoring.dto.BoardEditRequestDto;
import com.example.mentoring.dto.BoardRequestDto;
import com.example.mentoring.entity.Board;
import com.example.mentoring.response.Response;
import com.example.mentoring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor // @Non null + static 생성자
@RestController
public class BoardController {

    private final BoardService boardService;

    // 전체 게시물 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards")
    public Response getBoards() {
        return Response.success(boardService.getBoards());
    }

    // 단건 게시글 조회
    @ResponseStatus(HttpStatus.OK) // 상태코드는 컨트롤러에서 지정해주는게 좋다.
    @GetMapping("/boards/{id}")
    public Response getBoard(@Valid @PathVariable("id") Long id) {
        return Response.success(boardService.getBoard(id));
    }

    // POST 게시글 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/boards")
    public Response save(@RequestBody BoardRequestDto boardReq) {
        return Response.success(boardService.save(boardReq));
    }

    // PUT 게시글 수정
    // ex) localhost:8080/boards/3
    // 게시글 수정하고 -> 완료 버튼을 누른다 -> 백엔드 서버 요청 (id, updateBoard)
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/boards/{id}")
    public Response editBoard(@Valid @PathVariable("id") Long id, @RequestBody BoardEditRequestDto boardEditReq) {
        return Response.success(boardService.editBoard(id, boardEditReq));
    }

    // DELETE 게시글 삭제
    // ex) localhost:8080/boards/3
    // 게시글 삭제하기
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/boards/{id}")
    public Response deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return Response.success("삭제 완료");
    }
}
