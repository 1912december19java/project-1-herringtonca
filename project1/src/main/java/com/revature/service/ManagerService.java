package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import com.revature.model.Request;
import com.revature.model.Users;
import com.revature.repository.ReinbursementDAOPostgres;

public class ManagerService extends Service{
  
  Users manager;
  ReinbursementDAOPostgres dao;
  Request req;
  
  

  public ManagerService(Users manager) {
    super();
    this.manager = manager;
    dao = new ReinbursementDAOPostgres(manager);
  }

  public void logout() {
    
  }
  
  public ArrayList<Request> searchRequest(String searchUser){
    return dao.searchRequest(searchUser);
  }
  
  public void processRequest(List<Request> request) {
    dao.processRequest(request);
  }

  public ArrayList<Users> viewAllEmployees() {

    return dao.viewAllEmployees();
    
  }

  public ArrayList<Request> viewPendingRequests() {
    return dao.viewPendingRequests();
    
  }

  public ArrayList<Request> viewResolvedRequests() {
    return dao.viewResolvedRequests();
  }

  
  
}
