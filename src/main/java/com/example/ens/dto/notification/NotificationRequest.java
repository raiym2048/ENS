package com.example.ens.dto.notification;

import lombok.Data;

@Data
public class NotificationRequest {
    private String recipientUsername; // обязательно существующий
    private String subject;
    private String message;
    private String cron;
}
