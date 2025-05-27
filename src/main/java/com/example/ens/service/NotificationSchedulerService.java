package com.example.ens.service;

import com.example.ens.model.Notification;

import java.util.UUID;

public interface NotificationSchedulerService {
    void schedule(Notification notification, Runnable task);
    void cancel(UUID notificationId);
}
