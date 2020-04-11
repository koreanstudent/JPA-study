package jpabook.jpaproject.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	
	@Test
	public void 회원가입() throws Exception{
		
		//given
		Member member = new Member();
		member.setName("kim");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
//		em.flush(); //영속성 컨텍스트 내용에 있는 정보를 db에 커밋
		assertEquals(member, memberRepository.findOne(saveId));
		
	}
	
	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//when
		memberService.join(member1);
		memberService.join(member2);
		
		/* 같은 - @Test(expected = IllegalStateException.class)
		 * try {
		 * 
		 * memberService.join(member2);
		 * 
		 * } catch (IllegalStateException e) { return; }
		 */
		
		//then
		fail("예외가 발생해야 한다.");
		
	}

}
