package com.example.capstone2.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    @Value("${twilio.account.sid}")
    private String accountSid ;
    @Value("${twilio.auth.token}")
    private String authToken ;
    @Value("${twilio.whatsapp.from}")
    private String from ;
    public void sendContractByWhatsApp(String toPhone , Integer contractId){
        Twilio.init(accountSid,authToken);
        Message.creator(
                new PhoneNumber("whatsapp:" + toPhone),
                new PhoneNumber(from),
                "Your Contract #" + contractId + " has been generated! \nCheck your mail"
        ).create();
    }
}
