package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.ICICIPayment;
import com.example.demo.service.ICICIService;

@Controller
public class ICICIController {

	@Autowired
	public ICICIService icicService;

	@ResponseBody
	@RequestMapping(path="/getCustomerDetails",method=RequestMethod.GET)
public ResponseEntity<?> getCustomerDetails(){
		ICICIPayment customerDetails = new ICICIPayment();
		customerDetails.setAccountNumber("123456");
		customerDetails.setCardNumber("8500123");
		customerDetails.setCustomerName("Bharath");
		customerDetails.setCvvNumber(12);
		
		return new ResponseEntity<>(customerDetails,HttpStatus.OK);
	}
}
