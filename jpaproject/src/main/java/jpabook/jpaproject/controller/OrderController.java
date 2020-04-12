package jpabook.jpaproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpabook.jpaproject.domain.Member;
import jpabook.jpaproject.domain.item.Item;
import jpabook.jpaproject.service.ItemService;
import jpabook.jpaproject.service.MemberService;
import jpabook.jpaproject.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;
	
	@GetMapping("/order")
	public String createForm(Model model) {
		
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();
		
		model.addAttribute("members",members);
		model.addAttribute("items",items);
		
		return "order/orderForm";
	}
	
	@PostMapping("/order")
	public String order(@RequestMapping("memberId") Long memberId,
						@RequestMapping("itemId") Long itemId,
						@RequestMapping("count") int count) {
		orderService.order(memberId, itemId, count);
		return "redirect:/orders";
	}
}
