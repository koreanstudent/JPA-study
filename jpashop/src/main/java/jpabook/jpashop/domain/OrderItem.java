package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {

	@Id @GeneratedValue
	@Column( name = "ORDER_ITEM_ID")
	private Long id;
	
//	@Column(name = "ORDER_ID")
//	private Long orderId;
	
	@ManyToOne
	@JoinColumn(name ="ORDER_ID")
	private Order order;
	
//	@Column(name = "ITEM_ID")
//	private Long itemId;
	
	@ManyToOne
	@JoinColumn(name ="ITEM_ID")
	private Item itemId;
	
	
	private int orderPrice;
	private int count;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public int getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Item getItemId() {
		return itemId;
	}
	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}
	
	
	
}
