package com.mospan.railway.util;

import com.mospan.railwayspring.model.db.Ticket;

import com.mospan.railwayspring.service.UserService;
import org.apache.log4j.Logger;


import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private EmailSender() {
    }

    private static final Logger logger = Logger.getLogger(EmailSender.class);

    /**
     * Sending a notification for {reason} - either cancelling a trip or returning a ticket
     */
    public static void sendTicketNotification(Ticket ticket, String reason) {
        logger.info("Sending an email");
        String recipient = ticket.getUser().getDetails().getEmail();
        String sender = "railway.service@outlook.com";
        String host = "smtp.outlook.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("railway.service@outlook.com", new UserService().getEmailSenderData());
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            if (reason.equals("cancel_trip")) {
                message.setSubject(tripCancelMessage(ticket)[0]);
                message.setText(tripCancelMessage(ticket)[1]);
            } else if (reason.equals("return_ticket")) {
                message.setSubject(ticketReturnMessage(ticket)[0]);
                message.setText(ticketReturnMessage(ticket)[1]);
            }
            Transport.send(message);
            logger.info("Email notification successfully sent to user " + ticket.getUser().getLogin());
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static String[] tripCancelMessage(Ticket ticket) {
        return new String[]{"Your trip has been cancelled", "Dear " + ticket.getUser().getDetails().getFirstName() + ",\n\nYour trip on " + ticket.getTrip().getDepartDate() + " " +
                "from " + ticket.getTrip().getRoute().getStartStation().getName() + " to " + ticket.getTrip().getRoute().getEndStation().getName() +
                " was cancelled. Sorry for inconveniences and thank you for understanding. Your money is to be returned soon" +
                "\n\nBest regards, RailwayService"};
    }

    public static String[] ticketReturnMessage(Ticket ticket) {
        return new String[]{"Your ticket has been returned", "Dear " + ticket.getUser().getDetails().getFirstName() + ",\n\nYour ticket for trip on " + ticket.getTrip().getDepartDate() + " " +
                "from " + ticket.getTrip().getRoute().getStartStation().getName() + " to " + ticket.getTrip().getRoute().getEndStation().getName() +
                " was successfully returned. Your money is to be returned soon" +
                "\n\nBest regards, RailwayService"};
    }

}

