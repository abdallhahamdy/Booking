package com.AlTaraf.Booking.Controller.whatsApp;

import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Repository.File.FileForPdfRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/WhatsApp")
public class WhatsAppController {

    @Autowired
    FileForPdfRepository fileForPdfRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/sendDocument/{userId}")
    public String sendDocument(@PathVariable Long userId) {
        try {
            FileForPdf pdf = fileForPdfRepository.findByUserIdAndSentFlagIsNull(userId);

            if (pdf == null ) {
                return "لا يوجد ملف جاهز للارسال" ;
            }

            System.out.println("pdf : " + pdf.getId());

            User user = userRepository.findByUserId(userId);

            System.out.println("user.getPhone(): " + user.getPhone());

            String message = "Send_Invoice.message";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            System.out.println("ImagePath: " + pdf.getFileDownloadUri());

            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("token", "v8551cd68z16gr2y");
            map.add("to", "02"+user.getPhone());
            map.add("filename", "Details_Reservation.message");
            map.add("document", pdf.getFileDownloadUri());
            map.add("caption", "Send_Invoice.message");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            pdf.setSentFlag(Boolean.TRUE);
            fileForPdfRepository.save(pdf);

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.ultramsg.com/instance82647/messages/document";
            String response = restTemplate.postForObject(url, request, String.class);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending document: " + e.getMessage();
        }
    }
}
