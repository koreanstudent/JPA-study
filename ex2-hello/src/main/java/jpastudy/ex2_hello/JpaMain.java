package jpastudy.ex2_hello;

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

			// 저장
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("member1");
			member.setTeam(team); // pk -> fk 찾아서 넣어줌
			em.persist(member);
			
			Member findMember = em.find(Member.class,member.getId());
			
			Team findTeam= findMember.getTeam();
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		
		
		emf.close();
	}

}
