package jpabook.jpashop;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class Jpamain {

	public static void main(String[] args) {
 
			//  하나만 생성해서 애플리케이션 전체에서 공유 
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello2");
			
			// 요청이 올때마다 사용, 쓰레드간에 공유 x
			EntityManager em = emf.createEntityManager();
			
			// JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			try {
	
				
				
				
				tx.commit();
				
			} catch (Exception e) {
				tx.rollback();
			} finally {
				em.close();
			}
			emf.close();
		}

}
