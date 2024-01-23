package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private String title;
    private String contents;
    private String username;
    private String date;

    public ScheduleResponseDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUsername();
        this.date = schedule.getDate();
    }
    public ScheduleResponseDto(String title, String contents, String username, String date) {
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.date = date;
    }
}
