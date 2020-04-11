package jpabook.jpaproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpaproject.domain.Delivery;
import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.domain.Order;
import jpabook.jpaproject.domain.OrderItem;
import jpabook.jpaproject.domain.item.Item;
import jpabook.jpaproject.repository.ItemRepository;
import jpabook.jpaproject.repository.MemberRepository;
import jpabook.jpaproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdeService {
	
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	
	//주문
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(), count);
		
		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		
		//주문 저장
		// cascadeType.all 인해 orderitem, delivery 자동으로 persist됨
		orderRepository.save(order);
		
		return order.getId();
	}
	
	//주문 취소
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		// 주문 취소
		order.cancel();
	}
	
	//검색
//	public List<Order> findOrders(OrderSearch ordersearch) {
		
//	}
}
