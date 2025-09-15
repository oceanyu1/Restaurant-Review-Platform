package com.devocean.restaurant.services;

import com.devocean.restaurant.domain.GeoLocation;
import com.devocean.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation geoLocate(Address address);
}
