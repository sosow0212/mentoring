package com.example.mentoring.controller;

import com.example.mentoring.dto.BoardEditRequestDto;
import com.example.mentoring.dto.BoardRequestDto;
import com.example.mentoring.service.BoardService;
import com.example.mentoring.service.BoardServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TDD 테스트 주도 개발
@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {
    @InjectMocks
    BoardController boardController;

    @Mock
    BoardService boardService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }


    @Test
    @DisplayName("게시글 작성")
    public void saveBoardTest() throws Exception {
        // given
        BoardRequestDto boardReq = new BoardRequestDto( "제목", "내용", "홍길동");

        // when, then
        mockMvc.perform(
                post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardReq)))
                .andExpect(status().isCreated());

        verify(boardService).save(boardReq);
    }


    @Test
    @DisplayName("전체 게시글 조회")
    public void findBoardsTest() throws Exception {
        // given

        // when, then
        mockMvc.perform(
                get("/boards"))
                .andExpect(status().isOk());

        verify(boardService).getBoards();
    }


    @Test
    @DisplayName("게시건 단건 조회")
    public void findBoardTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        get("/boards/{id}", id))
                .andExpect(status().isOk());

        verify(boardService).getBoard(id);
    }


    @Test
    @DisplayName("게시글 수정")
    public void editBoardTest() throws Exception {
        // given
        Long id = 1L;
        BoardEditRequestDto boardEditReq = new BoardEditRequestDto( "제목", "내용", "홍길동");

        // when, then
        mockMvc.perform(
                put("/boards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardEditReq)))
                .andExpect(status().isOk());

        verify(boardService).editBoard(id, boardEditReq);
        assertThat(boardEditReq.getTitle()).isEqualTo("제목");
    }


    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoardTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                delete("/boards/{id}", id))
                .andExpect(status().isOk());

        verify(boardService).deleteBoard(id);
    }
}
