/*************************************************************
*Job                                                         *
**************************************************************
*Matt Silvey                                                 *
*Dan Wang                                                    *
**************************************************************
*CS350                                                       *
*Term Project                                                *
**************************************************************
*Is the class representing a job in the HOS;                 *
*It has the following elements:                              *
*  Job identification number                                 *
*  Memory request (in MB)                                    *
*  Execution time request                                    *
*  Memory assigned (segment number)                          *
*  Execution time remaining (initially the same as execution *
*       time request)                                        *
*  Job status: Waiting, Ready, Running, & Finished           *
**************************************************************/

public class Job {
  /***Variables***/
  int id;
  int memRequest;
  int timeRequest;
  int memAssigned;
  int timeRemain;
  String status;
  
  /***Constructors***/
  public Job() {
    id = -1;
    memRequest = -1;
    timeRequest = -1;
    memAssigned = -1;
    timeRemain = timeRequest;
    status = "Waiting";
  }
  
  public Job(int id, int memRequest, int timeRequest, int memAssigned, int timeRemain, String status) {
    this.id = id;
    this.memRequest = memRequest;
    this.timeRequest = timeRequest;
    this.memAssigned = memAssigned;
    this.timeRemain = timeRemain;
    this.status = status;
  }
  
  public Job(Job job) {
    this.id = job.getID();
    this.memRequest = job.getMemRequest();
    this.timeRequest = job.getTimeRequest();
    this.memAssigned = job.getMemAssigned();
    this.timeRemain = job.getTimeRemain();
    this.status = job.getStatus();
  }
  
  /***Setters***/
  public void setID(int id) {
    this.id = id;
  }
  
  public void setMemRequest(int memRequest) {
    this.memRequest = memRequest;
  }
  
  public void setTimeRequest(int timeRequest) {
    this.timeRequest = timeRequest;
  }
  
  public void setMemAssigned(int memAssigned) {
    this.memAssigned = memAssigned;
  }
  
  public void setTimeRemain(int timeRemain) {
    this.timeRemain = timeRemain;
  }
  
  public void setStatus(String status) {
    if(status == "Waiting" || status == "Ready" || status == "Running" || status == "Finished") {
      this.status = status;
    }
  }
  
  /***Getters***/
  public int getID() {
    return id;
  }
  
  public int getMemRequest() {
    return memRequest;
  }
  
  public int getTimeRequest() {
    return timeRequest;
  }
  
  public int getMemAssigned() {
    return memAssigned;
  }
  
  public int getTimeRemain() {
    return timeRemain;
  }
  
  public String getStatus() {
    return status;
  }
  
  /***toString***/
  public String toString() {
    return id + " " + memRequest + " " + timeRequest + " " + memAssigned + " "
	      + timeRemain + " " + status;
  }
  
  /***Test***/
  public static void main(String[] args) {
    Job test = new Job();
    test.setID(10);
    test.setMemRequest(3);
    test.setTimeRequest(8);
    test.setMemAssigned(2);
    test.setTimeRemain(7);
    test.setStatus("Waiting");
    System.out.println(test.toString());
  }
}