public class caseOne extends Thread {
  //Operate casetype 1
  //First-in, First-out, First-fit allocation policy
  Boolean caseComplete = false;
  int tick = 0;
  Queue jobs;
  MainMemory main;
  Boolean verbose = false;
  
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
	  int attempts = 0;
	  
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
	      if(verbose) {
		System.out.println("The job couldn't fit anywhere...");
	      }
	      
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
		if(verbose) {
		  System.out.println("None of the waiting memory can fit in any of the currently empty memory slots.");
		}
		finished = true;
	      }
	    }
	    
	    else {
	      //Everything is happy!
	      finished = true;
	    }
	    
	    attempts++;
	    if(attempts > jobs.getLength() + 1) {
	      if(verbose) {
		System.out.println("Attempts this tick: " + attempts);
		System.out.println("This tick has had too many attempts. Moving on to the next tick...");
	      }
	      
	      finished = true;
	    }
	  }
	}
      }
      
      //If verbose, print what this tick looks like
      if(verbose) {
	System.out.println(main.toString());
      }
      
      //Regardless of verbosity, print out the unit of time as per the instructions.
      System.out.println("Tick: " + tick);
      System.out.println(jobs.toString());
      
      //Increase the tick by 1.
      tick++;
      main.tick();
      
      //Break the while loop if all jobs are done.
      if(!jobs.getAnyUnfinishedJobs()) {
	caseComplete = true;
      }
      
      //Break the while loop if one more tick will be the 30th (max) tick, per the instructions.
      //Comment this out if you wish for the program to execute until ALL jobs reach a "Finished" state.
      if(tick >= 29) {
	caseComplete = true;
      }
    }
    
    //Tick once more - this is necessary because the while loop breaks when one or more jobs
    //may have 0 time remaining, but the Job hasn't Finished yet.
    tick++;
    main.tick();
    System.out.println("Tick: " + tick);
    System.out.println(jobs.toString());
  }
  
  public void setVerbose(Boolean verbose) {
    this.verbose = verbose;
  }
}