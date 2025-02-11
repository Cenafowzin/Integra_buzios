package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Activity {
    private String title;
    private String description;
    private String pwdDescription;
    private boolean pwdPriority;
    private int effortLevel;
    private int tickets;
    private int guidesNumber;
    private String place;

    public Activity(String title, String description, String pwdDescription,
                    boolean pwdPriority, int effortLevel, int tickets, int guidesNumber, String place) {
        notNull(title, " Activity title must not be null");
        notNull(description, " Activity description must not be null");
        isTrue((tickets > 0), "Activity tickets must be greater than 0");
        isTrue((guidesNumber > 0), "Activity guides must be greater than 0");
        notNull(place, " Activity place must not be null");
        this.title = title;
        this.description = description;
        this.pwdDescription = pwdDescription;
        this.pwdPriority = pwdPriority;
        this.effortLevel = effortLevel;
        this.tickets = tickets;
        this.guidesNumber = guidesNumber;
        this.place = place;
    }

    public String ToString(){
        return String.format("Title: %s, Description: %s, PwD: %s, Tickets: %d, Place: %s",
                title, description, isPwdPriority(), tickets, place);
    }
}
