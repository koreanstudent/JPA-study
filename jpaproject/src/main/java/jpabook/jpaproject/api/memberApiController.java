package jpabook.jpaproject.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class memberApiController {

	private final MemberService memberService;

	/** * 조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO를 반환한다. */
	@GetMapping("/api/v2/members")
	public Result membersV2() {
		List<Member> findMembers = memberService.findMembers(); // 엔티티 -> DTO 변환 
		List<MemberDto> collect = findMembers.stream() .map(m -> new MemberDto(m.getName())).collect(Collectors.toList());
		return new Result(collect);
	}

	@Data
	@AllArgsConstructor
	class Result<T> {
		private T data;
	}

	@Data
	@AllArgsConstructor
	class MemberDto {
		private String name;
	}

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
	// 요청과 응답을 절대로 엔티티를 사용하지 않고 dto를 생성하여 사용하여야 한다.
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
			@RequestBody @Valid UpdateMemberRequest request) {
		memberService.update(id, request.getName());
		Member findMember = memberService.findOne(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());

	}

	@Data
	static class UpdateMemberRequest {
		private String name;
	}

	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse {
		private Long id;
		private String name;
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
