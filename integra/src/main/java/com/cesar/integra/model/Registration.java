package com.cesar.integra.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Registration {
    private int id;
    private User user;
    private Activity activity;
    private LocalDateTime registrationDateTime;
    private List<Date> availableDays;
    private String status;

    /**To new registrations*/
    public Registration(User user, Activity activity, LocalDateTime registrationDateTime, List<Date> availableDays, String status) {
        notNull(user, "Registration user must not be null");
        notNull(activity, "Registration activity must not be null");
        notNull(registrationDateTime, "Registration date must not be null");
        notNull(availableDays, "Registration available days must not be null");
        notNull(status, "Registration status must not be null");
        this.user = user;
        this.activity = activity;
        this.registrationDateTime = registrationDateTime;
        this.availableDays = availableDays;
        this.status = status;
    }

    /**To create new registrations*/
    public Registration(int id, User user, Activity activity, LocalDateTime registrationDateTime,
                        List<Date> availableDays, String status) {
        this(user, activity, registrationDateTime, availableDays, status);
        isTrue(id > 0, "Registration id must be greater than 0");
        this.id = id;
    }

}
