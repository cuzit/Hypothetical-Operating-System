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
      
      //Assign Jobs to memory
      Boolean assigned = false;
      int count = 0;
      while(!assigned) {
	//If there are no available positions in the memory left
	if(!main.memoryAvailable()) {
	  assigned = true;
	}
	
	//If there are no more Jobs that have not been completed or currently assigned
	if(!jobs.getAnyUnassignedJobs()) {
	  assigned = true;
	}
	
	//If every job has been checked, but for whatever reason (i.e. none of the jobs
	//can fit in the currently available memory) a Job cannot be assigned.
	if(count >= jobs.getLength()) {
	  assigned = true;
	}
	
	//Increase the count until it equals the position in the job queue of an unassigned Job
	Boolean testDone = false;
	while(!testDone) {
	  if(count < jobs.getLength()) {
	    if(jobs.getStatus(count) == "Waiting") {
	      testDone = true;
	    }
	  }
	  
	  else {
	    assigned = true;
	    testDone = true;
	  }
	  
	  if(testDone == false) {
	    count++;
	  }
	}
	
	
	//Begin assigning jobs until the above conditions are no longer true.
	if(!assigned) {
	  //This section should be skipepd if assigned is true.
	  if(count < jobs.getLength()) {
	    //During this tick, there are still Jobs that haven't been checked that may need
	    //to be assigned.
	    Boolean success = false;
	    for(int i = main.firstAvailableMemoryPos(); i < main.size && success == false; i++) {
	      if(!main.getInUse(i)) {
		//This memory module is not in use.
		if(jobs.getMemRequest(count) < main.getSize(i)) {
		  //This job can fit in the memory block
		  main.assignMemory(i, jobs.getJob(count));
		  success = true;
		}
	      }
	    }
	    
	    
	  }
	}
	
	//Increase the count through the job queue.
	count++;
      }
      
      //If verbose, print what this tick looks like
      if(verbose) {
	System.out.println(main.toString());
      }
      
      //Increase the tick by 1.
      tick();
      
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
    tick();
    
    //Now that this case has executed, output the total number of jobs completed.
    System.out.println("Total number of finished jobs: " + jobs.numberOfFinishedJobs());
  }
  
  public void tick() {
    tick++;
    main.tick();
    System.out.println("=========================================================================");
    System.out.println("Tick: " + tick);
    System.out.println(jobs.toString());
    System.out.println("Waiting: " + jobs.numberOfWaitingJobs() + "\t\t");
    System.out.println("Total Memory Wasted: " + main.totalWastedMemory());
    System.out.println("=========================================================================");
    System.out.println("\n\n\n");
  }
  
  public void setVerbose(Boolean verbose) {
    this.verbose = verbose;
  }
}