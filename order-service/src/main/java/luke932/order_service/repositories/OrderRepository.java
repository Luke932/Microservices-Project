package luke932.order_service.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import luke932.order_service.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserId(Long userId);
	
	List<Order> findByUserIdIsNull();

    @Query("SELECT o FROM Order o WHERE o.articoli_ordinati LIKE %:articoli%")
    List<Order> findByArticoliOrdinatiContaining(@Param("articoli") String articoli);

    @Query("SELECT o FROM Order o WHERE o.metodo_pagamento = :metodo")
    List<Order> findByMetodoPagamento(@Param("metodo") String metodo);

    @Query("SELECT o FROM Order o WHERE o.info_spedizione LIKE %:info%")
    List<Order> findByInfoSpedizioneContaining(@Param("info") String info);
    
 // Custom JPQL query
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findOrdersByStatus(@Param("status") String status);

    // Custom native SQL query
    @Query(value = "SELECT * FROM Ordini o WHERE o.created_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findOrdersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
}
