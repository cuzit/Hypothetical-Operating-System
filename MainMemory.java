/**************************************************
*MainMemory                                       *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This class is an array of MemoryModule objects   *
*It contains functions for setting and retrieving *
*information related to an individual memory      *
*position and the memory unit as a whole; further,*
*it also contains functions for adding and        *
*removing a Job to a particular memory location,  *
*as well as a few functions for calculating       *
*various things                                   *
***************************************************/

public class MainMemory {
  /***Variables***/
  MemoryModule[] Memory; //The virtual memory
  int size = 7; //Change this value to change the number of MemoryModules
  int[] sizeArray = {64, 96, 48, 32, 128, 96, 48}; //Add or remove entries
		  //from this array if you change the variable size.
  int memory = 512; //512MB Memory, in case this is needed
   int lastProcessed; //This variable is used to keep track of the last Job
		      //to be used in order to process execution in a round robin fashion
  
  /***Constructor***/
  public MainMemory() {
    size = 7;
    
    Memory = new MemoryModule[size];
    
    //Initialize the memory modules
    for(int i = 0; i < size; i++) {
      Memory[i] = new MemoryModule();
      
      Memory[i].setSegmentNumber(i);
      Memory[i].setSize(sizeArray[i]);
      Memory[i].setInUse(false);
      Memory[i].setWastedSpace(0);
    }
  }
  
  /***Functions***/
  //Assign a memory position
  public Boolean assignMemory(int segmentNumber, Job job) {
    if(job.getMemRequest() < Memory[segmentNumber].getSize()) {
      if(!Memory[segmentNumber].getInUse()) {
	Memory[segmentNumber].setMyJob(job);
	setInUse(segmentNumber, true);
	
	//Change the relevant values in the job
	setMemAssigned(segmentNumber, segmentNumber);
	setStatus(segmentNumber, "Ready");
	setTimeRemain(segmentNumber, getTimeRequest(segmentNumber));
	
	//Determine wasted space and set it
	setWastedSpace(segmentNumber, getSize(segmentNumber) - getMemRequest(segmentNumber));
	
	return true; //The assignment was successful.
      }
    }
    
    return false; //The assignment was not successful.
  }
  
  //Memory available
  public Boolean memoryAvailable() {
    //returns whether or not there is an empty memory space
    //true if there is, false if not
    for(int i = 0; i < size; i++) {
      if(Memory[i].getInUse() == false) {
	return true; //There is at least one available memory space
      }
    }
    
    return false; //There is no available memory spaces
  }
  
  //First available memory slot
  public int firstAvailableMemoryPos() {
    //returns the value in memory, starting with 0, of the first
    //block of empty memory.
    if(memoryAvailable()) {
      for(int i = 0; i < size; i++) {
	if(Memory[i].getInUse() == false) {
	  return i; //This is the first available memory slot
	}
      }
    }
    
    //There is no available memory
    return -1;
  }
  
  //Tick all jobs in memory
  public void tick() {
    //Tick, as per the instructions, executes 2 jobs per time slice. Further, it
    //does this in a round robin fashion. If you wish to change the number of
    //processors the HOS has (and therefore how many jobs can be executed per
    //time slice), change the following variable (processors).
    int processors = 2;
    Boolean hasTicked = false;
    int initialProcessed = lastProcessed;
    int numberProcessed = 0;
    
    while(!hasTicked) {
      if(lastProcessed == size) {
	lastProcessed = 0; //Reset to 0 if the end of the array has been reached
      }
      
      Boolean success = false;
      if(Memory[lastProcessed].getInUse()) {
	//If the memory is in use currently
	if(getStatus(lastProcessed) == "Ready") {
	  //If the Job is "Ready", set it to "Running"
	  setStatus(lastProcessed, "Running");
	  //This counts as an execution, so move on to the next.
	  success = true;
	}
	
	else if(getStatus(lastProcessed) == "Running") {
	  //If the memory is "Running," tick it.
	  setTimeRemain(lastProcessed, getTimeRemain(lastProcessed) - 1);
	  
	  //If TimeRemain is now < 0, then the job has finished execution
	  if(getTimeRemain(lastProcessed) < 0) {
	    //Set the relevant values
	    setTimeRemain(lastProcessed, 0); //Simply for appearances, to show 0 instead of -1.
	    setStatus(lastProcessed, "Finished");
	    
	    //Remove it from memory
	    removeMemory(lastProcessed);
	  }
	  
	  //Successfully executed a job, so move on to the next.
	  success = true;
	}
      }
      
      if(!success) {
	//If, for any of the above reasons, a Job was not ticked during this round
	//through the while loop
	lastProcessed++;
      }
      
      else if(success) {
	//A job was successfully executed.
	lastProcessed++;
	numberProcessed++;
      }
      
      //Has a job been executed per processor?
      if(numberProcessed >= processors) {
	hasTicked = true;
      }
      
      //Has the round robin circled through all jobs, but only executed 0 or 1?
      //Note: This occurrs if the Jobs in memory are, for some reason, ticked
      //despite having no Jobs, OR if there is fewer Jobs currently in memory
      //than exist number of processors to execute them all.
      else if(lastProcessed == initialProcessed) {
	hasTicked = true;
      }
    }
  }
  
