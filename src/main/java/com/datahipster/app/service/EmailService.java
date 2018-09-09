package com.datahipster.app.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public boolean sendEmail(String recipient, String subject, String htmlBody){
        try {
//            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
//                    .withRegion(Regions.EU_WEST_1).build();
//
//            SendEmailRequest request = new SendEmailRequest()
//                .withDestination(
//                    new Destination().withToAddresses(recipient))
//                .withMessage(new Message()
//                    .withBody(new Body()
//                        .withHtml(new Content()
//                            .withCharset("UTF-8").withData(htmlBody))
//                        .withText(new Content()
//                            .withCharset("UTF-8").withData(htmlBody)))
//                    .withSubject(new Content()
//                        .withCharset("UTF-8").withData(subject)))
//                .withSource("eamonn.dunne@gmail.com");
//            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                + ex.getMessage());
            return false;
        }
        return true;
    }


}
