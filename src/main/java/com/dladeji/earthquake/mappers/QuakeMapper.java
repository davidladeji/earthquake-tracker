package com.dladeji.earthquake.mappers;

import java.util.List;

import com.dladeji.earthquake.dtos.QuakeDisplayDto;
import com.dladeji.earthquake.dtos.QuakeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuakeMapper {
    QuakeDto fromProjection(QuakeDisplayDto projection);
    List<QuakeDto> fromProjections(List<QuakeDisplayDto> projections);
}
