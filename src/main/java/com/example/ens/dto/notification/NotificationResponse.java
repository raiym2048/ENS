package com.example.ens.dto.notification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationResponse {
    private UUID id;
    private String subject;
    private String message;
    private String cron;
    private String senderUsername;
    private String recipientUsername;
    private LocalDateTime createdAt;
}
