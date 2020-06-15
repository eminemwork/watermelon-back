package com.example.watermelon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChartResponseDto {
    private String rank;
    private String title;
    private String singer;
    private String album;
    private int like;

    @Builder
    public ChartResponseDto(String rank, String title, String singer, String album, int like) {
        this.rank = rank;
        this.title= title;
        this. singer = singer;
        this.album = album;
        this.like = like;
    }
}
