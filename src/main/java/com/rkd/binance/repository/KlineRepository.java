package com.rkd.binance.repository;

import com.rkd.binance.model.KlineModel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface KlineRepository extends CassandraRepository<KlineModel, Instant> {
    @Query("SELECT * FROM kline WHERE open_time = ?0 AND close_time = ?1 LIMIT 1")
    Optional<KlineModel> findLastByOpenTimeAndCloseTime(Instant openTime, Instant closeTime);

    @Query("SELECT * FROM kline WHERE open_time = ?0 AND close_time = ?1")
    List<KlineModel> findByOpenTimeAndCloseTime(Instant openTime, Instant closeTime);

    @Query("SELECT sequence FROM kline WHERE open_time = ?0 AND close_time = ?1 LIMIT 1")
    Optional<Integer> findMaxSequenceByOpenTimeAndCloseTime(Instant openTime, Instant closeTime);
}
