package com.example.citronix.repository;

import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    @Query("SELECT hd FROM HarvestDetail hd " +
            "WHERE hd.tree.id = :treeId " +
            "AND hd.harvest.season = :season " +
            "AND hd.harvest.harvestDate BETWEEN :startDate AND :endDate")
    List<HarvestDetail> findByTreeAndSeasonInPeriod(
            @Param("treeId") Long treeId,
            @Param("season") Season season,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}