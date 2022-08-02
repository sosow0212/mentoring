package com.example.mentoring.dto;

import com.example.mentoring.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    // Response 즉 응답할 때 필요한 Dto

    private String title;
    private String content;
    private String writer;

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
                board.getTitle(),
                board.getContent(),
                board.getWriter()
        );
    }
}
