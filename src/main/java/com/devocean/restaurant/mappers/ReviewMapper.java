package com.devocean.restaurant.mappers;

import com.devocean.restaurant.domain.ReviewCreateUpdateRequest;
import com.devocean.restaurant.domain.dto.ReviewCreateUpdateRequestDto;
import com.devocean.restaurant.domain.dto.ReviewDto;
import com.devocean.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto);

    ReviewDto toDto(Review review);
}
