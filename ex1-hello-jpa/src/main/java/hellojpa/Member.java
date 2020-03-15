package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // jpa 가 로딩 될때 사용하는 자바인지 인식, JPA가 관리하는 객체 테이블과 매핑하기위해 사용
//@Table(name = "USER"); //    쿼리 나갈때 TABLE이름이 USER를 향한다.
public class Member {
	
	@Id  // jpa 에게 pk를 알려줘야함.
	@GeneratedValue(strategy = GenerationType.SEQUENCE) // sequence 사용
	private Long id;
	
	
	private String name;
	
	// jpa는 동적으로 객체를 생성해야한다. 기본생성자가 있어야한다.
	public Member () {
		
	}
	
	public Member(Long id, String name) {
		this.id =id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
