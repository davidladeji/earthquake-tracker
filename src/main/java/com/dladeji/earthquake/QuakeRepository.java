package com.dladeji.earthquake;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dladeji.earthquake.dtos.QuakeCountDto;
import com.dladeji.earthquake.dtos.QuakeDisplayDto;
import com.dladeji.earthquake.entities.Quake;

public interface QuakeRepository extends CrudRepository<Quake, Long> {
    
    @Query("Select e.type as type, COUNT(e.type) as count From Quake e Group By e.type")
    List<QuakeCountDto> getCountByType();

    List<QuakeDisplayDto> findByMagGreaterThan(double mag);

    @Query("Select q.title as title, q.type as type, q.mag as mag, q.place as place, q.time as time, q.latitude as latitude, q.longtitude as longtitude From Quake q Where q.mag > 5 ")
    List<QuakeDisplayDto> findAllQuakes();
}
