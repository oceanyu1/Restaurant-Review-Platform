package com.devocean.restaurant.mappers;

import com.devocean.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devocean.restaurant.domain.dto.GeoPointDto;
import com.devocean.restaurant.domain.dto.RestaurantCreateUpdateRequestDto;
import com.devocean.restaurant.domain.dto.RestaurantDto;
import com.devocean.restaurant.domain.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto restaurantCreateUpdateRequestDto);


    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}
