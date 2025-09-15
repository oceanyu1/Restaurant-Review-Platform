package com.devocean.restaurant.services;

import com.devocean.restaurant.domain.RestaurantCreateUpdateRequest;
import com.devocean.restaurant.domain.entities.Restaurant;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest request);

}
