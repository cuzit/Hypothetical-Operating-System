/**************************************************
*Queue                                            *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This class represents the waiting list of all    *
*processes waiting to be executed                 *
*It is an array of 20 Job objects                 *
*It also has functions for retrieving and setting *
*all data relevant to the queue itself and its    *
*individual objects, as well as returning info    *
*related to the status of the Jobs, such as if any*
*jobs remain with a "Waiting" status              *
***************************************************/

public class Queue {
  /***Constructor***/
  public Job[] jobQueue;
  public Queue() {
    jobQueue = new Job[20];
    for(int i = 0; i < 20; i++) {
      jobQueue[i] = new Job();
    }
  }
  
  public Queue(Queue queue) {
    jobQueue = new Job[20];
    for(int i = 0; i < queue.getLength(); i++) {
      Job temp = new Job(queue.getJob(i));
      setJob(i, temp);
    }
  }
  
  /***Getters***/
  public int getID(int segment) {
    return jobQueue[segment].getID();
  }
  
  public int getMemRequest(int segment) {
    return jobQueue[segment].getMemRequest();
  }
  
  public int getTimeRequest(int segment) {
    return jobQueue[segment].getTimeRequest();
  }
  
  public int getMemAssigned(int segment) {
    return jobQueue[segment].getMemAssigned();
  }
  
  public int getTimeRemain(int segment) {
    return jobQueue[segment].getTimeRemain();
  }
  
  public String getStatus(int segment) {
    return jobQueue[segment].getStatus();
  }
  
  public Job getJob(int segment) {
    return jobQueue[segment];
  }
  
  public int getLength() {
    //Return the length of the jobQueue
    return jobQueue.length;
  }
  
  /***Setters***/
  public void setID(int segment, int id){
    jobQueue[segment].setID(id);
  }
  
  public void setMemRequest(int segment, int memRequest) {
    jobQueue[segment].setMemRequest(memRequest);
  }
  
  public void setTimeRequest(int segment, int timeRequest) {
    jobQueue[segment].setTimeRequest(timeRequest);
  }
  
  public void setMemAssigned(int segment, int memAssigned) {
    jobQueue[segment].setMemAssigned(memAssigned);
  }
  
  public void setTimeRemain(int segment, int timeRemain) {
    jobQueue[segment].setTimeRemain(timeRemain);
  }
  
  public void setStatus(int segment, String status) {
    jobQueue[segment].setStatus(status);
  }
  
  public void setJob(int segment, Job job) {
    jobQueue[segment] = job;
  }
  
  /***Functions***/
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
 
 public Queue shortestJobs() {
    //Return a copy of this queue object ordered from shortest job to longest job
    Queue copy = new Queue();
    
    //Copy the current Queue into the copy Queue
    for(int i = 0; i < getLength(); i++) {
      copy.setJob(i, getJob(i));
    }
    
    for(int i = 0; i < getLength() - 1; i++) {
      for(int j = 0; j < getLength() - 1 - i; j++) {
	if(copy.getTimeRequest(j) > copy.getTimeRequest(j + 1)) {
	  Job temp = copy.getJob(j);
	  copy.setJob(j, copy.getJob(j + 1));
	  copy.setJob(j + 1, temp);
	}
      }
    }
    
    //Return the copy of the Queue sorted into SJF order.
    return copy;
  }
  
  /***toString***/
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
  
  /***Driver for shortestJobs Function***/
  public static void main(String args[]) {
    //Written to test the shortestJobs function
    Queue first = new Queue();
    Queue second = new Queue();
    
    for(int i = 0; i < first.getLength(); i++) {
      //Add data to the queue
      first.setMemRequest(i, (int)(Math.random() * 100));
      first.setID(i, i);
      first.setStatus(i, "Waiting");
      first.setTimeRequest(i, (int)(Math.random() * 10));
    }
    
    System.out.println("First:\n" + first.toString());
    
    second = first.shortestJobs();
    
    System.out.println("Second:\n" + second.toString());
  }
}