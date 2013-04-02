public class Queue {
  //Constructor
  public Job[] jobQueue;
  public Queue() {
    jobQueue = new Job[20];
    for(int i = 0; i < 20; i++) {
      jobQueue[i] = new Job();
    }
  }
  
  //setters and getters
  public void setID(int segment, int id){
    jobQueue[segment].setID(id);
  }
  
  public int getID(int segment) {
    return jobQueue[segment].getID();
  }
  
  
  public void setMemRequest(int segment, int memRequest) {
    jobQueue[segment].setMemRequest(memRequest);
  }
  
  public int getMemRequest(int segment) {
    try {
    return jobQueue[segment].getMemRequest();
    }
    catch(ArrayIndexOutOfBoundsException e) {
      System.out.println(toString());
      return -1;
    }
  }
  
  
  public void setTimeRequest(int segment, int timeRequest) {
    jobQueue[segment].setTimeRequest(timeRequest);
  }
  
  public int getTimeRequest(int segment) {
    return jobQueue[segment].getTimeRequest();
  }
  
  
  public void setMemAssigned(int segment, int memAssigned) {
    jobQueue[segment].setMemAssigned(memAssigned);
  }
  
  public int getMemAssigned(int segment) {
    return jobQueue[segment].getMemAssigned();
  }
  
  
  public void setTimeRemain(int segment, int timeRemain) {
    jobQueue[segment].setTimeRemain(timeRemain);
  }
  
  public int getTimeRemain(int segment) {
    return jobQueue[segment].getTimeRemain();
  }
  
  
  public void setStatus(int segment, String status) {
    jobQueue[segment].setStatus(status);
  }
  
  public String getStatus(int segment) {
    return jobQueue[segment].getStatus();
  }
  
  
  public void setJob(int segment, Job job) {
    jobQueue[segment] = job;
  }
  
  public Job getJob(int segment) {
    return jobQueue[segment];
  }
  
  
  public int getLength() {
    //Return the length of the jobQueue
    return jobQueue.length;
  }
  
  public Boolean getAnyUnassignedJobs() {
    //Return true if there are any jobs that need to be assigned - false otherwise.
    for(int i = 0; i < getLength(); i++) {
      if(getStatus(i) == "Waiting") {
	return true;
      }
    }
    
    return false;
  }
  
  public Boolean getAnyUnfinishedJobs() {
    //Return true if there are any unfinished jobs.
    for(int i = 0; i < getLength(); i++) {
      if(getStatus(i) != "Finished") {
	return true;
      }
    }
    
    return false;
  }
  
 public int numberOfWaitingJobs() {
    //Return the number of jobs that are waiting.
    int count = 0;
    for(int i = 0; i < getLength(); i++) {
      if(getStatus(i) == "Waiting") {
	count++;
      }
    }
    return count;
 }
 
 public int numberOfFinishedJobs() {
    //Return the number of jobs that are finished
    int count = 0;
    for(int i = 0; i < getLength(); i++) {
      if(getStatus(i) == "Finished") {
	count++;
      }
    }
    return count;
 }
  
  public String toString() {
    String printout = "=========================================================================\n";
    
    printout += "Time\tID\tSegment\tMem Request\tTime Remain\tMessages\n";
    printout += "----\t--\t-------\t-----------\t-----------\t--------\n";
    
    for(int i = 0; i < getLength(); i++) {
      printout += jobQueue[i].getTimeRequest() + "\t";
      printout += jobQueue[i].getID() + "\t";
      printout += jobQueue[i].getMemAssigned() + "\t";
      printout += jobQueue[i].getMemRequest() + "\t\t";
      printout += jobQueue[i].getTimeRemain() + "\t\t";
      printout += jobQueue[i].getStatus() + "\n";
    }
    
    printout += "=========================================================================";
    
    return printout;
  }
}