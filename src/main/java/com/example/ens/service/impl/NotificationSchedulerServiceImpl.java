package com.example.ens.service.impl;

import com.example.ens.exception.BadRequestException;
import com.example.ens.model.Notification;
import com.example.ens.service.NotificationSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSchedulerServiceImpl implements NotificationSchedulerService {

    private final TaskScheduler taskScheduler;
    private final Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    @Override
    public void schedule(Notification notification, Runnable task) {
        if (!CronExpression.isValidExpression(notification.getCron())) {
            log.warn("Invalid cron expression: {}", notification.getCron());
            throw new BadRequestException("Invalid cron expression: " + notification.getCron());
        }

        CronTrigger trigger = new CronTrigger(notification.getCron());
        ScheduledFuture<?> future = taskScheduler.schedule(task, trigger);
        scheduledTasks.put(notification.getId(), future);
        log.info("Scheduled cron task for notification {}", notification.getId());
    }

    @Override
    public void cancel(UUID notificationId) {
        ScheduledFuture<?> future = scheduledTasks.remove(notificationId);
        if (future != null) {
            future.cancel(false);
            log.info("Canceled cron task for notification {}", notificationId);
        }
    }
}
