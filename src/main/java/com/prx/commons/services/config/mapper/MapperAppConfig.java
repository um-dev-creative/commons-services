package com.prx.commons.services.config.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct base configuration for project mappers.
 * Configures mappers to be Spring components and defines mapping behavior for nulls and unmapped properties.
 */
@MapperConfig(
        // Specifies that the mapper should be a Spring bean.
        componentModel = MappingConstants.ComponentModel.SPRING,
        // Specifies how to handle null properties when mapping.
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        // Ignore unmapped source properties.
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        // Ignore unmapped target properties.
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapperAppConfig {
}
