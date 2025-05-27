package com.example.ens.mapper;

import com.example.ens.dto.notification.DeliveredNotificationResponse;
import com.example.ens.model.DeliveredNotification;

public interface DeliveredNotificationMapper {
    DeliveredNotificationResponse toDto(DeliveredNotification delivered);
}
