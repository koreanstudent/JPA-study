package jpabook.jpaproject.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpaproject.domain.Address;
import jpabook.jpaproject.domain.Order;
import jpabook.jpaproject.domain.OrderStatus;
import jpabook.jpaproject.repository.OrderRepository;
import jpabook.jpaproject.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // final, notnull 에대해 생성자를 자동 생성해준다.
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;

	/**
	 * * V1. 엔티티 직접 노출 * - Hibernate5Module 모듈 등록, LAZY=null 처리 * - 양방향 관계 문제 발생
	 * -> @JsonIgnore
	 */
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAll();
		for (Order order : all) {
			order.getMember().getName(); // Lazy 강제 초기화
			order.getDelivery().getAddress(); // Lazy 강제 초기환
		}
		return all;
	}

	/**
	 * * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X) * - 단점: 지연로딩으로 쿼리 N번 호출
	 */
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		List<Order> orders = orderRepository.findAll();
		List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
		return result;
	}

	/**
	 * * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O) * - fetch join으로 쿼리 1번 호출 * 참고: fetch
	 * join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
	 *  재사용성이 있음
	 */
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
		return result;
	}

	/** * V4. JPA에서 DTO로 바로 조회 
	 * * - 쿼리 1번 호출 
	 * 
	 * - select 절에서 원하는 데이터만 선택해서 조회 
	 *  로직이 정해져 있어 재사용성 x 대신 원하는 값만 select
     */
	@GetMapping("/api/v4/simple-orders")
	public List<OrderSimpleQueryDto> ordersV4() {
		return orderRepository.findOrderDtos();
	}

	@Data
	static class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate; // 주문시간
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName(); // LAZY 초기화 ( 영속성컨텍스트가 member id 있는지 확인 해보고 db쿼리 날림) 데이터를 끌고옴
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress(); // LAZY 초기화
		}
	}
	

}
