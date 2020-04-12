package jpabook.jpaproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpaproject.domain.item.Item;
import jpabook.jpaproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);

	}

	/** * 속성 컨텍스트가 자동 변경 */
	@Transactional
	public void updateItem(Long id, String name, int price) {
		Item item = itemRepository.findOne(id);
		item.setName(name);
		item.setPrice(price);
	}

	public List<Item> findItems() {
		return itemRepository.findAll();
	}

	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}

}
