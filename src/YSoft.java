import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class YSoft {
    public static void main(String[] args) throws Exception {
        
        Accounting accounting = new Accounting();
        Company company=new Company();
        
        

        // Project project1 = new Project("FirstProject", 1, 2 , 2, 2, 3, 3);
        // company.addProject(project1);
        // Manager m1 = new Manager("manager1Name", "manager1Surname",accounting.calculateSalaryForFullTimeWorker());
        // company.hireEmployee(m1);
        // Designer d1= new Designer("designer1name", "designer1surname",accounting.calculateSalaryForFullTimeWorker());
        // company.hireEmployee(d1);
        // Designer d2= new Designer( "designer2name", "designer2surname", accounting.calculateSalaryForHalfTimeWorker());
        // Designer d3= new Designer( "d3name", "d3surname", accounting.calculateSalaryForFullTimeWorker());
        // Designer d4= new Designer( "d4name", "d4surname", accounting.calculateSalaryForFullTimeWorker());
        // Analyst a2= new Analyst( "a2name", "a2surname", accounting.calculateSalaryForHalfTimeWorker());
        // Project p2 = new Project("secondProject", 1, 1, 2, 2, 3, 4);
        // company.addProject(p2);
        // company.hireEmployee(a2);
        // company.hireEmployee(p3);
        // company.hireEmployee(d3);
        // company.hireEmployee(d4);
        // company.hireEmployee(d2);

        // Project pro1 = new Project("1Project", 1, 1, 2, 5, 5, 5);

        // company.addProject(pro1);

        // Manager m2 = new Manager("manager2Name", "manager2Surname",accounting.calculateSalaryForFullTimeWorker());
        // Programmer p1= new Programmer( "programmer1name", "porgrammer1surname", accounting.calculateSalaryForHalfTimeWorker());
        // Programmer p2= new Programmer( "programmer2name", "porgrammer2surname", accounting.calculateSalaryForHalfTimeWorker());

        // company.hireEmployee(p2);
        // company.hireEmployee(p1);
        // company.hireEmployee(m2);

        // company.activeTheProject(company.findprojectByIndex(1));
        // company.finishProject(company.findProjectByName("secondProject"));
  

        // Programmer p4= new Programmer( "programmer4name", "porgrammer4surname", accounting.calculateSalaryForHalfTimeWorker());
        // Programmer p5= new Programmer( "programmer5name", "porgrammer5surname", accounting.calculateSalaryForHalfTimeWorker());
        // Programmer p6= new Programmer( "programmer6name", "porgrammer6surname", accounting.calculateSalaryForHalfTimeWorker());
        
        // company.hireEmployee(p6);
        // Project p2 = new Project("2project", 1, 1, 1, 2, 2, 2);


        // company.addProject(p2);
        // Programmer p7= new Programmer( "programmer7name", "porgrammer7surname", accounting.calculateSalaryForHalfTimeWorker());
        // Programmer p8= new Programmer( "programmer8name", "porgrammer8surname", accounting.calculateSalaryForHalfTimeWorker());
        // company.hireEmployee(p7);
        // company.hireEmployee(p8);

        // Manager m3 = new Manager("manager2", "manager2surname", 0);
        // company.hireEmployee(m3);



        




        
 



   

    
    }
    public boolean makeProjectActive(String name) {
    	Company company = new Company();
    	Project project = company.findProjectByName(name);
    	if(project.getCanProjectActive()) {
    		project.activeProject();
    		return true;
    		
    	}
    	return false;
    }

   public  void addNewEmployee(String name,String surname,String empType,String workTimeType){
    	Company company= new Company();
        double salary;
        Accounting accounting=new Accounting();
        if(workTimeType.equals("HalfTime")){
            salary=accounting.calculateSalaryForHalfTimeWorker();
        }
        else{
            salary=accounting.calculateSalaryForFullTimeWorker();
        }
        if(empType.equals("Analyst")){
            Analyst newA = new Analyst(name, surname, salary);
            company.hireEmployee(newA);

        }
        else if(empType.equals("Designer")){
            Designer newD = new Designer(name, surname, salary);
            company.hireEmployee(newD);

        }
        else if(empType.equals("Programmer")){
            Programmer newP = new Programmer(name, surname, salary);
            company.hireEmployee(newP);
        }
        else{
            //manager
            Manager newM = new Manager(name, surname, salary);
            company.hireEmployee(newM);
        }
    }
    void addNewProject(String name,int minAnalyst,int minDesigner,int minProgrammer,int maxAnalyst,int maxDesigner,int maxProgrammer,Company company){
        Project newP = new Project(name, minAnalyst, minDesigner, minProgrammer, maxAnalyst, maxDesigner, maxProgrammer);
        company.addProject(newP);

    }
}
    



