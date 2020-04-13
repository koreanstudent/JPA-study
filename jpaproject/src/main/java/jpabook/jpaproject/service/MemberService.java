package jpabook.jpaproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly =true) // 조회할때 이점이 됨
@RequiredArgsConstructor // final이 있는 필드를 가지고 생성자를 만든다. 
public class MemberService {

//	private MemberRepository memberRepository;
//	
//	@Autowired
//	public MemberService(MemberRepository memberRepository) {
//
//		this.memberRepository = memberRepository;
//	}
	// 테스트를 할때 에러 내줌. ex) MemberService m = new MemberService() 주입을 안하면 에러 나타냄
	private final MemberRepository memberRepository;
	

	
	//회원 가입
	@Transactional
	public Long join(Member member) {
		
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> findMember = memberRepository.findByName(member.getName());
		
		if(!findMember.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	//회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
	}
	
	
}
