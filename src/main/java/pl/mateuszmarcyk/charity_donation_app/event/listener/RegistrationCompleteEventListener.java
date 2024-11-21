package pl.mateuszmarcyk.charity_donation_app.event.listener;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.mateuszmarcyk.charity_donation_app.event.RegistrationCompleteEvent;
import pl.mateuszmarcyk.charity_donation_app.registration.verificationtoken.VerificationToken;
import pl.mateuszmarcyk.charity_donation_app.registration.verificationtoken.VerificationTokenService;
import pl.mateuszmarcyk.charity_donation_app.user.User;
import pl.mateuszmarcyk.charity_donation_app.util.RegistrationMailSender;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Value("${token.valid.time}")
    private int tokenValidTime;

    private final RegistrationMailSender registrationMailSender;
    private final VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(token, user, tokenValidTime);
        verificationTokenService.saveToken(verificationToken);

        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + token;

        try {
            registrationMailSender.sendVerificationEmail(user, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
