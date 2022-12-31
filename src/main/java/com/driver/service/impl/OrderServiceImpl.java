package com.driver.service.impl;
import com.driver.io.converter.OrderConverter;
import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = OrderConverter.convertDtoToEntity(order);
        orderEntity.setOrderId(getAlphaNumericString(20));

        orderEntity = orderRepository.save(orderEntity);
        OrderDto newOrderDto = OrderConverter.convertEntityToDto(orderEntity);
        return newOrderDto;
    }

    // function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity == null) throw new Exception("Order doesn't exist!!!");

        OrderDto orderDto = OrderConverter.convertEntityToDto(orderEntity);

        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity oldOrder = orderRepository.findByOrderId(orderId);
        long id = oldOrder.getId();
        if(oldOrder == null) throw new Exception("Order not found!!!");

        OrderEntity updatedOrder = OrderEntity.builder()
                .id(id)
                .cost(order.getCost())
                .orderId(order.getOrderId())
                .items(order.getItems())
                .status(order.isStatus())
                .userId(order.getUserId())
                .build();

        updatedOrder = orderRepository.save(updatedOrder);
        OrderDto updatedOrderDto = OrderConverter.convertEntityToDto(updatedOrder);
        return updatedOrderDto;
    }

    @Override
    public OperationStatusModel deleteOrder(String orderId) throws Exception {
        OrderEntity order = orderRepository.findByOrderId(orderId);
        OperationStatusModel operationStatusModel;
        if(order == null){
            operationStatusModel = OperationStatusModel.builder()
                    .operationName(RequestOperationName.DELETE.toString())
                    .operationResult(RequestOperationStatus.ERROR.toString())
                    .build();

            throw new Exception("Order not found!!");
        }else{
            operationStatusModel = OperationStatusModel.builder()
                    .operationName(RequestOperationName.DELETE.toString())
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .build();
            orderRepository.delete(order);
        }

        return operationStatusModel;
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderEntity> allOrdersEntity = orderRepository.findAllByOrderId();
        List<OrderDto> allOrdersDto = new ArrayList<>();

        for(OrderEntity order : allOrdersEntity){
            OrderDto orderDto = OrderConverter.convertEntityToDto(order);
            allOrdersDto.add(orderDto);
        }
        return allOrdersDto;
    }
}