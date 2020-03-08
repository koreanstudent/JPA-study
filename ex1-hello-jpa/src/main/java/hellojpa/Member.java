package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // jpa 가 로딩 될때 사용하는 자바인지 인식
//@Table(name = "USER"); // 쿼리 나갈때 TABLE이름이 USER를 향한다.
public class Member {
	
	@Id  // jpa 에게 pk를 알려줘야함.
	private Long id;
	private String name;
	
	
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
