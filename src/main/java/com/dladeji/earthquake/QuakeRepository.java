package com.dladeji.earthquake;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dladeji.earthquake.dtos.QuakeCountDto;

public interface QuakeRepository extends CrudRepository<Quake, Long> {
    
    @Query("Select e.type as type, COUNT(e.type) as count From Quake e Group By e.type")
    public List<QuakeCountDto> getCountByType();
}
