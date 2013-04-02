public class MainMemory {
  /***Classes***/
  public class MemoryModule {
    /***Variables***/
    int segmentNumber;
    int size; //in kilobytes
    Boolean inUse;
    int wastedSpace;
    Job myJob;
    
    /***Constructor***/
    public MemoryModule() {
      segmentNumber = -1;
      size = -1;
      inUse = false;
      wastedSpace = -1;
      myJob = new Job();
    }
    
    public MemoryModule(int segmentNumber, int size, Boolean inUse, int wastedSpace, Job myJob) {
      this.segmentNumber = segmentNumber;
      this.size = size;
      this.inUse = inUse;
      this.wastedSpace = wastedSpace;
      this.myJob = myJob;
    }
    
    /***Functions***/
    //Getters
    public int getSegmentNumber() {
      return segmentNumber;
    }
    
    public int getSize() {
      return size;
    }
    
    public Boolean getInUse() {
      return inUse;
    }
    
    public int getWastedSpace() {
      return wastedSpace;
    }
    
    public Job getMyJob() {
      return myJob;
    }
    
    //Setters
    public void setSegmentNumber(int segmentNumber) {
      this.segmentNumber = segmentNumber;
    }
    
    public void setSize(int size) {
      this.size = size;
    }
    
    public void setInUse(Boolean inUse) {
      this.inUse = inUse;
    }
    
    public void setWastedSpace(int wastedSpace) {
      this.wastedSpace = wastedSpace;
    }
    
    public void setMyJob(Job myJob) {
      this.myJob = myJob;
    }
  }
  
  /***Variables***/
  MemoryModule[] Memory; //The virtual memory
  int size = 7; //Change this value to change the number of MemoryModules
  int[] sizeArray = {64, 96, 48, 32, 128, 96, 48}; //Add or remove entries
		  //from this array if you change the variable size.
  int memory = 512; //512MB Memory, in case this is needed
  
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
    for(int i = 0; i < size; i++) {
      //If the memory is in use
      if(Memory[i].getInUse()) {
	//If the memory is "Ready", set it to "Running"
	if(getStatus(i) == "Ready") {
	  //Job is "Ready" to begin executing
	  setStatus(i, "Running");
	}
	
	//If the memory is "Running" (which it now should be if it was "Ready", tick it
	if(getStatus(i) == "Running") {
	  setTimeRemain(i, getTimeRemain(i) - 1);
	  
	  //If TimeRemain is now < 0, then the job has finished execution.
	  if(getTimeRemain(i) < 0) {
	    //Set the relevant values
	    setTimeRemain(i, 0); //Simply for appearances, to show 0 instead of -1.
	    setStatus(i, "Finished");
	    
	    //Remove it from memory
	    removeMemory(i);
	  }
	}
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
  
  //Setters
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
  
  //Getters
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