package com.revature.revspace.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.StoreOTP;
import com.revature.revspace.models.TempOTP;



@RestController
public class VerifyOTPController {
	
		@PostMapping("/otp")
		public String verifyOTP(@RequestBody TempOTP sms) {
			//System.out.println("starting up");
			if(sms.getOtp()==StoreOTP.getOtp()) {
				System.out.println("correct");
				return "OTP Correct";
			}
			else {
				System.out.println("not correct");
				return "Not Correct OTP";
			}
				
		}
}
