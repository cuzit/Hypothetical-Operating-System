/**************************************************
*MemoryModule                                     *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This class represents an indivual location in    *
*memory; it contains functions for retrieving and *
*setting information relevant to this location in *
*memory                                           *
*It has the following components:                 *
*	Segment Number                            *
*	Size in kilobytes                         *
*	Whether the segment is in use or not      *
*	Wasted space                              *
***************************************************/

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
  
  /***Getters***/
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
  
  /***Setters***/
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