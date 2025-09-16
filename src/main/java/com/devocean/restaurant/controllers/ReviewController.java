package com.devocean.restaurant.controllers;

import com.devocean.restaurant.domain.ReviewCreateUpdateRequest;
import com.devocean.restaurant.domain.dto.ReviewCreateUpdateRequestDto;
import com.devocean.restaurant.domain.dto.ReviewDto;
import com.devocean.restaurant.domain.entities.Review;
import com.devocean.restaurant.domain.entities.User;
import com.devocean.restaurant.mappers.ReviewMapper;
import com.devocean.restaurant.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt) {

        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(review);

        User user = jwtToUser(jwt);

        Review createdReview = reviewService.createReview(user, restaurantId, reviewCreateUpdateRequest);

        return ResponseEntity.ok(reviewMapper.toDto(createdReview));
    }

    @GetMapping
    public Page<ReviewDto> listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC) Pageable pageable
            ) {

        return reviewService
                .listReviews(restaurantId, pageable)
                .map(reviewMapper::toDto);
    }

    @GetMapping(path = "/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable String restaurantId,
            @PathVariable String reviewId
    ) {
        return reviewService.getReview(restaurantId, reviewId)
                .map(reviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    private User jwtToUser(Jwt jwt) {
        return User.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();
    }

}
