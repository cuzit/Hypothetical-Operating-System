public class JobGenerator {
  public Queue jobqueue;
  
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
      
      jobqueue.setID(i, i);
      jobqueue.setTimeRemain(i, timeRequest);
    }
  }
}