package com.example.ens.controller;

import com.example.ens.dto.common.ActionResponse;
import com.example.ens.dto.notification.DeliveredNotificationResponse;
import com.example.ens.dto.notification.NotificationRequest;
import com.example.ens.dto.notification.NotificationResponse;
import com.example.ens.model.User;
import com.example.ens.security.AuthUtil;
import com.example.ens.service.DeliveryService;
import com.example.ens.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final DeliveryService deliveryService;


    @PostMapping
    @Operation(summary = "Schedule a new notification")
    public ResponseEntity<NotificationResponse> create(@RequestBody NotificationRequest notification) {
        return ResponseEntity.ok(notificationService.schedule(notification));
    }

    @GetMapping
    @Operation(summary = "Get all scheduled notifications created by the current user")
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAll());
    }

    @GetMapping("/incoming")
    @Operation(summary = "Get scheduled notifications where the current user is the recipient")
    public ResponseEntity<List<NotificationResponse>> getIncoming() {
        return ResponseEntity.ok(notificationService.getIncoming());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification by ID (if created by the current user)")
    public ResponseEntity<ActionResponse> delete(
            @Parameter(description = "UUID of the notification to delete", example = "fbc6daae-e28e-4a0c-bb9e-d9e98d924daa")
            @PathVariable("id") UUID id
    ) {
        ActionResponse result = notificationService.delete(id);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(404).body(result);
    }

    @PostMapping("/{id}/send")
    @Operation(summary = "Manually trigger notification delivery")
    public ResponseEntity<ActionResponse> sendNow(
            @Parameter(description = "UUID of the notification to send", example = "fbc6daae-e28e-4a0c-bb9e-d9e98d924daa")
            @PathVariable("id") UUID id
    ) {
        ActionResponse result = notificationService.sendNow(id);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(404).body(result);
    }


    @GetMapping("/inbox")
    @Operation(summary = "Get delivered notifications received by the current user")
    public ResponseEntity<List<DeliveredNotificationResponse>> getInbox() {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(deliveryService.getInbox(currentUser));
    }
}
