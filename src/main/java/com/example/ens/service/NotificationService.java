package com.example.ens.service;

import com.example.ens.dto.common.ActionResponse;
import com.example.ens.dto.notification.NotificationRequest;
import com.example.ens.dto.notification.NotificationResponse;
import com.example.ens.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    NotificationResponse schedule(NotificationRequest request);
    List<NotificationResponse> getAll();
    List<NotificationResponse> getIncoming();
    ActionResponse delete(UUID id);


    ActionResponse sendNow(UUID id);
}
