/**************************************************
*HOS                                              *
***************************************************
*Matt Silvey                                      *
*Dan Wang                                         *
***************************************************
*CS350                                            *
*Term Project                                     *
***************************************************
*This is the main class of the HOS; it calls all  *
*other classes and executes them continuously to  *
*simulate an operating system's memory management.*
***************************************************/

public class HOS {
  /***Variables***/
  int casetype;
  Boolean verbose;
  
  Queue jobs;
  MainMemory main;
  JobGenerator generator;
  
  /***Constructor***/
  public HOS(int casetype, Boolean verbose) {
    this.casetype = casetype;
    this.verbose = verbose;
    
    //Construct the hardware
    jobs = new Queue();
    generator = new JobGenerator(jobs);
    main = new MainMemory();
    if(verbose == true) {
      System.out.println(jobs.toString());
      //System.out.println(generator.toString());
      System.out.println(main.toString());
    }
    
    System.out.println("Constructor constructed.");
  }
  
  /***Start***/
  public void start() {
    if(casetype == 1) {
      //Operate casetype 1
      caseOne execute = new caseOne(jobs, main);
      execute.start();
    }
    
    /*else if(casetype == 2) {
      //Operate casetype 2
      caseTwo execute = new caseTwo(jobs, main);
      execute.start();
    }
    
    else if(casetype == 3) {
      //Operate casetype 3
      caseThree execute = new caseThree(jobs, main);
      execute.start();
    }
    
    else if(casetype == 4) {
      //Operate casetype 4
      caseOne executeOne = new caseOne(jobs, main);
      caseTwo executeTwo = new caseTwo(jobs, main);
      caseThree executeThree = new caseThree(jobs, main);
      
      executeOne.start();
      executeTwo.start();
      executeThree.start();
    }*/
    
    else {
      System.out.println("Something, somewhere, went wrong!");
    }
  }
  
  /***Functions***/
  
  /***toString***/
  public String toString() {
    return "";
  }
  
  /***Main***/
  public static void main(String[] args) {
    //arg1: Allowed values are 1, 2, 3, OR 4. This represents the three
    //	    different cases the HOS can execute. For example, 1 is
    //	    case 1, 2 is case 2, 3 is case 3. If you use 4, it will
    //	    perform all cases, with identical data.
    //arg2: Allowed value is v. This toggles the HOS to print
    //      everything verbosely.
    /***Variables***/
    int casetype;
    Boolean verbose;
    HOS operatingsystem;
    
    //Initialize important variables
    verbose = false; casetype = 1;
    
    if(args.length == 2) {
      //The user passed both an arg1 and an arg2
      casetype = Integer.parseInt(args[0]);
      char[] temp = args[1].toCharArray();
      if(temp[0] == 'v') {
	verbose = true;
      }
      
      else {
	verbose = false;
      }
      
      //Verify the casetype
      if(casetype != 1 && casetype != 2 && casetype != 3  && casetype != 4) {
	casetype = 1;
      }
      
      //Execute the HOS
      System.out.println("Executing the HOS with case " + casetype + ".");
    }
    
    else if(args.length == 1) {
      //The user only passed an arg1
      casetype = Integer.parseInt(args[0]);
      verbose = false;
      
      //Verify the casetype
      if(casetype != 1 && casetype != 2 && casetype != 3 && casetype != 4) {
	casetype = 1;
      }
      
      //Execute the HOS
      System.out.println("Executing the HOS with case " + casetype + ".");
    }
    
    else if(args.length == 0) {
      //The user passed no args, make the arg the default values
      System.out.println("NOTE: Arguments not specified. HOS will execute,");
      System.out.println("      assuming default values (case 1, quiet).");
      System.out.println("Please pass either one or two arguments to");
      System.out.println("to specify how the HOS should operate.");
      casetype = 1;
      verbose = false;
    }
    
    else {
      //The user passed more than 2 arguments, or something else
      //unexpected happened.
      System.out.println("This program must be executed with no more than");
      System.out.println("two arguments.");
      System.out.println("Arg1: The case to execute (case one, case two, or");
      System.out.println("      case three). See README for more information.");
      System.out.println("Arg2: (Optional) Use 'v' to make the HOS print");
      System.out.println("      its process verbosely.");
      System.out.println("Example:");
      System.out.println("java HOS 4 v");
      System.out.println("	-This will execute the HOS, all cases");
      System.out.println("	 consecutively, in a verbose manner.");
      System.out.println("java HOS 2 v");
      System.out.println("     -This will execute the HOS, case 2, verbose.");
      System.out.println("java HOS 3");
      System.out.println("     -This will execute the HOS, case 3, quiet.");
      System.out.println("java HOS");
      System.out.println("     -This will execute the HOS with default");
      System.out.println("       values - case 1, quiet.");
      casetype = -1;
    }
    
    //Execute the HOS
    if(casetype != -1) {
      operatingsystem =  new HOS(casetype, verbose);
      operatingsystem.start();
    }
  }
}