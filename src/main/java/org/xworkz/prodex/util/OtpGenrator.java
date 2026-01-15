package org.xworkz.prodex.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xworkz.prodex.service.ProdexService;

import java.util.Random;

@Component
public class OtpGenrator {

    @Autowired
    private ProdexService prodexService;

    public String generateOtp(){
        Random random = new Random();
        int generateOtp = 1000 + random.nextInt(9000);
        return String.valueOf(generateOtp);
    }

    @Scheduled(fixedRate = 120000)
    private void runTask() {
        try {
            prodexService.clearExpiredOtps();

        }catch (Exception e){
        }
    }
}