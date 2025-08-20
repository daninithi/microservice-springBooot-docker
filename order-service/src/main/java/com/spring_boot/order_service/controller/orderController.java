package com.spring_boot.order_service.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.order_service.entity.Order;
import com.spring_boot.order_service.exception.ResourceNotFoundException;
import com.spring_boot.order_service.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class orderController {
	@Autowired
	private OrderRepository orderRepository;
//	@GetMapping 
//	public String getOrders() {
//		return "Orders";
//	}
//	
	@GetMapping
	public List<Order> getOrders() {
//		return Arrays.asList(new Order(1L, 1L, "pencil"),new Order(2L, 1L, "pen"), new Order(3L, 1L, "pen"), new Order(4L, 1L, "pen"));
		return orderRepository.findAll();
	}
	
	@PostMapping
	public Order createOrder(@RequestBody Order order) {
		 return orderRepository.save(order);
	}

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found in this id"));
	}
	
	@PutMapping("/{id}")
	public Order updateOrderbyId(@PathVariable Long id, @RequestBody Order order) {
		 Order orderData =  orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found "+ id));
		 orderData.setProduct_name(order.getProduct_name());
		 orderData.setUser_id(order.getUser_id());
		 return orderRepository.save(orderData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteorderById(@PathVariable Long id) {
		Order orderData = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found "+ id));
		orderRepository.delete(orderData);
		return ResponseEntity.ok().build();
	}

	
}
