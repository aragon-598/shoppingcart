package com.store.shoppingcart.clients.infrastructure.persistence;

import com.store.shoppingcart.clients.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, String> {
    
    @Query("SELECT c FROM ClientEntity c WHERE c.userId = :userId")
    Optional<ClientEntity> findByUserId(@Param("userId") String userId);
    
    @Query("SELECT c FROM ClientEntity c WHERE c.documentValue = :documentValue AND c.documentType = :documentType")
    Optional<ClientEntity> findByDocumentValueAndDocumentType(
        @Param("documentValue") String documentValue,
        @Param("documentType") com.store.shoppingcart.clients.infrastructure.persistence.entity.DocumentTypeEntity documentType
    );
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ClientEntity c WHERE c.userId = :userId")
    boolean existsByUserId(@Param("userId") String userId);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ClientEntity c WHERE c.documentValue = :documentValue AND c.documentType = :documentType")
    boolean existsByDocumentValueAndDocumentType(
        @Param("documentValue") String documentValue,
        @Param("documentType") com.store.shoppingcart.clients.infrastructure.persistence.entity.DocumentTypeEntity documentType
    );
    
    @Query("SELECT c FROM ClientEntity c ORDER BY c.createdAt DESC")
    List<ClientEntity> findAllPaginated(Pageable pageable);
}
