package com.example.mentoring.service;

import com.example.mentoring.dto.BoardEditRequestDto;
import com.example.mentoring.dto.BoardRequestDto;
import com.example.mentoring.dto.BoardResponseDto;
import com.example.mentoring.entity.Board;
import com.example.mentoring.exception.BoardNotFoundException;
import com.example.mentoring.exception.WriterNotFoundException;
import com.example.mentoring.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        // Dto에 데이터를 씌워서 보내줄거임 -> Entity 자체를 보내면 안됨!
        // Dto를 통해 데이터를 보내는 이유는, Entity에 있는 Password 같은 민감정보를 Dto에는 빼서 보낼 수 있음
        // 그리고 Entity 보다는 Dto로 컨트롤러 - 서비스 - 엔터티 간 통신하는게 좋음
        List<Board> boards = boardRepository.findAll();

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        boards.stream().forEach(i -> boardResponseDtoList.add(new BoardResponseDto().toDto(i)));
        return boardResponseDtoList;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        return BoardResponseDto.toDto(board);
    }

    @Transactional
    public BoardResponseDto save(BoardRequestDto boardReq) {
        // 작성자가 없을 때 예외 터뜨려보기
        if (boardReq.getWriter().equals(" ")) {
            throw new WriterNotFoundException();
        }

        Board board = boardReq.toDto(boardReq);
        boardRepository.save(board);
        return BoardResponseDto.toDto(board);
    }

    @Transactional
    // Transactional 을 붙이면 더티체킹이 일어나서, 저장하지 않아도 메서드가 성공적으로 끝나면 저장이 된다.
    public BoardResponseDto editBoard(Long id, BoardEditRequestDto BoardEditReq) {
        // 1. 기존 게시물을 꺼내온다
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        // 2. 기존 게시물에, updateBoard 정보를 덮어씌워준다.
        board.setTitle(BoardEditReq.getTitle());
        board.setContent(BoardEditReq.getContent());
        return BoardResponseDto.toDto(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        // 게시판을 먼저 찾고, 없다면 Exception 처리

        boardRepository.deleteById(id);
    }

}
