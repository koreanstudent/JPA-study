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
			List<Orderite> 
		});
		
	}
	
	// jpa에서 dto 직접조회하려면 collection을 바로 조회불가능.
	public List<OrderQueryDto> findOrderQueryDtos() {
		return em.createQuery("select new jpabook.jpaproject.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)"
				+ "from Order o"
				+ "join o.member m"
				+ "join o.delivery d",OrderQueryDto.class)
				.getResultList();
		
		
	}
}
