package com.example.ens.mapper;

import com.example.ens.dto.notification.NotificationResponse;
import com.example.ens.model.Notification;

public interface NotificationMapper {
    NotificationResponse toDto(Notification notification);
}
