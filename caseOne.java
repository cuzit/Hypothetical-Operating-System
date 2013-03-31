public class caseOne extends Thread {
  //Operate casetype 1
  //First-in, First-out, First-fit allocation policy
  Boolean caseComplete = false;
  int tick = 0;
  Queue jobs;
  MainMemory main;
  
  public caseOne(Queue jobs, MainMemory main) {
    this.jobs = jobs;
    this.main = main;
  }
  
  public void run() {
    while(!caseComplete) {
      if(jobs.getAnyUnassignedJobs()) {
	//Get the first available waiting job
	int jobToTry = -1;
	for(int i = 0; i < jobs.getLength(); i++) {
	  if(jobToTry == -1) {
	    if(jobs.getStatus(i) == "Waiting") {
	      jobToTry = i;
	    }
	  }
	}
	
	if(main.memoryAvailable()) {
	  //If there is at least one available position in memory
	  Boolean finished = false;
	  while(!finished) {
	    Boolean assigned = false;
	    for(int i = main.firstAvailableMemoryPos(); i < main.size && assigned == false; i++) {
	      if(!main.getInUse(i)) {
		//This memory module is not in use.
		if(jobs.getMemRequest(jobToTry) < main.getSize(i)) {
		  //The job can fit in this memory block
		  Boolean success;
		  success = main.assignMemory(i, jobs.getJob(jobToTry));
		  if(success) {
		    assigned = true;
		  }
		}
	      }
	    }
	    
	    if(assigned == false) {
	      //If this equals false, then this job did not get assigned - probably
	      //because there are no available positions in memory it can fit in.
	      System.out.println("The job couldn't fit anywhere...");
	      
	      //Find another job to try...
	      if(jobToTry + 1 < jobs.getLength()) {
		Boolean reassigned = false;
		for(int i = jobToTry + 1; i < jobs.getLength(); i++) {
		  if(!reassigned) {
		    if(jobs.getStatus(i) == "Waiting") {
		      jobToTry = i;
		      reassigned = true;
		    }
		  }
		}
	      }
	      
	      else {
		System.out.println("None of the waiting memory can fit in any of the currently empty memory slots.");
		finished = true;
	      }
	    }
	    
	    else {
	      //Everything is happy!
	      finished = true;
	    }
	  }
	}
      }
      
      //If verbose, print what this tick looks like
      System.out.println(main.toString());
      
      //Increase the tick by 1.
      tick++;
      System.out.println(tick);
      main.tick();
      
      //Break the while loop if all jobs are done.
      if(!jobs.getAnyUnfinishedJobs()) {
	caseComplete = true;
      }
    }
  }
}