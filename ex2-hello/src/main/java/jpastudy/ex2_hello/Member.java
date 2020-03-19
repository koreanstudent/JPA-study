package jpastudy.ex2_hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity // jpa 가 로딩 될때 사용하는 자바인지 인식, JPA가 관리하는 객체 테이블과 매핑하기위해 사용
public class Member {
	
	@Id  // jpa 에게 pk를 알려줘야함.
	@GeneratedValue(strategy = GenerationType.SEQUENCE) // sequence 사용
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;
	
//	@Column(name = "TEAM_ID")
//	private Long teamId;
	
	@ManyToOne  //관계가 먼지
	@JoinColumn(name ="TEAM_ID") // join 할것 
	private Team team;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	// 로직이 있으면 set 보다 알아볼수있는 메서드로 만든다.
//	public void changeTeam(Team team) {
//		this.team = team;
//		// 연관관계 편의 메소드
//		team.getMembers().add(this);  // team.getMembers().add(member); // 양방향 매핑 시 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
//	}

	
	
	
	
	
}
