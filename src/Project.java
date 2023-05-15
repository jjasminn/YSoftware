

import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public class Project {

    private String projectID;
    private String projectName;
    private LocalDate startDate;
    private LocalDate finishDate;
    private boolean canProjectActive;
    private boolean isProjectActive;
    private ArrayList<Employee> employees;
    private Manager managerOfTheProject;
    private int numberOfAnalyst;
    private int numberOfDesigner;
    private int numberOfProgrammer;
    private int minNumberOfAnalyst;
    private int minNumberOfDesigner;
    private int minNumberOfProgrammer;
    private int maxNumberOfAnalyst;
    private int maxNumberOfDesigner;
    private int maxNumberOfProgrammer;
    Service service= new Service();

    public Project(String projectName, 
    int minNumberOfAnalyst, int minNumberOfDesigner, int minNumberOfProgrammer, int maxNumberOfAnalyst,
    int maxNumberOfDesigner, int maxNumberOfProgrammer){
        this.projectID = UUID.randomUUID().toString().substring(0, 20);
        this.projectName = projectName;
        this.minNumberOfAnalyst = minNumberOfAnalyst;
        this.minNumberOfDesigner = minNumberOfDesigner;
        this.minNumberOfProgrammer = minNumberOfProgrammer;
        this.maxNumberOfAnalyst = maxNumberOfAnalyst;
        this.maxNumberOfDesigner = maxNumberOfDesigner;
        this.maxNumberOfProgrammer = maxNumberOfProgrammer;
        this.employees= new ArrayList<>();//hic calisan eklemeden de proje baslatabilmek icin
        this.numberOfAnalyst=0;
        this.numberOfDesigner=0;
        this.numberOfProgrammer=0;
        this.canProjectActive=false;
        this.isProjectActive=false;
    }
    public void activeProject(){//proje ancak disardan aktif edilebilir
        isProjectActive=true;
        this.startDate=LocalDate.now();
        service.setProjectActive(this);
    }


    public Project(String projectID, String projectName, LocalDate startDate, LocalDate finishDate,//veritabanindan olusturulurken idsi ve icindeki employeelar belli olacagindan bu constructor calistirilacak
          Boolean  isProjectActive, ArrayList<Employee> employees, int numberOfAnalyst, int numberOfDesigner, int numberOfProgrammer,
            int minNumberOfAnalyst, int minNumberOfDesigner, int minNumberOfProgrammer, int maxNumberOfAnalyst,
            int maxNumberOfDesigner, int maxNumberOfProgrammer,Manager manager) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.isProjectActive=isProjectActive;
        this.employees = employees;
        this.numberOfAnalyst = numberOfAnalyst;
        this.numberOfDesigner = numberOfDesigner;
        this.numberOfProgrammer = numberOfProgrammer;
        this.minNumberOfAnalyst = minNumberOfAnalyst;
        this.minNumberOfDesigner = minNumberOfDesigner;
        this.minNumberOfProgrammer = minNumberOfProgrammer;
        this.maxNumberOfAnalyst = maxNumberOfAnalyst;
        this.maxNumberOfDesigner = maxNumberOfDesigner;
        this.maxNumberOfProgrammer = maxNumberOfProgrammer;
        this.managerOfTheProject=manager;
        this.canProjectActive=canProjectActiveFunc();
    }
    

    public boolean canProjectActiveFunc(){
        if(managerOfTheProject!=null&& employees.stream().filter(e -> e instanceof Analyst).count() >= maxNumberOfAnalyst && employees.stream().filter(e -> e instanceof Designer).count() >= maxNumberOfDesigner && employees.stream().filter(e -> e instanceof Programmer).count() >= maxNumberOfProgrammer){
                return true;
        }
        return false;
    }
    public boolean doesProjectHasManager(){
        if(managerOfTheProject==null){
            return false;
        }
        return true;
    }

    public boolean canAddEmployee(Employee emp) {

        if (emp instanceof Manager && this.managerOfTheProject!=null) {
     
            return false;
        }
        else if (emp instanceof Analyst && this.employees.stream().filter(e -> e instanceof Analyst).count() >= maxNumberOfAnalyst) {
            //System.out.println("Bu projede en fazla " + numberOfAnalyst + " analist çalışabilir.");
            return false;
        }
        else if (emp instanceof Designer && this.employees.stream().filter(e -> e instanceof Designer).count() >= maxNumberOfDesigner) {
           // System.out.println("Bu projede en fazla " + numberOfDesigner + " tasarımcı çalışabilir.");
            return false;
        }
        else if (emp instanceof Programmer && this.employees.stream().filter(e -> e instanceof Programmer).count() >= maxNumberOfProgrammer) {
           // System.out.println("Bu projede en fazla " + numberOfProgrammer + " programcı çalışabilir.");
            return false;
        }
        else {
            return true;
        }
    }
    
    public void addEmployeetoProject(Employee e){
        if(e instanceof Manager){
      
            Manager p =(Manager) e;
            if(p.getProjectID()==null){
                p.setProject(this.projectID);
                this.managerOfTheProject=p;
                service.AddEmployeetoDatabase(p);

            }
            else{
                p.setProject(this.projectID);
                this.managerOfTheProject=p;
                service.setAnotherProjectToEmployee(p, this);

            }
            
            
        }
        else{
            employees.add(e);
            if(e instanceof Programmer){
                numberOfProgrammer+=1;
                Programmer p =(Programmer) e;
                if(p.getProjectID()==null){//ilk projesi
                    p.setProject(this.projectID);
                    service.AddEmployeetoDatabase(p);
                }
                else{
                    p.setProject(this.projectID);
                    service.setAnotherProjectToEmployee(p, this);

                }

            }
            else if (e instanceof Designer){
                numberOfDesigner+=1;
                Designer p =(Designer) e;
                if(p.getProjectID()==null){//ilk projesi
                    p.setProject(this.projectID);
                    service.AddEmployeetoDatabase(p);
                }
                else{
                    p.setProject(this.projectID);
                    service.setAnotherProjectToEmployee(p, this);

                }
            }
            else if(e instanceof Analyst){
                numberOfAnalyst+=1;
                Analyst p =(Analyst) e;
                if(p.getProjectID()==null){//ilk projesi
                    p.setProject(this.projectID);
                    service.AddEmployeetoDatabase(p);
                }
                else{
                    p.setProject(this.projectID);
                    service.setAnotherProjectToEmployee(p, this);

                }
               
            }
        }
        
        this.canProjectActive=canProjectActiveFunc();
    }
  
    public Manager getManagerOfTheProject() {
        return managerOfTheProject;
    }

 
    public String getProjectID() {
        return projectID;
    }
    public void deleteEmployee(Employee e){
        employees.remove(e);
    }


  


    public String getProjectName() {
        return projectName;
    }
    public boolean getIsActive() {
        return isProjectActive;
    }





    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getFinishDate() {
        return finishDate;
    }
    // public int getHowMuchDayForProject() {
    //     if(startDate==null &&finishDate==null){
    //         return -1;
    //     }

    //     Duration duration = Duration.between(startDate, finishDate); //projeye bbitmis
    //     long days = duration.toDays(); 
    //     return  (int) days; // Günlük fark
    // }






    public ArrayList<Employee> getEmployees() {
        return employees;
    }


   


    public int getNumberOfAnalyst() {
        return numberOfAnalyst;
    }
    public boolean getCanProjectActive() {
        return canProjectActive;
    }

    


    public int getNumberOfDesigner() {
        return numberOfDesigner;
    }


   


    public int getNumberOfProgrammer() {
        return numberOfProgrammer;
    }


    


    public int getMinNumberOfAnalyst() {
        return minNumberOfAnalyst;
    }


  


    public int getMinNumberOfDesigner() {
        return minNumberOfDesigner;
    }





    public int getMinNumberOfProgrammer() {
        return minNumberOfProgrammer;
    }


 

    public int getMaxNumberOfAnalyst() {
        return maxNumberOfAnalyst;
    }




    public int getMaxNumberOfDesigner() {
        return maxNumberOfDesigner;
    }


  


    public int getMaxNumberOfProgrammer() {
        return maxNumberOfProgrammer;
    }
  


  



    }