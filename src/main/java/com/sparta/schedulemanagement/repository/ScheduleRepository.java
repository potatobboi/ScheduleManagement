package com.sparta.schedulemanagement.repository;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Schedule save(Schedule schedule) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (title, contents, username, password, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTitle());
                    preparedStatement.setString(2, schedule.getContents());
                    preparedStatement.setString(3, schedule.getUsername());
                    preparedStatement.setString(4, schedule.getPassword());
                    preparedStatement.setString(5, schedule.getDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String date = rs.getString("date");
                return new ScheduleResponseDto(title, contents, username, date);
            }
        });
    }

    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET title = ?, contents = ?, username = ?, password = ?, date = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername(), requestDto.getPassword(), requestDto.getDate(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setTitle(resultSet.getString("title"));
                schedule.setContents(resultSet.getString("contents"));
                schedule.setUsername(resultSet.getString("username"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setDate(resultSet.getString("date"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
