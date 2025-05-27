package com.example.ens.mapper.impl;

import com.example.ens.dto.notification.DeliveredNotificationResponse;
import com.example.ens.mapper.DeliveredNotificationMapper;
import com.example.ens.model.DeliveredNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveredNotificationMapperImpl implements DeliveredNotificationMapper {

    @Override
    public DeliveredNotificationResponse toDto(DeliveredNotification delivered) {
        return DeliveredNotificationResponse.builder()
                .subject(delivered.getSubject())
                .message(delivered.getMessage())
                .deliveredAt(delivered.getDeliveredAt())
                .senderUsername(delivered.getNotification().getSender().getUsername())
                .build();
    }
}
