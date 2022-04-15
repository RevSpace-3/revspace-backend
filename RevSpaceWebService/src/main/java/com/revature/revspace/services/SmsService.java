package com.revature.revspace.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.revature.revspace.models.SmsPojo;
import com.revature.revspace.models.StoreOTP;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Component
public class SmsService {
	public SmsService() {
		Properties properties = new Properties();
		
		try {
			// config.properties is a seperate file in my .gitignore holding the following 3 values
			FileReader f = new FileReader("config.properties");
			
				properties.load(f);
				ACCOUNT_SID = properties.getProperty("SECURE_ACCOUNT_SID");
				AUTH_TOKEN = properties.getProperty("SECURE_AUTH_TOKEN");
				FROM_NUMBER = properties.getProperty("SECURE_FROM_NUMBER");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
    private String ACCOUNT_SID;

    private String AUTH_TOKEN;

    private String FROM_NUMBER;

    public void send(SmsPojo sms) throws ParseException {
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      
    	
        int min = 100000;  
         int max = 999999; 
        int number=(int)(Math.random()*(max-min+1)+min);
      
        
        String msg ="Your OTP - "+number+ " please verify this OTP in your Application by group1 in RevSpace V3";
       
        
        Message message = Message.creator(new PhoneNumber(sms.getPhoneNo()), new PhoneNumber(FROM_NUMBER), msg)
                .create();
       
        StoreOTP.setOtp(number);
     
    }

    public void receive(MultiValueMap<String, String> smscallback) {
   
    }

}