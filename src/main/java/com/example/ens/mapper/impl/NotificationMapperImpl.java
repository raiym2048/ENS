package com.example.ens.mapper.impl;

import com.example.ens.dto.notification.NotificationResponse;
import com.example.ens.mapper.NotificationMapper;
import com.example.ens.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponse toDto(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .cron(notification.getCron())
                .createdAt(notification.getCreatedAt())
                .senderUsername(notification.getSender().getUsername())
                .recipientUsername(notification.getRecipient().getUsername())
                .build();
    }
}
