package com.example.mentoring.service;

import com.example.mentoring.dto.BoardEditRequestDto;
import com.example.mentoring.dto.BoardRequestDto;
import com.example.mentoring.dto.BoardResponseDto;
import com.example.mentoring.entity.Board;
import com.example.mentoring.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @InjectMocks
    BoardService boardService;

    @Mock
    BoardRepository boardRepository;

    @Test
    @DisplayName("전체 게시글 조회 서비스 테스트")
    void findBoardsServiceTest() {
        // given
        List<Board> boards = new ArrayList<>();
        Board board = new Board("제목", "내용", "작성자");
        Board board2 = new Board("제목", "내용", "작성자");
        boards.add(board);
        boards.add(board2);

        given(boardRepository.findAll()).willReturn(boards);

        // when
        List<BoardResponseDto> result = boardService.getBoards();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 단건 조회 서비스 테스트")
    void getBoardServiceTest() {
        // given
        Board board = new Board("제목", "내용", "작성자");
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        BoardResponseDto result = boardService.getBoard(1L);

        // then
        assertThat(result.getTitle()).isEqualTo(board.getTitle());
    }


    @Test
    @DisplayName("게시글 작성 서비스 테스트")
    void saveBoardServiceTest() {
        // given
        Board board = new Board("제목", "내용", "작성자");
        BoardRequestDto boardRequestDto = new BoardRequestDto("제목", "내용", "작성자");
        given(boardRepository.save(board)).willReturn(board);

        // when
        boardService.save(boardRequestDto);

        // then
        verify(boardRepository).save(any());
    }

    @Test
    @DisplayName("게시글 수정 서비스 테스트")
    void editBoardServiceTest() {
        // given
        Board board = new Board(1L, "제목", "내용", "작성자");
        BoardEditRequestDto req = new BoardEditRequestDto("제목", "내용2", "작성자");
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        boardService.editBoard(1L, req);

        // then
        assertThat(board.getContent()).isEqualTo("내용2");
    }

    @Test
    @DisplayName("게시글 삭제 서비스 테스트")
    void deleteBoardServiceTest() {
        // given
        Board board = new Board( "제목", "내용", "작성자");
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        boardService.deleteBoard(1L);

        // then
        verify(boardRepository).deleteById(anyLong());
    }
}
