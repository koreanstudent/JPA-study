package jpabook.jpaproject.api;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class memberApiController {

	private final MemberService memberService;

	// @RequestBody -> json 데이터를 Member안에 맞게 넣어준다.
	// API를 만들기 위해서는 entity를 받아서도 안되고 내보내서도 안된다.
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);

		return new CreateMemberResponse(id);
	}

	/** * 등록 V2: 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다. */
	// 엔티티와 API 스펙을 명확하게 분리가능
	// 엔티티가 변경이 되도 API 스펙이 변경이 되지 않는 장점이 있다.
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@Data
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
	}

	@Data
	static class CreateMemberResponse {
		private Long id;

		public CreateMemberResponse(Long id) {
			this.id = id;
		}
	}
}
