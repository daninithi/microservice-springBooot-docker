package com.spring_boot.order_service.controller;
import java.util.List;

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
import org.springframework.web.reactive.function.client.WebClient;

import com.spring_boot.order_service.dto.OrderResponseDTO;
import com.spring_boot.order_service.dto.UserDTO;
import com.spring_boot.order_service.entity.Order;
import com.spring_boot.order_service.exception.ResourceNotFoundException;
import com.spring_boot.order_service.repository.OrderRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class orderController {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;
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
	public Mono<ResponseEntity<OrderResponseDTO>> createOrder(@RequestBody Order order) {
		// fetch user details from user service
		return webClientBuilder.build().get().uri("http://user-service/users/" + order.getUser_id())
				.retrieve()
				.bodyToMono(UserDTO.class)
				.map(userDTO -> {
					OrderResponseDTO responseDTO = new OrderResponseDTO();
					responseDTO.setUser_id(order.getUser_id());

					// set user details in response
					responseDTO.setUserName(userDTO.getName());
					responseDTO.setUserEmail(userDTO.getEmail());

					// save order
					orderRepository.save(order);
					return ResponseEntity.ok(responseDTO);
				});
	}
	// public Order createOrder(@RequestBody Order order) {
	// 	 return orderRepository.save(order);
	// }

	// @GetMapping("/{id}")
	// public Order getOrderById(@PathVariable Long id) {
	// 	return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found in this id"));
	// }

	@GetMapping("/{id}")
	public Mono<ResponseEntity<OrderResponseDTO>> getOrderById(@PathVariable Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
		
		return webClientBuilder.build().get().uri("http://user-service/users/" + order.getUser_id())
				.retrieve()
				.bodyToMono(UserDTO.class)
				.map(userDTO -> {
					OrderResponseDTO responseDTO = new OrderResponseDTO();
					responseDTO.setOrderId(order.getId());
					responseDTO.setUser_id(order.getUser_id());
					responseDTO.setUserName(userDTO.getName());
					responseDTO.setUserEmail(userDTO.getEmail());
					return ResponseEntity.ok(responseDTO);
				});
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<OrderResponseDTO>> updateOrderById(@PathVariable Long id, @RequestBody Order order) {
		Order existingOrder = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
		existingOrder.setProduct_name(order.getProduct_name());
		existingOrder.setUser_id(order.getUser_id());	
		return webClientBuilder.build().get().uri("http://user-service/users/" + existingOrder.getUser_id())
				.retrieve()
				.bodyToMono(UserDTO.class)
				.map(userDTO -> {
					OrderResponseDTO responseDTO = new OrderResponseDTO();
					responseDTO.setOrderId(existingOrder.getId());
					responseDTO.setUser_id(existingOrder.getUser_id());
					responseDTO.setUserName(userDTO.getName());
					responseDTO.setUserEmail(userDTO.getEmail());
					orderRepository.save(existingOrder);
					return ResponseEntity.ok(responseDTO);
				});
	}


	
	// @PutMapping("/{id}")
	// public Order updateOrderbyId(@PathVariable Long id, @RequestBody Order order) {
	// 	 Order orderData =  orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found "+ id));
	// 	 orderData.setProduct_name(order.getProduct_name());
	// 	 orderData.setUser_id(order.getUser_id());
	// 	 return orderRepository.save(orderData);
	// }

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteorderById(@PathVariable Long id) {
		Order orderData = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found "+ id));
		orderRepository.delete(orderData);
		return ResponseEntity.ok().build();
	}

	
}
