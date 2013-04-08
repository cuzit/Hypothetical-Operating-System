/**************************************************
*JobGenerator                                     *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This class generates the data randomly for all   *
*Jobs in the given queue, and also assigns values *
*to their defaults - i.e., the ID number.         *
***************************************************/

public class JobGenerator {
  /***Variables***/
  public Queue jobqueue;
  
  /***Constructor/Execution***/
  public JobGenerator(Queue jobqueue) {
    this.jobqueue = jobqueue;

    for(int i = 0; i < 20; i++) {
      //Generate what memrequest should be
      int memRequest = 0;
      while(memRequest < 24 || memRequest > 100) {
	memRequest = (int)(Math.random()*100+24);
      }
      jobqueue.setMemRequest(i, memRequest);

      //Generate time request
      int timeRequest = 0;
      while(timeRequest < 2 || timeRequest > 10) {
	timeRequest = (int)(Math.random()*10+2);
      }
      jobqueue.setTimeRequest(i, timeRequest);
      
      //Set other variables for this Job to their defaults.
      jobqueue.setID(i, i);
      jobqueue.setTimeRemain(i, timeRequest);
    }
  }
}