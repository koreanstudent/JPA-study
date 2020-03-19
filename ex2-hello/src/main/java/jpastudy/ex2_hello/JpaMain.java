package jpastudy.ex2_hello;

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

			// 저장 
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("member1");
//			member.changeTeam(team); // pk -> fk 찾아서 넣어줌
			em.persist(member);
			
			team.addMember(member);
			
//			team.getMembers().add(member); // 양방향 매핑 시 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
			
			em.flush(); // 즉시 db에 쿼리반영 
			em.clear(); // 메모리 비우기
			
			Member findMember = em.find(Member.class,member.getId());
			List<Member> members = findMember.getTeam().getMembers(); // 양방향 매핑
			
			for (Member m : members) {
				System.out.println("m = " + m.getUsername());
			}
			
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		
		
		emf.close();
	}

}
