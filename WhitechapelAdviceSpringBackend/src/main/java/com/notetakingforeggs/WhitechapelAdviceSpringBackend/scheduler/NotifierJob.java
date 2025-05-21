package com.notetakingforeggs.WhitechapelAdviceSpringBackend.scheduler;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot.TelegramBot;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.CourtCase;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Subscription;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.CourtCaseRepository;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jvnet.hk2.annotations.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifierJob {

    private final CourtCaseRepository cases;
    private final SubscriptionRepository subs;
    private final TelegramBot bot;

    @Scheduled(cron =  "0 30 7 * * *")
    public void run(){
        LocalDate today = LocalDate.now();

        // iterating thru all subscriptions
        for (Subscription s : subs.findAll()){
            List<CourtCase> claimantHits = new ArrayList<>();
            List<CourtCase> defendantHits = new ArrayList<>();

            // getting all claimants/defendants that are returned with alert search terms
            for (String claimaint : s.getAlertTermsClaimant()){
                claimantHits.addAll(cases.findByClaimantContainingIgnoreCase(claimaint));
            }for (String defendant : s.getAlertTermsDefendant()){
                defendantHits.addAll(cases.findByDefendantContainingIgnoreCase(defendant));
            }
            // do something with these? send them back to the user as a message, but also dont send all of the ones you already sent

        }
    }
}
