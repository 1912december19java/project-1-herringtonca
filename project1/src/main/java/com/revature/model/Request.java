package com.revature.model;

public class Request{

  String description;
  double amount;
  String reciptLink;
  String status = "Pending";
  String resolvedBy;
  
  public Request() {
    super();
  }


  public Request(String description, double amount, String status, String resolvedBy) {
    super();
    this.description = description;
    this.amount = amount;
    this.status = status;
    this.resolvedBy = resolvedBy;
  }


  public String getResolvedBy() {
    return resolvedBy;
  }


  public void setResolvedBy(String resolvedBy) {
    this.resolvedBy = resolvedBy;
  }


  public Request(String description, double amount, String status) {
    super();
    this.description = description;
    this.amount = amount;
    this.status = status;
  }

  public Request(String description, double amount) {
    super();
    this.description = description;
    this.amount = amount;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public double getAmount() {
    return amount;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }
  public String getReciptLink() {
    return reciptLink;
  }
  public void setReciptLink(String reciptLink) {
    this.reciptLink = reciptLink;
  }

  @Override
  public String toString() {
    return "Request [description=" + description + ", amount=" + amount + ", reciptLink="
        + reciptLink + ", status=" + status + ", resolvedBy=" + resolvedBy + "]";
  }

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(amount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((reciptLink == null) ? 0 : reciptLink.hashCode());
    result = prime * result + ((resolvedBy == null) ? 0 : resolvedBy.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Request other = (Request) obj;
    if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (reciptLink == null) {
      if (other.reciptLink != null)
        return false;
    } else if (!reciptLink.equals(other.reciptLink))
      return false;
    if (resolvedBy == null) {
      if (other.resolvedBy != null)
        return false;
    } else if (!resolvedBy.equals(other.resolvedBy))
      return false;
    if (status == null) {
      if (other.status != null)
        return false;
    } else if (!status.equals(other.status))
      return false;
    return true;
  }
  
}
