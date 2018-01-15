package com.dangam.namu.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * Admin web controller
 * 
 */
@Controller
public class WebController {
	
  @RequestMapping("/")
  public String viewMain(Model model) {

	  return "/front/index";
  }
  
  
}
