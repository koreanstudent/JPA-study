package jpabook.jpaproject.repository.order.query;

import java.util.List;

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
