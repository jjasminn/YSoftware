import java.util.ArrayList;
import java.util.Comparator;

public class Company {
    private ArrayList<Employee> employees;
    private ArrayList<Project> projects;
    Service service;

     Company(){//degisecek
        service= new Service();
        employees=service.getAllEmployees();
        projects = service.getAllProjects(employees);
        System.out.println("olustu");
    
    
    }


    

    public void hireEmployee(Employee employee) {//duzenlenecek  maas kismi
        if(assignProject(employee)){
            employees.add(employee);
            System.out.println("Calisan Eklendi");
        }else{
            System.out.println("Girebilecegi musait proje olmadigindan calisan eklenemedi");
        }     
    }

    private boolean assignProject(Employee employee) {
        if(emergencyHire(employee)){
            return true;
        }
        else{
            for (Project project : projects) {
                if (project.canAddEmployee(employee)) {
                    project.addEmployeetoProject(employee);
                    return true;            
                }
            }
        }
        return false;
    }
    public void finishProject(Project project){
        projects.remove(project);
        if(project.doesProjectHasManager()){
            if(!assignProject(project.getManagerOfTheProject())){
                service.deleteManagerFromProject(project);
                System.out.println(Accounting.terminationPay(project.getManagerOfTheProject().getHireDate(), project.getManagerOfTheProject().getSalary()));
            
                service.deleteEmployeeFromDatabase(project.getManagerOfTheProject().getIdentificationNumber());
                employees.remove(project.getManagerOfTheProject());
            }
        }
        for (Employee employee : project.getEmployees()) {
           if( employee instanceof Manager){
            Manager emp=(Manager)employee;
            if(!assignProject(emp)){
            	System.out.println(Accounting.terminationPay(emp.getHireDate(), emp.getSalary()));
                service.deleteManagerFromProject(project);
                service.deleteEmployeeFromDatabase(emp.getIdentificationNumber());
                
                employees.remove(emp);
            }
           }
           else if(employee instanceof Designer){
            Designer emp =(Designer) employee;
            if(!assignProject(emp)){
                service.deleteEmployeeFromDatabase(emp.getIdentificationNumber());
                //isten cikarilma
                System.out.println(Accounting.terminationPay(emp.getHireDate(), emp.getSalary()));
                employees.remove(emp);
            }
           }
           else if(employee instanceof Programmer){
            Programmer emp = (Programmer)employee;
            if(!assignProject(emp)){
                service.deleteEmployeeFromDatabase(emp.getIdentificationNumber());
                employees.remove(emp);
                System.out.println(Accounting.terminationPay(emp.getHireDate(), emp.getSalary()));
                
               
               
            }
           }
           else{
            Analyst emp = (Analyst) employee;
            if(!assignProject(emp)){
                //isten cikarilma
                service.deleteEmployeeFromDatabase(emp.getIdentificationNumber());
                employees.remove(emp);
                System.out.println(Accounting.terminationPay(emp.getHireDate(), emp.getSalary()));
            }
          
           }
           
            
           
        }
        service.deleteProjectFromDatabase(project);
        //servisten projeyi sill
    }
    public void activeTheProject(Project project){
        if(project.getCanProjectActive()){
            project.activeProject();
            service.setProjectActive(project);

        }
        else{
            System.out.println("Projec cannot be active");
        }
       

    }
    public Project findprojectByIndex(int index){
        return projects.get(index);

    }
    public Project findProjectByName(String name){
        for (Project p : projects) {
            if(p.getProjectName().equals(name)){
                return p;
            }
        }
        return null;
    }
    private boolean emergencyHire(Employee e){
        
        for (Project project : projects) {
            if ((e instanceof Designer && project.getMinNumberOfDesigner()>project.getNumberOfDesigner())||
            (e instanceof Analyst && project.getMinNumberOfAnalyst()>project.getNumberOfAnalyst())||
            (e instanceof Programmer && project.getMinNumberOfProgrammer()>project.getNumberOfProgrammer())||
            e instanceof Manager && !project.doesProjectHasManager()) {
                if(e instanceof Designer){
                    Designer d = (Designer) e;                
                    project.addEmployeetoProject(d);                    
                }  
                else if(e instanceof Analyst){
                    Analyst a = (Analyst) e;                  
                    project.addEmployeetoProject(a);                   
                }
                else if(e instanceof Programmer){
                    Programmer p = (Programmer) e;             
                    project.addEmployeetoProject(p);
                }
                else{
                    Manager m = (Manager) e;
                    project.addEmployeetoProject(m);                  
                }
                return true;
            }
        }
        return false;

    }
    public void addProject(Project project) {
        projects.add(project);
       
        service.addProjectToDatabase(project);
    }//eger proje baslamamissa az gorunuyor


}
