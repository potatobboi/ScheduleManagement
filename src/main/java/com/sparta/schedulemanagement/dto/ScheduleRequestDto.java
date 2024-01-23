package com.sparta.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long id;

    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;
}
