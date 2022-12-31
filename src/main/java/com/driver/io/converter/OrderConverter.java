package com.driver.io.converter;

import com.driver.io.entity.OrderEntity;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.shared.dto.OrderDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderConverter {

    public static OrderDto convertRequestToDto(OrderDetailsRequestModel orderDetailsRequestModel){
        return OrderDto.builder()
                .cost(orderDetailsRequestModel.getCost())
                .items(orderDetailsRequestModel.getItems())
                .userId(orderDetailsRequestModel.getUserId())
                .status(true)
                .build();
    }

    public static OrderDetailsResponse convertDtoToResponse(OrderDto orderDto){
        return OrderDetailsResponse.builder()
                .cost(orderDto.getCost())
                .items(orderDto.getItems())
                .userId(orderDto.getUserId())
                .status(true)
                .build();
    }

    public static OrderEntity convertDtoToEntity(OrderDto orderDto){
        return OrderEntity.builder()
                .cost(orderDto.getCost())
                .items(orderDto.getItems())
                .userId(orderDto.getUserId())
                .status(true)
                .build();
    }

    public static OrderDto convertEntityToDto(OrderEntity orderEntity){
        return OrderDto.builder()
                .cost(orderEntity.getCost())
                .items(orderEntity.getItems())
                .userId(orderEntity.getUserId())
                .status(true)
                .id(orderEntity.getId())
                .build();
    }
}
