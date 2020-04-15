package jpabook.jpaproject.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jpabook.jpaproject.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAllByString(OrderSearch orderSearch) { // language=JPAQL
		String jpql = "select o From Order o join o.member m";
		boolean isFirstCondition = true;
		// 주문 상태 검색
		if (orderSearch.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		} // 회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}
		TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); // 최대 1000건
		if (orderSearch.getOrderStatus() != null) {
			query = query.setParameter("status", orderSearch.getOrderStatus());
		}
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query = query.setParameter("name", orderSearch.getMemberName());
		}
		return query.getResultList();
	}

	public List<Order> findAll(OrderSearch orderSearch) {
		return em.createQuery("select o from Order o" + " join fetch o.member m" + " join fetch o.delivery d", Order.class)
				.getResultList();
	}

	// fetch 조인
	public List<Order> findAllWithMemberDelivery() {
		return em.createQuery("select o from Order o" + " join fetch o.member m" + " join fetch o.delivery d", Order.class)
				.getResultList();
	}
	
	// 일반적인 sql을 사용할 때 처럼 원하는 값을 선택해서 조회
	// new 명령어를 사용해서 jpql의 결과를 dto로 즉시 변환
	// 리포지토리 재사용성 떨어짐
	public List<OrderSimpleQueryDto> findOrderDtos() {
		return em.createQuery(
				"select new jpabook.jpaproject.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDdate, o.status, o.address) from Order o" +
				"join o.member m" +
				"join o.delivery d", OrderSimpleQueryDto.class)
				.getResultList();
	}

}
