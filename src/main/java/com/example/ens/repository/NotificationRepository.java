package com.example.ens.repository;


import com.example.ens.model.Notification;
import com.example.ens.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllBySender(User sender);
    Optional<Notification> findByIdAndSender(UUID id, User sender);

    List<Notification> findAllByRecipient(User currentUser);
}
