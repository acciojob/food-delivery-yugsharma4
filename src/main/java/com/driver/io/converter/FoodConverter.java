package com.driver.io.converter;

import com.driver.io.entity.FoodEntity;
import com.driver.io.entity.OrderEntity;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.OrderDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FoodConverter {

    public static FoodDto convertRequestToDto(FoodDetailsRequestModel foodDetailsRequestModel){
        return FoodDto.builder()
                .foodCategory(foodDetailsRequestModel.getFoodCategory())
                .foodPrice(foodDetailsRequestModel.getFoodPrice())
                .foodName(foodDetailsRequestModel.getFoodName())
                .build();
    }

    public static FoodDetailsResponse convertDtoToResponse(FoodDto foodDto){
        return FoodDetailsResponse.builder()
                .foodCategory(foodDto.getFoodCategory())
                .foodId(foodDto.getFoodId())
                .foodName(foodDto.getFoodName())
                .foodPrice(foodDto.getFoodPrice())
                .build();
    }

    public static FoodEntity convertDtoToEntity(FoodDto foodDto){
        return FoodEntity.builder()
                .foodCategory(foodDto.getFoodCategory())
                .foodId(foodDto.getFoodId())
                .foodName(foodDto.getFoodName())
                .foodPrice(foodDto.getFoodPrice())
                .build();
    }

    public static FoodDto convertEntityToDto(FoodEntity foodEntity){
        return FoodDto.builder()
                .foodName(foodEntity.getFoodName())
                .foodPrice(foodEntity.getFoodPrice())
                .foodCategory(foodEntity.getFoodCategory())
                .foodId(foodEntity.getFoodId())
                .id(foodEntity.getId())
                .build();
    }
}
