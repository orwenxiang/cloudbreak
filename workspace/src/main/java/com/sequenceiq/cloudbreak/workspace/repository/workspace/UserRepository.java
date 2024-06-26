package com.sequenceiq.cloudbreak.workspace.repository.workspace;

import java.util.Optional;
import java.util.Set;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sequenceiq.cloudbreak.workspace.model.User;
import com.sequenceiq.cloudbreak.workspace.repository.EntityType;

@EntityType(entityClass = User.class)
@Transactional(TxType.REQUIRED)
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tenant t WHERE u.userId = :userId AND t.name = :tenantName")
    Optional<User> findByTenantNameAndUserId(@Param("tenantName") String tenantName, @Param("userId") String userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tenant t WHERE u.userId = :userId AND t.id = :tenantId")
    Optional<User> findByTenantIdAndUserId(@Param("tenantId") Long tenantId, @Param("userId") String userId);

    Set<User> findByUserIdIn(Set<String> userIds);
}
