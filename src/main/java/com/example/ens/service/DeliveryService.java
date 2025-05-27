package com.example.ens.service;

import com.example.ens.dto.notification.DeliveredNotificationResponse;
import com.example.ens.model.Notification;
import com.example.ens.model.User;

import java.util.List;

public interface DeliveryService {
    void deliver(Notification notification);
    List<DeliveredNotificationResponse> getInbox(User currentUser);
}
