package com.AlTaraf.Booking.payload.response;

import com.AlTaraf.Booking.dto.CityDto;

public class CityResponse {
    private CityDto cityDto;
    private ApiResponse apiResponse;

    public CityResponse(CityDto cityDto, ApiResponse apiResponse) {
        this.cityDto = cityDto;
        this.apiResponse = apiResponse;
    }

    public CityDto getCityDto() {
        return cityDto;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
