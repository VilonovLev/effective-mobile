package ru.vilonov.effective.mobile.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vilonov.effective.mobile.model.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface ClientRepository extends CrudRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    @Modifying
    @Transactional
    @Query(value = """ 
            UPDATE t_client AS t1 SET c_score = (
                CASE WHEN t1.c_score < (:maxPercentage / 100)  *  (
                    SELECT c_init_score FROM t_user as t2 WHERE t2.c_client_id = t1.c_client_id)
                THEN t1.c_score * :percentage ELSE t1.c_score END);""", nativeQuery = true)
    void updateBalances(@Param("maxPercentage") long maxPercentage, @Param("percentage") long percentage);
}
