package jpabook.jpaproject.repository;

import java.time.LocalDateTime;

import jpabook.jpaproject.domain.Address;
import jpabook.jpaproject.domain.Order;
import jpabook.jpaproject.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {

	private Long orderId;
	private String name;
	private LocalDateTime orderDate; // 주문시간
	private OrderStatus orderStatus;
	private Address address;

	public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus,
			Address address) {
//			orderId = order.getId();
//			name = order.getMember().getName(); // LAZY 초기화 ( 영속성컨텍스트가 member id 있는지 확인 해보고 db쿼리 날림) 데이터를 끌고옴
//			orderDate = order.getOrderDate();
//			orderStatus = order.getStatus();
//			address = order.getDelivery().getAddress(); // LAZY 초기화
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}

}
