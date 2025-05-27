package com.example.ens.service.impl;

import com.example.ens.dto.common.ActionResponse;
import com.example.ens.dto.notification.NotificationRequest;
import com.example.ens.dto.notification.NotificationResponse;
import com.example.ens.exception.NotFoundException;
import com.example.ens.mapper.NotificationMapper;
import com.example.ens.model.Notification;
import com.example.ens.model.User;
import com.example.ens.repository.NotificationRepository;
import com.example.ens.repository.UserRepository;
import com.example.ens.security.AuthUtil;
import com.example.ens.service.DeliveryService;
import com.example.ens.service.NotificationSchedulerService;
import com.example.ens.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository repository;
    private final UserRepository userRepository;
    private final NotificationSchedulerService scheduler;
    private final NotificationMapper mapper;
    private final DeliveryService deliveryService;

    @Override
    public NotificationResponse schedule(NotificationRequest request) {
        User sender = AuthUtil.getCurrentUser();

        User recipient = userRepository.findByUsername(request.getRecipientUsername())
                .orElseThrow(() -> new NotFoundException("Recipient not found: " + request.getRecipientUsername(), HttpStatus.NOT_FOUND));

        Notification notification = Notification.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(request.getSubject())
                .message(request.getMessage())
                .cron(request.getCron())
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = repository.save(notification);

        scheduler.schedule(saved, () -> {
            log.info("CRON Triggered: {} -> {} | {}", sender.getUsername(), recipient.getUsername(), saved.getSubject());
            deliveryService.deliver(saved);
        });


        return mapper.toDto(saved);
    }

    @Override
    public List<NotificationResponse> getAll() {
        User currentUser = AuthUtil.getCurrentUser();
        return repository.findAllBySender(currentUser).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<NotificationResponse> getIncoming() {
        User currentUser = AuthUtil.getCurrentUser();
        return repository.findAllByRecipient(currentUser).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ActionResponse delete(UUID id) {
        User currentUser = AuthUtil.getCurrentUser();
        return repository.findByIdAndSender(id, currentUser).map(notification -> {
            scheduler.cancel(notification.getId());
            repository.delete(notification);
            log.info("Deleted notification and canceled task: {}", id);
            return new ActionResponse(true, "Notification deleted and task canceled");
        }).orElseGet(() -> new ActionResponse(false, "Notification not found or access denied"));
    }


    @Override
    public ActionResponse sendNow(UUID id) {
        User currentUser = AuthUtil.getCurrentUser();
        return repository.findByIdAndSender(id, currentUser).map(notification -> {
            log.info("Manual Trigger: {} -> {} | {}",
                    notification.getSender().getUsername(),
                    notification.getRecipient().getUsername(),
                    notification.getSubject());

            deliveryService.deliver(notification);

            return new ActionResponse(true, "Notification sent manually");
        }).orElseGet(() -> new ActionResponse(false, "Notification not found or access denied"));
    }

}
//*/5 * * * * *