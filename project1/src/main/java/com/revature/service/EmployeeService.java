package com.revature.service;

import java.util.ArrayList;
import com.revature.model.Request;
import com.revature.model.Users;
import com.revature.repository.ReinbursementDAOPostgres;

public class EmployeeService extends Service{
  
  Users employee;
  ReinbursementDAOPostgres dao;
  Request request = new Request();
  
  
  public EmployeeService(Users employee) {
    super();
    this.employee = employee;
    this.dao = new ReinbursementDAOPostgres(employee);
  }
  
  public void updateEmail(String email) {
    employee.setEmail(email);
    dao.updateEmail(email);
  }

  public void updateName(String name) {
    employee.setName(name);
    dao.updateName(name);
  }

  public void updatePassword(String password) {
    employee.setPassword(password);
    dao.updatePassword(password);
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
  
  public void processRequest(Request request) {
    dao.processRequest(request);
  }
  
  
}
