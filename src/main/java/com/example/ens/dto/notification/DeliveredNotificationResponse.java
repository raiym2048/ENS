package com.example.ens.dto.notification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeliveredNotificationResponse {
    private String subject;
    private String message;
    private String senderUsername;
    private LocalDateTime deliveredAt;
}
