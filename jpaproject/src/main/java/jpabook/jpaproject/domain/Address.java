package jpabook.jpaproject.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

// 새로운 값 타입을 직접 정의해서 사용할 수 있는데, JPA에서는 이것을 임베디드 타입(embedded type)이라 합니다.
// 중요한 것은 직접 정의한 임베디드 타입도 int, String처럼 값 타입이라는 것입니다.
// @Embeddable: 값 타입을 정의하는 곳에 표시
// @Embedded: 값 타입을 사용하는 곳에 표시
// 임베디드 타입은 기본 생성자가 필수
// 장점은 재사용, 높은 응집도
@Embeddable
@Getter
public class Address {

	private String city;
	
	private String street;
	
	private String zipcode;
	
}
