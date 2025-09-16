package com.devocean.restaurant.mappers;

import com.devocean.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devocean.restaurant.domain.dto.GeoPointDto;
import com.devocean.restaurant.domain.dto.RestaurantCreateUpdateRequestDto;
import com.devocean.restaurant.domain.dto.RestaurantDto;
import com.devocean.restaurant.domain.dto.RestaurantSummaryDto;
import com.devocean.restaurant.domain.entities.Restaurant;
import com.devocean.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto restaurantCreateUpdateRequestDto);


    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    RestaurantSummaryDto toSummaryDto(Restaurant restaurant);

    @Named("populateTotalReviews")
    private Integer populateTotalReviews(List<Review> reviews) {
        return reviews.size();
    }

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}
