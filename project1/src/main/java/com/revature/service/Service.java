package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Request;
import com.revature.model.Users;
import com.revature.repository.ReinbursementDAOPostgres;

public class Service {

  Users user;
  ReinbursementDAOPostgres dao = new ReinbursementDAOPostgres();
  Request request;
  
  public Service() {
    super();
  }

  public Users login(String email, String password) throws UserNotFoundException{
    user = dao.verifyLoginCredentials(email, password);
    return user;
  }
  
  public void logout() {
    user = new Users();
    dao.logout();
  }
  
  public ArrayList<Users> viewAllEmployees() {
    return dao.viewAllEmployees();
  }
  
  public ArrayList<Request> viewAllRequests() {
    return dao.viewAllRequests();
  }
  
  public ArrayList<Request> viewPendingRequests() {
    return dao.viewPendingRequests();
  }
  
  public ArrayList<Request> viewResolvedRequests() {
    return dao.viewResolvedRequests();
  }
  public void updateEmail(String email) {
    user.setEmail(email);
    dao.updateEmail(email);
  }

  public void updateName(String name) {
    user.setName(name);
    dao.updateName(name);
  }

  public void updatePassword(String password) {
    user.setPassword(password);
    dao.updatePassword(password);
  }
  
  public void processRequest(List<Request> requests) {
    dao.processRequest(requests);
  }

  public ArrayList<Request> searchRequest(String searchUser) {
    return dao.searchRequest(searchUser);
  }

  public void processRequest(Request request) {
    dao.processRequest(request);
  }

}
