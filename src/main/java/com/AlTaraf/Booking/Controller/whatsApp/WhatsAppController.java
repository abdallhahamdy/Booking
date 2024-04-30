package com.AlTaraf.Booking.Controller.whatsApp;

import com.AlTaraf.Booking.Entity.Image.Pdf;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Repository.image.PdfRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    PdfRepository pdfRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/sendDocument/{userId}")
    public String sendDocument(@PathVariable Long userId) {
        try {
            Pdf pdf = pdfRepository.findByUserIdAndSentIsNull(userId);

            if (pdf == null ) {
                return "لا يوجد ملف جاهز للارسال" ;
            }

            System.out.println("pdf : " + pdf.getId());

            User user = userRepository.findByUserId(userId);

            System.out.println("user.getPhone(): " + user.getPhone());

            String message = "Send_Invoice.message";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            System.out.println("ImagePath: " + pdf.getImagePath());

            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("token", "v8551cd68z16gr2y");
            map.add("to", "02"+user.getPhone());
            map.add("filename", "Details_Reservation.message");
            map.add("document", pdf.getImagePath());
            map.add("caption", "Send_Invoice.message");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            pdf.setSent(Boolean.TRUE);
            pdfRepository.save(pdf);

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
