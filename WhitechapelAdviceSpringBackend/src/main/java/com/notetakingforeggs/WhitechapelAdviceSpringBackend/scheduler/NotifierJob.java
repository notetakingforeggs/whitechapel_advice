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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifierJob {

    private final CourtCaseRepository cases;
    private final SubscriptionRepository subs;
    private final TelegramBot bot;

    @Scheduled(cron =  "0 3 * * * *")
//    @Scheduled(cron =  "0 30 7 * * *")
    public void run(){

        // iterating thru all subscriptions
        for (Subscription s : subs.findAll()){
            List<CourtCase> claimantHits = new ArrayList<>();
            List<CourtCase> defendantHits = new ArrayList<>();

            // getting all claimants/defendants that are returned with alert search terms
            // TODO need to create methods for finding cases created after a certain date and feed this in here
            for (String claimaint : s.getAlertTermsClaimant()){
                claimantHits.addAll(cases.findByClaimantContainingIgnoreCaseAndCreatedAtAfter(claimaint, s.getLastNotifiedTimestamp()));
            }for (String defendant : s.getAlertTermsDefendant()){
                defendantHits.addAll(cases.findByDefendantContainingIgnoreCaseAndCreatedAtAfter(defendant, s.getLastNotifiedTimestamp()));
            }
            // do something with these? send them back to the user as a message, but also dont send all of the ones you already sent

            if(!claimantHits.isEmpty() || !defendantHits.isEmpty()){
                bot.sendMessage(s.getChatId().toString(),format(claimantHits, defendantHits));
                s.setLastNotifiedTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            }
        }
    }

    public String format(List<CourtCase> claimaintHits, List<CourtCase> defendantHits){
        StringBuilder sb = new StringBuilder(" **New Hits for your subscribed search terms!!** \n\n");

        if(!claimaintHits.isEmpty()){
            sb.append("Hits for your claimaint subscriptions: \n");
            claimaintHits.forEach(c ->
                            sb.append("• Start-time").append(epochSecondsToString(c.getStartTimeEpoch()))
                                    .append("\n")
                                    .append("Details:").append(c.getCaseDetails())
                    );
        }if(!defendantHits.isEmpty()){
            sb.append("Hits for your defendants subscriptions: \n");
            claimaintHits.forEach(c ->
                            sb.append("• Start-time").append(epochSecondsToString(c.getStartTimeEpoch()))
                                    .append("\n")
                                    .append("Details:").append(c.getCaseDetails())
                    );
        }
        return sb.toString();
    }

    public String epochSecondsToString(Long startTimeEpochSeconds){
        ZonedDateTime dateTime = Instant.ofEpochSecond(startTimeEpochSeconds).atZone(ZoneOffset.UTC);
        DateTimeFormatter ukFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        return dateTime.format(ukFormatter);
    }
}
