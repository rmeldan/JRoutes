package com.softserve.edu.jroutes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.service.UserServiceImpl;


public class UserServiceTest {
	
	@Test
	public void filterUsersTest1() {
		//prepare input data
		List<User> userList = new ArrayList<User>();
		User u1 = new User();
		u1.setEmail("u1@ukr.net");
		User u2 = new User();
		u2.setEmail("u2@ukr.net");
		userList.add(u1);
		userList.add(u2);
		
		UserServiceImpl service = new UserServiceImpl();
		
		ArrayList<User> result = (ArrayList<User>) service.filterUsers(userList);
		
		Assert.assertTrue(result.size() == userList.size());
	}
	
	@Test
	public void filterUsersTest2() {
		//prepare input data
		List<User> userList = new ArrayList<User>();
		User u1 = new User();
		u1.setEmail("u1@ukr.net");
		User u2 = new User();
		u2.setEmail("u2@ukr.net");
		User u3 = new User();
		u3.setEmail("u2@ukr.net");
		userList.add(u1);
		userList.add(u2);
		userList.add(u3);
		
		UserServiceImpl service = new UserServiceImpl();
		
		ArrayList<User> result = (ArrayList<User>) service.filterUsers(userList);
		
		Assert.assertTrue(result.size() == 2);
	} 
	
	
	
	
	
	
}
