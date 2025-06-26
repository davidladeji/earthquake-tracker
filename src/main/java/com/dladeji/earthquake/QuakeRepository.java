package com.dladeji.earthquake;

import org.springframework.data.repository.CrudRepository;

public interface QuakeRepository extends CrudRepository<Quake, Long> {
    
}
