import java.time.LocalDate;
import java.util.UUID;

public class Programmer extends Employee{
    private String identificationNumber;
    private String name;
    private String surname;
    private double salary;
    private String projectID;
    private LocalDate hireDate;

    public Programmer( String name, String surname, double salary) {
        this.identificationNumber = UUID.randomUUID().toString().substring(0, 20);
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        hireDate=LocalDate.now();

    }
    public Programmer(String identificationNumber, String name, String surname, double salary,String projectId, LocalDate hirDate){//veritabanindan cekerken alinacak olan yapici
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.hireDate=hireDate;
        this.projectID=projectId;
    }

    // Worker arayüzünden uygulanması gereken yöntemlerin implementasyonu
 
    public String getIdentificationNumber() {
        return identificationNumber;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public double getSalary() {
        return salary;
    }


    public String getProjectID() {
        return projectID;
    }
    public void setProject(String projectId){
        this.projectID = projectId;
     }
     public LocalDate getHireDate(){
        return this.hireDate;

     }
    
    
}
