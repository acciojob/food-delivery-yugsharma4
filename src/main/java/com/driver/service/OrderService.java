package com.driver.service;

import java.util.List;

import com.driver.model.response.OperationStatusModel;
import com.driver.shared.dto.OrderDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface OrderService {

	OrderDto createOrder(OrderDto order);
	OrderDto getOrderById(String orderId) throws Exception;
	OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception;
	OperationStatusModel deleteOrder(String orderId) throws Exception;
	List<OrderDto> getOrders();
}
