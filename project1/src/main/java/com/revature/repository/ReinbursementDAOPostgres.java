package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Request;
import com.revature.model.Users;

public class ReinbursementDAOPostgres implements ReinbursementDAO {

  private static Connection conn;
  private Users user;

  // This guy will run when the class loads, after static fields are initialized.
  static {
    // This explicitly loads the Driver class:
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    }
    try {
      conn = DriverManager.getConnection(System.getenv("connstring"), System.getenv("username"),
          System.getenv("password"));
    } catch (SQLException e) {
      System.out
          .println("Database connection failed, check your dependencies and enviornment variables");
    }
  }


  public ReinbursementDAOPostgres() {
    super();
  }

  public ReinbursementDAOPostgres(Users user) {
    super();
    this.user = user;
  }

  public ArrayList<Request> searchRequest(String searchUser) {
    ArrayList<Request> userReq = new ArrayList<Request>();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.prepareStatement(
          "SELECT description, amount, status, resolved_by FROM requests JOIN users ON users.id=requests.user_id WHERE user_name = ?");
      stmt.setString(1, searchUser);
      if (stmt.execute())
        ;
      rs = stmt.getResultSet();
      while (rs.next()) {
        if (rs.getString("status").equals("Pending"))
          userReq.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status"), rs.getString("resolved_by")));
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
    return userReq;
  }

  public ArrayList<Request> viewAllRequests() {
    ArrayList<Request> allRequests = new ArrayList<Request>();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    if (user.getType().equals("employee")) {
      try {
        stmt = conn.prepareStatement(
            "SELECT * FROM requests JOIN users ON users.id = requests.user_id WHERE email = ?");
        stmt.setString(1, user.getEmail());
        if (stmt.execute()) {
          rs = stmt.getResultSet();
        }
        while (rs.next()) {
          allRequests.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status"), rs.getString("resolved_by")));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return allRequests;
    } else {
      return allRequests;
    }
  }

  public void processRequest(Request request) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
      stmt.setString(1, user.getEmail());
      stmt.execute();
      rs = stmt.getResultSet();
      rs.next();
      stmt = conn.prepareStatement(
          "INSERT INTO requests(user_id, amount, description, status) VALUES (?,?,?,?)");
      stmt.setInt(1, rs.getInt("id"));
      stmt.setDouble(2, request.getAmount());
      stmt.setString(3, request.getDescription());
      stmt.setString(4, request.getStatus());
      stmt.execute();

    } catch (SQLException e) {
      // TODO: handle exception
    }
  }

  public void processRequest(List<Request> requests) {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      for (Request r : requests) {
        stmt = conn.prepareStatement("UPDATE requests SET status = ?, resolved_by = ? WHERE description = ? AND amount = ?");
        stmt.setString(1, r.getStatus());
        stmt.setString(2, r.getResolvedBy());
        stmt.setString(3, r.getDescription());
        stmt.setDouble(4, r.getAmount());
        stmt.execute();
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
  }

  public ArrayList<Request> viewResolvedRequests() {
    ArrayList<Request> fufilledRequests = new ArrayList<Request>();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    if (user.getType().equals("employee")) {
      try {
        stmt = conn.prepareStatement(
            "SELECT status, amount, description, resolved_by FROM requests JOIN users ON users.id = requests.user_id WHERE email = ? and status != 'Pending'");
        stmt.setString(1, user.getEmail());
        if (stmt.execute()) {
          rs = stmt.getResultSet();
        }
        while (rs.next()) {
          fufilledRequests.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status"), rs.getString("resolved_by")));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return fufilledRequests;
    } else {
      try {
        stmt = conn.prepareStatement(
            "SELECT user_name, status, amount, description, resolved_by FROM requests JOIN users ON users.id = requests.user_id WHERE status != 'Pending'");
        if (stmt.execute()) {
          rs = stmt.getResultSet();
        }
        while (rs.next()) {
          fufilledRequests.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status"), rs.getString("resolved_by")));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return fufilledRequests;
    }
  }

  public ArrayList<Users> viewAllEmployees() {
    ArrayList<Users> allEmployees = new ArrayList<Users>();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.prepareStatement("SELECT * FROM users");
      if (stmt.execute()) {
        rs = stmt.getResultSet();
      }
      while (rs.next()) {
        allEmployees.add(new Users(rs.getString("user_name"), rs.getString("user_type")));
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
    return allEmployees;
  }

  public ArrayList<Request> viewPendingRequests() {
    ArrayList<Request> pendingRequests = new ArrayList<Request>();

    PreparedStatement stmt = null;
    ResultSet rs = null;

    if (user.getType().equals("employee")) {
      try {
        stmt = conn.prepareStatement(
            "SELECT status, amount, description FROM requests JOIN users ON users.id = requests.user_id WHERE email = ? and status = 'Pending'");
        stmt.setString(1, user.getEmail());
        if (stmt.execute()) {
          rs = stmt.getResultSet();
        }
        while (rs.next()) {
          pendingRequests.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status")));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return pendingRequests;
    } else {
      try {
        stmt = conn.prepareStatement(
            "SELECT status, amount, description FROM requests WHERE status = 'Pending'");
        if (stmt.execute()) {
          rs = stmt.getResultSet();
        }
        while (rs.next()) {
          pendingRequests.add(new Request(rs.getString("description"), rs.getDouble("amount"),
              rs.getString("status")));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return pendingRequests;
    }

  }

  public Users verifyLoginCredentials(String email, String password) throws UserNotFoundException {
    user = new Users();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND user_password = ?");
      stmt.setString(1, email);
      stmt.setString(2, password);
      if (stmt.execute()) {
        rs = stmt.getResultSet();
        if (rs.next()) {
          user.setName(rs.getString("user_name"));
          user.setEmail(rs.getString("email"));
          user.setPassword(rs.getString("user_password"));
          user.setType(rs.getString("user_type"));
          Users returnUser = user;
          return returnUser;
        } else
          throw new UserNotFoundException();
      }
    } catch (SQLException e) {

    }
    return null;
  }

  public void logout() {
    user = new Users();
  }

  public void updateEmail(String email) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("UPDATE users SET email = ? WHERE email = ?");
      stmt.setString(1, email);
      stmt.setString(2, user.getEmail());
      stmt.execute();
      user.setEmail(email);
    } catch (SQLException e) {
      // TODO: handle exception
    }
  }

  public void updateName(String name) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("UPDATE users SET user_name = ? WHERE email = ?");
      stmt.setString(1, name);
      stmt.setString(2, user.getEmail());
      stmt.execute();
      user.setName(name);
    } catch (SQLException e) {
      // TODO: handle exception
    }
  }

  public void updatePassword(String password) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement("UPDATE users SET user_password = ? WHERE email = ?");
      stmt.setString(1, password);
      stmt.setString(2, user.getEmail());
      stmt.execute();
      user.setPassword(password);
    } catch (SQLException e) {
      // TODO: handle exception
    }
  }
}
