package com.bintang.usermanagement.specification;

import com.bintang.usermanagement.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(String name, String email) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isFalse(root.get("isDeleted")));

            if (name != null) {
                predicates.add(
                        cb.like(cb.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%")
                );
            }

            if (email != null) {
                predicates.add(
                        cb.like(cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%")
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }
}
