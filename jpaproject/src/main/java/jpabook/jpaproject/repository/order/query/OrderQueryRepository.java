package jpabook.jpaproject.repository.order.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final EntityManager em;

	public List<OrderQueryDto> findOrderQueryDtos() {
		List<OrderQueryDto> result = findOrders();

		result.forEach(o -> {
			List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
			o.setOrderItems(orderItems);
		});

		return result;

	}

	// OrderQueryRepository에 추가
	/** * 최적화 * Query: 루트 1번, 컬렉션 1번 
	 * * 데이터를 한꺼번에 처리할 때
	// 많이 사용하는 방식 * 
	 * */
	public List<OrderQueryDto> findAllByDto_optimization() {
		// 루트 조회(toOne 코드를 모두 한번에 조회)
		List<OrderQueryDto> result = findOrders();
		// orderItem 컬렉션을 MAP 한방에 조회
		Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));
		// 루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
		result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
		return result;
	}

	private List<Long> toOrderIds(List<OrderQueryDto> result) {
		return result.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
	}

	private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
		List<OrderItemQueryDto> orderItems = em.createQuery(
				"select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
						+ " from OrderItem oi" + " join oi.item i" + " where oi.order.id in :orders",
				OrderItemQueryDto.class).setParameter("orderIds", orderIds).getResultList();
		return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
	}

	/** * 1:N 관계인 orderItems 조회 */
	private List<OrderItemQueryDto> findOrderItems(Long orderId) {
		return em.createQuery(
				"select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
						+ " from OrderItem oi" + " join oi.item i" + " where oi.order.id = : orderId",
				OrderItemQueryDto.class).setParameter("orderId", orderId).getResultList();
	}

	// jpa에서 dto 직접조회하려면 collection을 바로 조회불가능.
	public List<OrderQueryDto> findOrders() {
		return em.createQuery(
				"select new jpabook.jpaproject.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)"
						+ "from Order o" + "join o.member m" + "join o.delivery d",
				OrderQueryDto.class).getResultList();

	}
}
