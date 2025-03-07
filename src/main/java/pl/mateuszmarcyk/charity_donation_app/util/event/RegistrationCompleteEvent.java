package pl.mateuszmarcyk.charity_donation_app.util.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.mateuszmarcyk.charity_donation_app.entity.User;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
