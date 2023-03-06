package com.example.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.data.entity.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecification {

	public static Specification<User> search(String text) {
		return new Specification<User>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (text == null || text.trim().length() <= 0) return null;
				
				String likeTerm = "%" + text + "%";
				
				Predicate predicate = cb.or(cb.like(root.get("name"), likeTerm), 
						cb.or(cb.like(root.get("email"), likeTerm), cb.like(root.get("role").as(String.class), likeTerm)));
				
				return predicate;
				
			}
			
		};
	}
	
}
