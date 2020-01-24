package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Employee;
import com.revature.model.Password;
import com.revature.model.Request;
import com.revature.model.Users;
import com.revature.service.EmployeeService;
import com.revature.service.ManagerService;
import com.revature.service.Service;


@WebServlet(name = "mainServlet", urlPatterns = {"/main/*"})
public class MainServlet extends HttpServlet {
  private ObjectMapper om = new ObjectMapper();
  private Users user = new Users();
  private Service service = new Service();
  private Request request = new Request();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    System.out.println("GET");
    String reqURI[] = req.getRequestURI().split("/");
    if (reqURI.length > 3 && reqURI[3].equals("profile")) {
      resp.getWriter().write(om.writeValueAsString(user));
    }
    if (reqURI.length > 3 && reqURI[3].equals("homescreen")) {
      resp.getWriter().write(om.writeValueAsString(user));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    System.out.println("Reached " + req.getMethod() + " at " + req.getRequestURI());

    String reqURI[] = req.getRequestURI().split("/");
    if (reqURI[2].equals("main") && reqURI.length < 4) {
      user = om.readValue(req.getReader(), Users.class);
      try {
        user = service.login(user.getEmail(), user.getPassword());
        if (user.getType().equals("manager")) {
          service = new ManagerService(user);
          resp.sendRedirect("http://localhost:8080/project1/home-screen-manager.html");
        } else {
          service = new EmployeeService(user);
          resp.sendRedirect("http://localhost:8080/project1/home-screen.html");
        }
      } catch (UserNotFoundException e) {
        resp.setStatus(400);
      }
    }
    if (reqURI.length > 3 && reqURI[3].equals("homescreen")) {
      user = new Users();
      service.logout();
      resp.sendRedirect("http://localhost:8080/project1/index.html");
    }
    if (reqURI.length > 3 && reqURI[3].equals("profile")) {
      Users updatedUser = om.readValue(req.getReader(), Users.class);
      user.setEmail(updatedUser.getEmail());
      user.setName(updatedUser.getName());
      service.updateEmail(user.getEmail());
      service.updateName(user.getName());
    }
    if (reqURI.length > 3 && reqURI[3].equals("profile-pass")) {
      Password password = om.readValue(req.getReader(), Password.class);
      user.setPassword(password.getPassword());
      service.updatePassword(password.getPassword());
    }
    if (reqURI.length > 3 && reqURI[3].equals("request")) {
      request = om.readValue(req.getReader(), Request.class);
      service.processRequest(request);
    }
    if (reqURI.length > 3 && reqURI[3].equals("request1")) {
      List<Request> allRequests;
      allRequests = service.viewAllRequests();
      resp.getWriter().write(om.writeValueAsString(allRequests));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request2")) {
      ArrayList<Request> fufilledRequests = new ArrayList<Request>();
      fufilledRequests = service.viewResolvedRequests();
      resp.getWriter().write(om.writeValueAsString(fufilledRequests));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request3")) {
      ArrayList<Request> pendingRequests = new ArrayList<Request>();
      pendingRequests = service.viewPendingRequests();
      resp.getWriter().write(om.writeValueAsString(pendingRequests));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request-search")) {
      String searchUser = om.readValue(req.getReader(), String.class);
      ArrayList<Request> userReq = new ArrayList<Request>();
      userReq = service.searchRequest(searchUser);
      resp.getWriter().write(om.writeValueAsString(userReq));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request-manager2")) {
      ArrayList<Users> allEmployees = new ArrayList<Users>();
      allEmployees = service.viewAllEmployees();
      resp.getWriter().write(om.writeValueAsString(allEmployees));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request-manager1")) {
      ArrayList<Request>requests = new ArrayList<Request>();
      requests = service.viewResolvedRequests();
      resp.getWriter().write(om.writeValueAsString(requests));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request-manager")) {
      ArrayList<Request>requests = new ArrayList<Request>();
      requests = service.viewPendingRequests();
      resp.getWriter().write(om.writeValueAsString(requests));
    }
    if (reqURI.length > 3 && reqURI[3].equals("request-manager3")) {
      Request[] updatedReqs = om.readValue(req.getReader(), Request[].class);
      List<Request> updatedRequests = new ArrayList<>(Arrays.asList(updatedReqs));
      for(Request e : updatedRequests) {
        e.setResolvedBy(user.getName());
        System.out.println(e.toString());
      }
      service.processRequest(updatedRequests);
    }
  }
}
