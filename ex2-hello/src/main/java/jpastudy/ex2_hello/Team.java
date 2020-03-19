package jpastudy.ex2_hello;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {
   
	@Id @GeneratedValue
	@Column(name = "TEAM_ID")
	private Long teamId;

	private String name;
	
	@OneToMany(mappedBy ="team")  // mamber에 team이랑 매핑
	private List<Member> members = new ArrayList();
	
	public void addMember(Member member) {
		member.setTeam(this);
		members.add(member);
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
