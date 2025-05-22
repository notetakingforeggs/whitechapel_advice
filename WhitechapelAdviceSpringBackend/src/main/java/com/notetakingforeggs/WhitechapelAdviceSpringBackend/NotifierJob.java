package com.notetakingforeggs.WhitechapelAdviceSpringBackend;

import com.notetakingforeggs.WhitechapelAdviceSpringBackend.bot.TelegramBot;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.CourtCase;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.model.Subscription;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.CourtCaseRepository;
import com.notetakingforeggs.WhitechapelAdviceSpringBackend.repository.SubscriptionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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


    @PostConstruct
    public void init() {
        System.out.println("NotifierJob initialized");
    }

    //    @Scheduled(cron =  "0 30 7 * * *")
//    @Scheduled(cron =  "0 * * * * *")
    @Scheduled(fixedRate = 50000) // every 10 seconds
    public void run(){
        System.out.println("Scheduler running");
        // iterating thru all subscriptions
        for (Subscription s : subs.findAll()){
            List<CourtCase> claimantHits = new ArrayList<>();
            List<CourtCase> defendantHits = new ArrayList<>();

            // getting all claimants/defendants that are returned with alert search terms
            // TODO need to create methods for finding cases created after a certain date and feed this in here
            for (String claimaint : s.getAlertTermsClaimant()){
                System.out.println("Alert Terms for Claimaint");
                System.out.println(claimaint);
                System.out.println("last notifiedL" + s.getLastNotifiedTimestamp());

                claimantHits.addAll(cases.findByClaimantContainingIgnoreCaseAndCreatedAtAfter(claimaint, s.getLastNotifiedTimestamp()));
            }for (String defendant : s.getAlertTermsDefendant()){
                System.out.println("Alert terms for defendant");
                System.out.println(defendant);
                System.out.println("last notifiedL" + s.getLastNotifiedTimestamp());
                defendantHits.addAll(cases.findByDefendantContainingIgnoreCaseAndCreatedAtAfter(defendant, s.getLastNotifiedTimestamp()));
            }
            // do something with these? send them back to the user as a message, but also dont send all of the ones you already sent

            if(!claimantHits.isEmpty() || !defendantHits.isEmpty()){
                System.out.println("not empty condish");
                bot.sendMessage(s.getChatId().toString(),format(claimantHits, defendantHits));
                s.setLastNotifiedTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            }else{
                System.out.println("empteeeeeeeeeeeeeee");
                //TODO i think the issue is in the last notified time or smth?
            }
        }
    }

    public String format(List<CourtCase> claimaintHits, List<CourtCase> defendantHits){
        StringBuilder sb = new StringBuilder(" **New Hits for your subscribed search terms!!** \n\n");

        if(!claimaintHits.isEmpty()){
            sb.append("Hits for your claimaint subscriptions: \n\n");
            claimaintHits.forEach(c ->
                            sb.append("• Start-time").append(epochSecondsToString(c.getStartTimeEpoch()))
                                    .append("\n")
                                    .append("Details: ").append(c.getCaseDetails())
                                    .append("\n\n")
                    );
        }if(!defendantHits.isEmpty()){
            sb.append("Hits for your defendants subscriptions: \n\n");

            for(CourtCase c : defendantHits){
                System.out.println(epochSecondsToString(c.getStartTimeEpoch()));

                sb.append("• Start-time ").append(epochSecondsToString(c.getStartTimeEpoch()))
                                    .append("\n\n")
                                    .append("Details:").append(c.getCaseDetails())
                                    .append("\n\n");
            }
// TODO this anon function not working. understand why?

//            defendantHits.forEach(c ->
//                            sb.append("• Start-time").append(epochSecondsToString(c.getStartTimeEpoch()))
//                                    .append("\n")
//                                    .append("Details:").append(c.getCaseDetails())
//                    );
        }
        return sb.toString();
    }

    public String epochSecondsToString(Long startTimeEpochSeconds){
        ZonedDateTime dateTime = Instant.ofEpochSecond(startTimeEpochSeconds).atZone(ZoneOffset.UTC);
        DateTimeFormatter ukFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        return dateTime.format(ukFormatter);
    }
}
