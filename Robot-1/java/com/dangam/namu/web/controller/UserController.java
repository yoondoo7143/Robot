package com.dangam.namu.web.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.dangam.namu.web.dao.UserMapper;


/**
*
* UserController
* 2017-08-30
* 윤희현
* 
*/
@Controller
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	

	/* 
	 * 회원가입 화면 호출
	 * 2017-08-30
	 * 윤희현
	 */
  
  @RequestMapping(value = "/join") 
  public ModelAndView join(HttpServletRequest req, HttpServletResponse rep, Model model ) {
	  ModelAndView mav = new ModelAndView();
	  
	  System.out.println("====logoPath====");
	  
	  
	  
	  mav.setViewName("/member/join");
	
	  return mav;
  }
  
  /* 
   * 회원ID 중복확인
   * 2017-08-30
   * 윤희현
   */
  @RequestMapping(value = "/confirmId") 
  public @ResponseBody int confirmId(HttpServletRequest req, HttpServletResponse rep, Model model,
		  @RequestParam("userId") String userId) throws SQLException {
			System.out.println(userId);
			System.out.println(userMapper.confirmId(userId));
			int confirmId = userMapper.confirmId(userId);
			System.out.println("confirmId");
			System.out.println(confirmId);
	  return confirmId;
  }
  
  /* 
   * 회원가입 DBinsert 
   * 2017-09-04
   * 윤희현
   */
  @RequestMapping(value = "/joinUser") 
  public ModelAndView joinUser(HttpServletRequest req, HttpServletResponse rep, Model model,
		  @RequestParam("checkedValue") String checkedValue, @RequestParam("companyName") String companyName, @RequestParam("ceoNm") String ceoNm,
		  @RequestParam("companyNum") String companyNum, @RequestParam("userNm") String userNm, @RequestParam("userId") String userId, @RequestParam("user_pw") String user_pw, @RequestParam("tel") String tel, 
		  @RequestParam("email") String email, @RequestParam("emailDiv") String emailDiv, @RequestParam("filePath") String filePath, @RequestParam("input_postal") String input_postal, 
		  @RequestParam("loadNm") String loadNm, @RequestParam("jusoDtl") String jusoDtl) throws SQLException {
	  ModelAndView mav = new ModelAndView();
	  
	  String checkedDiv = null;
	  String userRole = null;
	  if(checkedValue.length() > 1) {
		  int num1 = Integer.parseInt(checkedValue.split(",")[0]);
		  int num2 = Integer.parseInt(checkedValue.split(",")[1]);
		  checkedDiv = String.valueOf(num1+num2);
	  } else {
		  checkedDiv = checkedValue;
	  }
	  
	 switch (checkedDiv) {
		case "1" :
			System.out.println("ROLE_UPLOADER");
			userRole = "ROLE_UPLOADER";
			break;
		case "2" :
			System.out.println("ROLE_DOWNLOADER");
			userRole = "ROLE_DOWNLOADER";
			break;
		case "3" :
			System.out.println("ROLE_BOTH");
			userRole = "ROLE_BOTH";
			break;
	}
	 
	  String address = loadNm + "," + jusoDtl;
	  
	  Map<Object, Object> joinData = new HashMap<>();
	  
	  String realFilePath = null;

	  System.out.println(filePath);
	  if(!filePath.equals("")) {
		  System.out.println("filePath not null");
		  String path = "C:\\Users\\andre\\git\\dangam_bc\\Dangam_BC\\src\\main\\webapp\\WEB-INF\\resources\\upload\\companyLogo"; 
		  try { 
			  // MultipartHttpServletRequest 생성 
			  MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req; 
			  Iterator<?> iter = mhsr.getFileNames(); 
			  MultipartFile mfile = null; 
			  String fieldName = ""; 
			  // 디레토리가 없다면 생성 
			  File dir = new File(path); 
			  if (!dir.isDirectory()) { 
				  dir.mkdirs(); 
			  
			  }
			  // 값이 나올때까지
			  while (iter.hasNext()) { 
				  fieldName = (String) iter.next(); 
				  // 내용을 가져와서 
				  mfile = mhsr.getFile(fieldName); 

				  String origName; 
				  origName = new String(mfile.getOriginalFilename()); 

				  //한글꺠짐 방지 // 파일명이 없다면 
				  
				  if ("".equals(origName)) { 
					  continue; 
				  } 
			  // 파일 명 변경(uuid로 암호화) 
			  //String ext = origName.substring(origName.lastIndexOf('.')); 
		
			  // 확장자 
			  String saveFileName = origName; 
			  // 설정한 path에 파일저장 
			  File serverFile = new File(path + File.separator + saveFileName);
			  realFilePath = "/resources/upload/companyLogo/"+origName;
			  // DB에 들어가야할 파일Path
			  mfile.transferTo(serverFile); 

			
			  
			  } 	
			  
			  } catch (UnsupportedEncodingException e) { 
				  e.printStackTrace(); 
			  }catch (IllegalStateException e) { 
				  e.printStackTrace(); 
			  } catch (IOException e) { 
				  e.printStackTrace(); 
			  } 
	  } 
	  
	  joinData.put("realFilePath", realFilePath);
	  joinData.put("division", checkedDiv);
	  joinData.put("companyNm", companyName);
	  joinData.put("ceoNm", ceoNm);
	  joinData.put("companyNum", companyNum);
	  joinData.put("userNm", userNm);
	  joinData.put("userId", userId);
	  joinData.put("tel", tel);
	  joinData.put("email", email);
	  joinData.put("emailDiv", emailDiv);
	  joinData.put("addressNum", input_postal);
	  joinData.put("address", address);
	  joinData.put("user_role", userRole);
	  
	  System.out.println(joinData);
	  userMapper.insertjoinUsersData(joinData);
	  
	  mav.setViewName("/member/join");
	
	  return mav;
  }
  
  /* 
   * ID 찾기
   * 2017-08-30
   * 윤희현
   */
  @RequestMapping(value = "/searchId" , method = RequestMethod.GET) 
  @ResponseBody
  public String searchId(HttpServletRequest req, HttpServletResponse rep, Model model,
		  @RequestParam("userNm") String userNm, @RequestParam("userTel") String userTel) throws SQLException {
			System.out.println(userNm);
			System.out.println(userTel);
			Map<Object, Object> param = new HashMap<>();

			param.put("userNm", userNm);			
			param.put("userTel", userTel);		
			String searchId = userMapper.searchId(param);
			System.out.println("confirmId");
			System.out.println(searchId);
			String id = "password";
	  return searchId;
  }
	
}
