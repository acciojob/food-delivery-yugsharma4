package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.converter.OrderConverter;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderServiceImpl orderService;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto= orderService.getOrderById(id);
		OrderDetailsResponse orderDetailsResponse = OrderConverter.convertDtoToResponse(orderDto);
		return orderDetailsResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = OrderConverter.convertRequestToDto(order);
		orderDto = orderService.createOrder(orderDto);
		OrderDetailsResponse orderDetailsResponse = OrderConverter.convertDtoToResponse(orderDto);
		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = OrderConverter.convertRequestToDto(order);
		OrderDto updatedOrderDto = orderService.updateOrderDetails(id,orderDto);
		OrderDetailsResponse orderDetailsResponse = OrderConverter.convertDtoToResponse(updatedOrderDto);
		return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel = orderService.deleteOrder(id);
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDetailsResponse> orderDetailsResponses = new ArrayList<>();
		List<OrderDto> orderDtos = orderService.getOrders();
		for(OrderDto orderDto : orderDtos){
			OrderDetailsResponse orderDetailsResponse = OrderConverter.convertDtoToResponse(orderDto);
			orderDetailsResponses.add(orderDetailsResponse);
		}
		return orderDetailsResponses;
	}
}
