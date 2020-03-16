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

	
	
	
	
	
}
