package jpabook.jpaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class JpaprojectApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(JpaprojectApplication.class, args);
	}
	
	// 엔티티로 넘기거나 받을때 필요  사용할일 없을..
	@Bean Hibernate5Module hibernate5Module() {    return new Hibernate5Module(); }
	 

}
