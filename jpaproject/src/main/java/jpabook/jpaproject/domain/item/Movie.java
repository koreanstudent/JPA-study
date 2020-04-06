package jpabook.jpaproject.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") //DB에 저장됨 
@Getter @Setter
public class Movie extends Item {

	private String director;
	private String actor;
	
}
