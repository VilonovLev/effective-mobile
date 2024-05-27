package ru.vilonov.effective.mobile.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.vilonov.effective.mobile.model.Client;
import ru.vilonov.effective.mobile.model.embedded.ClientContact;
import ru.vilonov.effective.mobile.model.embedded.ClientInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class JpaSpecificationForClient implements Specification<Client> {

    final Map<String,String> filters;

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (var filter : this.filters.keySet()) {
            switch (filter) {
                case "birthday" ->
                        predicateList.add(criteriaBuilder.greaterThan(root.<ClientInfo>get("clientInfo")
                            .get(filter), this.filters.get(filter)));
                case "phone", "email" ->
                        predicateList.add(criteriaBuilder.equal(root.<ClientContact>get("clientContact")
                                .get(filter), this.filters.get(filter)));
                case "firstname", "surname", "patronymic" ->
                        predicateList.add(criteriaBuilder.like(root.<ClientInfo>get("clientInfo")
                                .get(filter), this.filters.get(filter)));
                default ->
                        predicateList.add(criteriaBuilder.equal(root.<UUID>get(filter),
                                UUID.fromString(this.filters.get(filter))
                ));
            }
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
