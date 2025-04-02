package com.pcn.playing_ground.repository;

import com.pcn.playing_ground.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query("SELECT u.firstname, o FROM Order o LEFT JOIN User u ON o.user.id = u.id ORDER BY o.id DESC")
    List<Object[]> findOrdersWithUserFirstName();

}
