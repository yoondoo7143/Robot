package com.dangam.namu.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
*
* UserMapper
* 2017-08-30
* 윤희현
* 
*/
@Mapper
public interface UserMapper {
	String selectToday();

	List<HashMap<String, Object>> test();
	
	void insertjoinUsersData(Map<Object, Object> joinData);

	int confirmId(String userId);

	String searchId(Map<Object, Object> param);

	String searchPw(String id);

	// Intercepter 회원정보 가져오기 위해 사용 중.
	List<HashMap<String, Object>> findOne(String userId);

}
