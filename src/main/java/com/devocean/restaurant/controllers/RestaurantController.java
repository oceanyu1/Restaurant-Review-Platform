package com.devocean.restaurant.controllers;

import com.devocean.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devocean.restaurant.domain.dto.RestaurantCreateUpdateRequestDto;
import com.devocean.restaurant.domain.dto.RestaurantDto;
import com.devocean.restaurant.domain.entities.Restaurant;
import com.devocean.restaurant.mappers.RestaurantMapper;
import com.devocean.restaurant.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(
            @Valid @RequestBody RestaurantCreateUpdateRequestDto request
    ) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper
                .toRestaurantCreateUpdateRequest(request);

        Restaurant restaurant = restaurantService.createRestaurant(restaurantCreateUpdateRequest);
        RestaurantDto createdRestaurantDto = restaurantMapper.toRestaurantDto(restaurant);
        return ResponseEntity.ok(createdRestaurantDto);
    }

    @GetMapping
    public ResponseEntity<?> getRestaurants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size) {
        // Return empty list for now
        return ResponseEntity.ok(Map.of(
                "content", List.of(),
                "totalElements", 0,
                "totalPages", 0
        ));
    }
}