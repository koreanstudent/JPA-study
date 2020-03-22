package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn( name = "PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy ="parent")
	private List<Category> child = new ArrayList<Category>();
	
	@ManyToMany
	@JoinTable(name ="CATEGORY_ITEM", 
		joinColumns = @JoinColumn(name ="CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name ="ITEM_ID")
	)
	private List<Item> items = new ArrayList<Item>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChild() {
		return child;
	}

	public void setChild(List<Category> child) {
		this.child = child;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
	
	
}
