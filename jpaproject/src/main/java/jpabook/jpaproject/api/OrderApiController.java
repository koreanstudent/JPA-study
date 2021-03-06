package jpabook.jpaproject.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpaproject.domain.Address;
import jpabook.jpaproject.domain.Order;
import jpabook.jpaproject.domain.OrderItem;
import jpabook.jpaproject.domain.OrderStatus;
import jpabook.jpaproject.repository.OrderRepository;
import jpabook.jpaproject.repository.OrderSearch;
import jpabook.jpaproject.repository.order.query.OrderQueryDto;
import jpabook.jpaproject.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;

	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();

			List<OrderItem> orderItems = order.getOrderItems();
			orderItems.stream().forEach(o -> o.getItem().getName());
		}
		return all;
	}

	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2() {
		List<Order> orders = orderRepository.findAll();
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
		return result;
	}

	@GetMapping("/api/v3/orders")
	public List<OrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithItem();
		List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
		return result;
	}

	@GetMapping("/api/v4/orders")
	public List<OrderQueryDto> ordersV4() {
		return orderQueryRepository.findOrderQueryDtos();

	}

	@GetMapping("/api/v5/orders")
	public List<OrderQueryDto> ordersV5() {
		return orderQueryRepository.findAllByDto_optimization();
	}
	@GetMapping("/api/v6/orders")
	public List<OrderQueryDto> ordersV6() {
		return orderQueryRepository.findAllByDto_flat();
	}

	@Data
	static class OrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; // 주문시간
		private OrderStatus orderStatus;
		private Address address;
		private List<OrderItemDto> orderItems;

		public OrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
			orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
					.collect(Collectors.toList());
		}
	}

	@Data
	static class OrderItemDto {
		private String itemName;// 상품 명
		private int orderPrice; // 주문 가격
		private int count; // 주문 수량

		public OrderItemDto(OrderItem orderItem) {
			itemName = orderItem.getItem().getName();
			orderPrice = orderItem.getOrderPrice();
			count = orderItem.getCount();
		}
	}
}
