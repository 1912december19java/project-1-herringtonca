package com.revature.repository;

import java.util.ArrayList;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Request;
import com.revature.model.Users;

public interface ReinbursementDAO {

  ArrayList<Request> searchRequest(String searchUser);
  
  ArrayList<Users> viewAllEmployees();
  
  ArrayList<Request> viewAllRequests();
  
  void processRequest(Request request);
  
  ArrayList<Request> viewResolvedRequests();
      
  ArrayList<Request> viewPendingRequests();
  
  Users verifyLoginCredentials(String email, String password) throws UserNotFoundException;
  
  void updateEmail(String email);
  
  void updateName(String name);
  
  void updatePassword(String password);
  
  void logout();
}