  //Remove a job from a memory position
  public Boolean removeMemory(int segmentNumber) {
    Memory[segmentNumber].setInUse(false);
    
    //Reset the memory location
    Memory[segmentNumber] = new MemoryModule();
    Memory[segmentNumber].setSize(sizeArray[segmentNumber]);
    
    return true;
  }
  
  //Return the total amount of wasted memory
  public int totalWastedMemory() {
    int total = 0;
    for(int i = 0; i < size; i++) {
      if(Memory[i].getInUse()) {
	//This memory module is being occupied
	total += getWastedSpace(i);
      }
      else {
	//This memory module is not being occupied
	total += getSize(i);
      }
    }
    return total;
  }
  
  
  /***Setters***/
  //For Memory
  public void setSegmentNumber(int segmentNumber, int changedSegmentNumber) {
    Memory[segmentNumber].setSegmentNumber(changedSegmentNumber);
  }
  
  public void setSize(int segmentNumber, int size) {
    Memory[segmentNumber].setSize(size);
  }
  
  public void setInUse(int segmentNumber, Boolean inUse) {
    Memory[segmentNumber].setInUse(inUse);
  }
  
  public void setWastedSpace(int segmentNumber, int wastedSpace) {
    Memory[segmentNumber].setWastedSpace(wastedSpace);
  }
  
  //For Job
  public void setID(int segmentNumber, int id) {
    (Memory[segmentNumber].getMyJob()).setID(id);
  }
  
  public void setMemRequest(int segmentNumber, int memRequest) {
    (Memory[segmentNumber].getMyJob()).setMemRequest(memRequest);
  }
  
  public void setTimeRequest(int segmentNumber, int timeRequest) {
    (Memory[segmentNumber].getMyJob()).setTimeRequest(timeRequest);
  }
  
  public void setMemAssigned(int segmentNumber, int memAssigned) {
    (Memory[segmentNumber].getMyJob()).setMemAssigned(memAssigned);
  }
  
  public void setTimeRemain(int segmentNumber, int timeRemain) {
    (Memory[segmentNumber].getMyJob()).setTimeRemain(timeRemain);
  }
  
  public void setStatus(int segmentNumber, String status) {
    (Memory[segmentNumber].getMyJob()).setStatus(status);
  }
  
  /***Getters***/
  //For memory
  public int getSegmentNumber(int segmentNumber) {
    return Memory[segmentNumber].getSegmentNumber();
  }
  
  public int getSize(int segmentNumber) {
    return Memory[segmentNumber].getSize();
  }
  
  public Boolean getInUse(int segmentNumber) {
    return Memory[segmentNumber].getInUse();
  }
  
  public int getWastedSpace(int segmentNumber) {
    return Memory[segmentNumber].getWastedSpace();
  }
  
  //For Job
  public int getID(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getID();
  }
  
  public int getMemRequest(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getMemRequest();
  }
  
  public int getTimeRequest(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getTimeRequest();
  }
  
  public int getMemAssigned(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getMemAssigned();
  }
  
  public int getTimeRemain(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getTimeRemain();
  }
  
  public String getStatus(int segmentNumber) {
    return (Memory[segmentNumber].getMyJob()).getStatus();
  }
  
  
  /***toString***/
  public String toString() {
    String temp = "";
    for(int i = 0; i < Memory.length; i++) {
      temp += "Memory Position " + i;
      temp += "\n-----------------------------------\n";
      temp += "Segment Number: " + Memory[i].getSegmentNumber() + "\n";
      temp += "Size: " + Memory[i].getSize() + "\n";
      temp += "InUse: " + Memory[i].getInUse() + "\n";
      temp += "Wasted Space: " + Memory[i].getWastedSpace() + "\n";
      temp += "Job toString:\n";
      temp += "-------------------------------------------\n";
      temp += (Memory[i].getMyJob()).toString() + "\n";
      temp += "-------------------------------------------\n";
      if(i != Memory.length - 1) {
	temp += "-------------------------------------------\n";
      }
    }
    
    return temp;
  }
  
  /***Main***/
  public static void main(String[] args) {
    //Test driver
    System.out.println("Testing...");
    System.out.println("Test wrttien by Matt Silvey.");
    
    MainMemory test = new MainMemory();
    
    System.out.println(test.toString());
    
    Job myTestJob = new Job(3, 24, 5, 0, 5, "Ready");
    test.assignMemory(myTestJob.getMemAssigned(), myTestJob);
    
    System.out.println(test.toString());
    
    test.setTimeRemain(0, 4);
    System.out.println(test.toString());
  }
}