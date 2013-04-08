/**************************************************
*caseThree                                        *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This class executes the HOS with case one's      *
*policies, which are:                             *
*	SJFS (Shortest-Job, First-Served) Order   *
*	Best Fit Allocation Policy                *
*This class is responsible for the "logic" of the *
*HOS for this particular case                     *
***************************************************/

public class caseThree {
  /***Variables***/
  Boolean caseComplete = false;
  int tick = 0;
  Queue jobs;
  Queue SJFjobs; //Copy of jobs ordered from the shortest job first to the longest job last
  MainMemory main;
  Boolean verbose = false;
  int numberOfFinishedJobs = 0;
  
  /***Constructor***/
  public caseThree(Queue jobs, MainMemory main) {
    this.jobs = jobs;
    this.main = main;
    
    SJFjobs = jobs.shortestJobs();
  }
  
  /***Execute the HOS on Case Type 3***/
  public void run() {
    while(!caseComplete) {
      if(verbose) { System.out.println("I'm running!"); }
      //Assign Jobs to memory
      Boolean assigned = false;
      int count = 0;
      int jobToAssign = 0;
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
	    if(SJFjobs.getStatus(count) == "Waiting") {
	      jobToAssign = SJFjobs.getID(count);
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
	if(!assigned){
	  //This section should be skipepd if assigned is true.
	  if(count < jobs.getLength()) {
	    //During this tick, there are still Jobs that haven't been checked that may need
	    //to be assigned.
	    Boolean success = false;
	    for(int i = main.firstAvailableMemoryPos(); i < main.size && success == false; i++) {
	      if(!main.getInUse(i)) {
		 //This memory module is not in use.
		  if(jobs.getMemRequest(jobToAssign) < main.getSize(i)) {
		    if(verbose) { System.out.println("A"); }
		    int[] temp = new int[main.size];
		    for(int j = 0; j < main.size; j++) {
		      temp[j] = main.sizeArray[j] - jobs.getMemRequest(j);
		    }
		    
		    if(verbose) { System.out.println("B"); }
		    int currentSmallest = 0;
		    for(int j = 0; currentSmallest + j < temp.length; j++) {
		      if(temp[currentSmallest + j] < temp[currentSmallest] && temp[currentSmallest + j] >= 0) {
			currentSmallest = currentSmallest + j;
			j = 0;
		      }
		    }
		    
		    if(verbose) { System.out.println("C"); }
		    //This job can fit in the memory block
		    main.assignMemory(i, jobs.getJob(jobToAssign));
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
      if(tick >= 30) {
	caseComplete = true;
      }
    }
    
    //Now that this case has executed, output the total number of jobs completed.
    numberOfFinishedJobs = jobs.numberOfFinishedJobs();
    System.out.println("Total number of finished jobs: " + numberOfFinishedJobs);
  }
  
  /***Tick Function***/
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
  
  /***Functions***/
  public void setVerbose(Boolean verbose) {
    this.verbose = verbose;
  }
  
  public int numberOfFinishedJobs() {
    if(tick < 30) {
      //The case hasn't been run yet... return a null value.
      return -1;
    }
    
    return numberOfFinishedJobs;
  }
}