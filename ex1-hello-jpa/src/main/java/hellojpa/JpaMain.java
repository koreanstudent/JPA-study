package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

	public static void main(String[] args) {

		//  하나만 생성해서 애플리케이션 전체에서 공유
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		// 요청이 올때마다 사용, 쓰레드간에 공유 x
		EntityManager em = emf.createEntityManager();
		
		// JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			// insert
//			Member member = new Member();
//			member.setId(1L);
//			member.setName("hello");
			// 영속
//			em.persist(member);
			
			// select
//			Member findMember = em.find(Member.class, 1L);
			
			// update
//			Member findMember = em.find(Member.class, 1L);
//			findMember.setName("helloJPA");
			
			// JPQL
//			List<Member> result =em.createQuery("select m from Member as m", Member.class)
//					.setFirstResult(5)
//					.setMaxResults(8)
//					.getResultList();
//			
//			for (Member member : result) {
//				System.out.println("member.name= " + member.getName());
//			}
//			회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
			// em.detach(member);
			
			// 객체를 삭제한 상태(삭제)
			// em.remove(member);
			
			Member member1 = new Member(150L, "A");
			Member member2 = new Member(160L, "B");
			System.out.println("================");
			
			em.persist(member1);
			em.persist(member2);
			
			em.flush(); // 즉시 db에 쿼리반영 
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		
		
		emf.close();
	}

}
