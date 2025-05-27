package com.example.ens.service.impl;

import com.example.ens.dto.notification.DeliveredNotificationResponse;
import com.example.ens.mapper.DeliveredNotificationMapper;
import com.example.ens.mapper.NotificationMapper;
import com.example.ens.model.DeliveredNotification;
import com.example.ens.model.Notification;
import com.example.ens.model.User;
import com.example.ens.repository.DeliveredNotificationRepository;
import com.example.ens.service.DeliveryService;
import com.example.ens.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveredNotificationRepository deliveredRepo;
    private final DeliveredNotificationMapper deliveredNotificationMapper;
    private final EmailSenderService emailSenderService;


    @Override
    public void deliver(Notification notification) {
        // Реальная отправка письма
        emailSenderService.send(
                notification.getRecipient().getUsername(),
                notification.getSubject(),
                notification.getMessage()
        );

        // Сохраняем факт доставки
        DeliveredNotification delivered = DeliveredNotification.builder()
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .deliveredAt(LocalDateTime.now())
                .notification(notification)
                .build();

        deliveredRepo.save(delivered);
    }

    @Override
    public List<DeliveredNotificationResponse> getInbox(User currentUser) {
        return deliveredRepo.findAllByRecipient(currentUser).stream()
                .map(deliveredNotificationMapper::toDto)
                .toList();
    }



}
