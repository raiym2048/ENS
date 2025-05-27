package com.example.ens.repository;

import com.example.ens.model.DeliveredNotification;
import com.example.ens.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveredNotificationRepository extends JpaRepository<DeliveredNotification, UUID> {
    List<DeliveredNotification> findAllByRecipient(User recipient);
}

