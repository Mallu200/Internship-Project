package org.xworkz.prodex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.xworkz.prodex.dto.ProductPurchaseDto;
import org.xworkz.prodex.service.ReminderService;
import org.xworkz.prodex.util.AdminEmailUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public boolean scheduleAdminReminders(ProductPurchaseDto requestDto, String placerName, String senderEmail) {

        if (taskScheduler == null) {
            return false;
        }

        Instant initialRunTime = Instant.now().plus(24, ChronoUnit.HOURS);

        Runnable reminderTask = () -> {

            boolean sent = AdminEmailUtil.sendAdminNotificationEmail(requestDto, placerName, senderEmail);

            if (sent) {
            } else {
            }

        };

        try {
            java.util.Date firstDate = Objects.requireNonNull(Date.from(initialRunTime), "Date.from(initialRunTime) returned null");
            taskScheduler.schedule(reminderTask, firstDate);

            Instant followUpRunTime = initialRunTime.plus(48, ChronoUnit.HOURS);
            java.util.Date followUpDate = Objects.requireNonNull(Date.from(followUpRunTime), "Date.from(followUpRunTime) returned null");
            taskScheduler.schedule(reminderTask, followUpDate);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

