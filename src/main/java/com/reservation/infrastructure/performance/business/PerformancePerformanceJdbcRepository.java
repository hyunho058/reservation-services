package com.reservation.infrastructure.performance.business;

import com.reservation.domain.performance.Seat;
import com.reservation.domain.performance.repository.PerformanceJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformancePerformanceJdbcRepository implements PerformanceJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(List<Seat> seats) {
        String sql = "INSERT INTO reservation.seat (created_at, updated_at, is_reserved, location, number, performance_id)"
                + "VALUES (now(), now(), ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Seat seat = seats.get(i);
                ps.setBoolean(1, seat.getIsReserved());
                ps.setString(2, seat.getLocation());
                ps.setInt(3, seat.getNumber());
                ps.setLong(4, seat.getPerformanceId());
            }

            @Override
            public int getBatchSize() {
                return seats.size();
            }
        });
    }
}
