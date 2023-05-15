import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

class Service{
    Connection conn;
    Statement s ;
    Statement s2;
    public Service(){
        try {
            conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/YSoft", "jasmin", "postgresql");
            s = conn.createStatement();
            s2=conn.createStatement();
        } catch (Exception e) {
           System.out.println("Veritabanina Baglanmada Problem "+ e.getMessage());
        }

    };
    public ArrayList<Employee> getAllEmployees(){
        String name,surname,id,project_id,employee_type;
        int salary;
        LocalDate hireDate;
        ArrayList<Employee> list=new ArrayList<Employee>();
        try {
            String query= "SELECT * FROM employee";
            ResultSet r = s.executeQuery(query);
             while(r.next()){
                id=r.getString(1);
                name=r.getString(2);
                surname=r.getString(3);
                salary=r.getInt(4);
                project_id=r.getString(5);
                hireDate= r.getTimestamp(6).toLocalDateTime().toLocalDate();
                employee_type=r.getString(7);
                if(employee_type.equals("Designer")){
                    list.add(new Designer(id, name, surname, salary, project_id, hireDate) );
                }
                else if(employee_type.equals("Analyst")){
                    list.add(new Analyst(id, name, surname, salary, project_id, hireDate) );

                }
                else if(employee_type.equals("Programmer")){
                    list.add(new Programmer(id, name, surname, salary, project_id, hireDate) );
                }
                else{
                    list.add(new Manager(id, name, surname, salary, project_id, hireDate) );
                }

             }
             return list;

        
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }
    public ArrayList<Project> getAllProjects(ArrayList<Employee> allEmployeees){
        String name,id,manager_id;
        int numberOfAnalyst;
        int numberOfDesigner;
        int numberOfProgrammer;
        int minNumberOfAnalyst;
        int minNumberOfDesigner;
        int minNumberOfProgrammer;
        int maxNumberOfAnalyst;
        int maxNumberOfDesigner;
        int maxNumberOfProgrammer;
        boolean isActive;
        LocalDate startDate,finishDate;
        ArrayList<Project> project_list=new ArrayList<Project>();
        ArrayList<Employee> employee_list;
        try {
            String queryProjects= "SELECT * FROM project";
            String queryWorksOn = "SELECT * FROM works_on";
            
            ResultSet projectsData = s.executeQuery(queryProjects);
     
            
             while(projectsData.next()){
                employee_list = new ArrayList<>();
                id=projectsData.getString(1);
                name=projectsData.getString(2);
                if(projectsData.getTimestamp(3)==null){
                    startDate=null;
                }
                else{
                    startDate= projectsData.getTimestamp(3).toLocalDateTime().toLocalDate();
                }
                if(projectsData.getTimestamp(4)==null){
                    finishDate=null;
                }
                else{
                    finishDate= projectsData.getTimestamp(4).toLocalDateTime().toLocalDate();
                }

                isActive =projectsData.getBoolean(5);
                manager_id=projectsData.getString(6);
                numberOfAnalyst=projectsData.getInt(7);
                numberOfDesigner=projectsData.getInt(8);
                numberOfProgrammer=projectsData.getInt(9);
                minNumberOfAnalyst=projectsData.getInt(10);
                minNumberOfDesigner=projectsData.getInt(11);
                minNumberOfProgrammer=projectsData.getInt(12);
                maxNumberOfAnalyst=projectsData.getInt(13);
                maxNumberOfDesigner=projectsData.getInt(14);
                maxNumberOfProgrammer=projectsData.getInt(15);
                Manager manager=null;
                if(manager_id==null){
                    manager=null;
                }
                else{
                    for (Employee employee : allEmployeees) {
                        if(employee instanceof Manager){
                            Manager m = (Manager) employee;
                            if(m.getIdentificationNumber().equals(manager_id)){
                                manager=m;
                                break;   
                             }
                        }
                    }

                }
                ResultSet works_onData=s2.executeQuery(queryWorksOn);
                while (works_onData.next()) {
                    String employeeid = works_onData.getString("identification_id");
                    String projectId = works_onData.getString("project_id");
                    
                    // projectId'ler eşleştiğinde ilgili projenin çalışan listesine ekle

                    for (Employee employee : allEmployeees) {
                        if(employee instanceof Designer){
                            Designer d = (Designer) employee;
                            if(id.equals(projectId)&&d.getIdentificationNumber().equals(employeeid)){
                                employee_list.add(d);
                            }
                        }
                        else if(employee instanceof Analyst){
                            Analyst d = (Analyst) employee;
                            if(id.equals(projectId)&&d.getIdentificationNumber().equals(employeeid)){
                                employee_list.add(d);
                            }
                        }
                        else if(employee instanceof Programmer){
                            Programmer d = (Programmer) employee;
                            if(id.equals(projectId)&&d.getIdentificationNumber().equals(employeeid)){
                                employee_list.add(d);
                            }
                        }
                    }
                }
                
                

                project_list.add(new Project(id, name, startDate, finishDate, isActive, employee_list, numberOfAnalyst, numberOfDesigner, numberOfProgrammer, minNumberOfAnalyst, minNumberOfDesigner, minNumberOfProgrammer, maxNumberOfAnalyst, maxNumberOfDesigner, maxNumberOfProgrammer, manager));


                
               
                

             }
             return project_list;

        
        } catch (Exception e) {
            System.out.println("projeleri alirken hata "+e.getMessage());
        }
        return null;

    }
    public void addProjectToDatabase(Project project){
        String insertQuery = "INSERT INTO project VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Assuming you have the necessary values for the new project
        String id = project.getProjectID();
        String name = project.getProjectName();
        Timestamp startDate;
        Timestamp finishDate;
        if(project.getStartDate()==null){
            startDate=null;
        }
        else{
            startDate= Timestamp.valueOf(project.getStartDate().atStartOfDay());
        }
        if(project.getFinishDate()==null){
            finishDate=null;
        }
        else{
            finishDate=Timestamp.valueOf(project.getFinishDate().atStartOfDay());
        }

    boolean isActive = project.getIsActive();
    String manager_id;
    if(project.getManagerOfTheProject()==null){
        manager_id = null;
    }
    else{
        manager_id = project.getManagerOfTheProject().getIdentificationNumber();
    }
    
    int numberOfAnalyst = project.getNumberOfAnalyst();
    int numberOfDesigner = project.getNumberOfAnalyst();
    int numberOfProgrammer = project.getNumberOfProgrammer();
    int minNumberOfAnalyst = project.getMinNumberOfAnalyst();
    int minNumberOfDesigner = project.getMinNumberOfDesigner();
    int minNumberOfProgrammer = project.getMinNumberOfProgrammer();
    int maxNumberOfAnalyst = project.getMaxNumberOfAnalyst();
    int maxNumberOfDesigner = project.getMaxNumberOfDesigner();
    int maxNumberOfProgrammer = project.getMaxNumberOfProgrammer();
    try {
        PreparedStatement statement = conn.prepareStatement(insertQuery);
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setTimestamp(3, startDate);
        statement.setTimestamp(4, finishDate);
        statement.setBoolean(5, isActive);
        statement.setString(6, manager_id);
        statement.setInt(7, numberOfAnalyst);
        statement.setInt(8, numberOfDesigner);
        statement.setInt(9, numberOfProgrammer);
        statement.setInt(10, minNumberOfAnalyst);
        statement.setInt(11, minNumberOfDesigner);
        statement.setInt(12, minNumberOfProgrammer);
        statement.setInt(13, maxNumberOfAnalyst);
        statement.setInt(14, maxNumberOfDesigner);
        statement.setInt(15, maxNumberOfProgrammer);
    
        int rowsAffected = statement.executeUpdate();
        
    
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
    

    }

   public void AddEmployeetoDatabase(Employee employee){
    String query = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?)";
    String queryaddWorkson = "INSERT INTO works_on VALUES (?, ?)";
        if(employee instanceof Designer){
            Designer d=(Designer)employee;
            try {       

       
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, d.getIdentificationNumber());
                statement.setString(2, d.getName());
                statement.setString(3, d.getSurname());
                statement.setDouble(4, d.getSalary());
                statement.setString(5, d.getProjectID());
                statement.setTimestamp(6, Timestamp.valueOf(d.getHireDate().atStartOfDay()));
                statement.setString(7, "Designer");
                statement.executeUpdate();
                statement.close();
                try (PreparedStatement preparedStatement = conn.prepareStatement(queryaddWorkson)) {
                    preparedStatement.setString(1, d.getIdentificationNumber());
                    preparedStatement.setString(2, d.getProjectID());
                    
                    preparedStatement.executeUpdate();
                    setProjectsNumberOfEmployees("Designer", employee);
                } catch (SQLException e) {
                    System.out.println("worksona eklerken hata "+ e.getMessage());
                  
                }
            } catch (Exception e) {
               System.out.println("employee eklerken hata"+e.getMessage());
                    
            }           
        }
        else if(employee instanceof Manager){
            Manager m = (Manager) employee;
           
            try {       

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, m.getIdentificationNumber());
                statement.setString(2, m.getName());
                statement.setString(3, m.getSurname());
                statement.setDouble(4, m.getSalary());
                statement.setString(5, m.getProjectID());
                statement.setTimestamp(6, Timestamp.valueOf(m.getHireDate().atStartOfDay()));
                statement.setString(7, "Manager");
                statement.executeUpdate();
                statement.close();
                try (PreparedStatement preparedStatement = conn.prepareStatement(queryaddWorkson)) {
                    preparedStatement.setString(1, m.getIdentificationNumber());
                    preparedStatement.setString(2, m.getProjectID());
                    
                    preparedStatement.executeUpdate();
                    setManagerOfTheProjectToDatabase(m);

                } catch (SQLException e) {
                    System.out.println("worksona eklerken hata "+ e.getMessage());
               
                    // Hata durumunda uygun şekilde işleyin
                }
                } catch (Exception e) {
                    System.out.println("employee eklerken hata"+e.getMessage());
                    
                }
                

                 
        }
        else if(employee instanceof Programmer){
            Programmer p= (Programmer) employee;
            try {       
               
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, p.getIdentificationNumber());
                statement.setString(2, p.getName());
                statement.setString(3, p.getSurname());
                statement.setDouble(4, p.getSalary());
                statement.setString(5, p.getProjectID());
                statement.setTimestamp(6, Timestamp.valueOf(p.getHireDate().atStartOfDay()));
                statement.setString(7, "Programmer");
                statement.executeUpdate();
                statement.close();
                try (PreparedStatement preparedStatement = conn.prepareStatement(queryaddWorkson)) {
                    preparedStatement.setString(1, p.getIdentificationNumber());
                    preparedStatement.setString(2, p.getProjectID());
                    
                    preparedStatement.executeUpdate();
                    setProjectsNumberOfEmployees("Programmer", employee);
                } catch (SQLException e) {
                    System.out.println("worksona eklerken hata "+ e.getMessage());
               
                    // Hata durumunda uygun şekilde işleyin
                }
                } catch (Exception e) {
                    System.out.println("employee eklerken hata"+e.getMessage());
                    
                }   

        }
        else{
            Analyst a = (Analyst) employee;
            try {       
               
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, a.getIdentificationNumber());
                statement.setString(2, a.getName());
                statement.setString(3, a.getSurname());
                statement.setDouble(4, a.getSalary());
                statement.setString(5, a.getProjectID());
                statement.setTimestamp(6, Timestamp.valueOf(a.getHireDate().atStartOfDay()));
                statement.setString(7, "Analyst");
                statement.executeUpdate();
                statement.close();
                try (PreparedStatement preparedStatement = conn.prepareStatement(queryaddWorkson)) {
                    preparedStatement.setString(1, a.getIdentificationNumber());
                    preparedStatement.setString(2, a.getProjectID());
                    
                    preparedStatement.executeUpdate();
                    setProjectsNumberOfEmployees("Analyst", employee);
                } catch (SQLException e) {
                    System.out.println("worksona eklerken hata "+ e.getMessage());
        
                    // Hata durumunda uygun şekilde işleyin
                }
                } catch (Exception e) {
                    System.out.println("employee eklerken hata"+e.getMessage());
                    
                }    
            }
    }

    public void setProjectActive(Project project){
        String query = "UPDATE project SET is_project_active = ? WHERE project_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setBoolean(1, project.getIsActive());
            preparedStatement.setString(2, project.getProjectID());
            preparedStatement.executeUpdate();
        
        } catch (Exception e) {
            System.out.println("hata olustu "+e.getMessage());
        }


    }
    public void setAnotherProjectToEmployee(Employee e,Project project){
        String query = "UPDATE employee SET project_id = ? WHERE identification_number = ?";
        String query2 = "UPDATE works_on SET project_id = ? WHERE identification_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, project.getProjectID());

            if(e instanceof Analyst){
                Analyst a = (Analyst)e;
                preparedStatement.setString(2, a.getIdentificationNumber());
                setProjectsNumberOfEmployees("Analyst", a);
                try (PreparedStatement preparedStatement2 = conn.prepareStatement(query2)) {
                    preparedStatement2.setString(1, project.getProjectID());
                    preparedStatement2.setString(2, a.getIdentificationNumber());
                
                    int rowsAffected = preparedStatement2.executeUpdate();
                
                    System.out.println("Güncellenen satır sayısı: " + rowsAffected);
                } catch (SQLException exxcc) {
                    System.out.println("hata works_onda employee silmeye calisirken " +exxcc.getMessage());
                }
                
            }

            else if(e instanceof Programmer){
                Programmer p = (Programmer)e;
                preparedStatement.setString(2, p.getIdentificationNumber());
                setProjectsNumberOfEmployees("Programmer", p);
                try (PreparedStatement preparedStatement2 = conn.prepareStatement(query2)) {
                    preparedStatement2.setString(1, project.getProjectID());
                    preparedStatement2.setString(2, p.getIdentificationNumber());
                
                    int rowsAffected = preparedStatement2.executeUpdate();
                
                    System.out.println("Güncellenen satır sayısı: " + rowsAffected);
                } catch (SQLException exxcc) {
                    System.out.println("hata works_onda employee silmeye calisirken " +exxcc.getMessage());
                }
            }
            else if(e instanceof Designer){
                Designer d = (Designer )e;
                preparedStatement.setString(2, d.getIdentificationNumber());
                setProjectsNumberOfEmployees("Designer", d);
                try (PreparedStatement preparedStatement2 = conn.prepareStatement(query2)) {
                    preparedStatement2.setString(1, project.getProjectID());
                    preparedStatement2.setString(2, d.getIdentificationNumber());
                
                    int rowsAffected = preparedStatement2.executeUpdate();
                
                    System.out.println("Güncellenen satır sayısı: " + rowsAffected);
                } catch (SQLException exxcc) {
                    System.out.println("hata works_onda employee silmeye calisirken " +exxcc.getMessage());
                }
            }
            else{
                //manager
                Manager m = (Manager ) e;
                preparedStatement.setString(2, m.getIdentificationNumber());
                setManagerOfTheProjectToDatabase(m);
                try (PreparedStatement preparedStatement2 = conn.prepareStatement(query2)) {
                    preparedStatement2.setString(1, project.getProjectID());
                    preparedStatement2.setString(2, m.getIdentificationNumber());
                
                    int rowsAffected = preparedStatement2.executeUpdate();
                
                    System.out.println("Güncellenen satır sayısı: " + rowsAffected);
                } catch (SQLException exxcc) {
                    System.out.println("hata works_onda employee silmeye calisirken " +exxcc.getMessage());
                }
            }

            preparedStatement.executeUpdate();
        
        } catch (Exception exc) {
            System.out.println("hata olustu "+exc.getMessage());
        }


    }
    private void setProjectsNumberOfEmployees(String empType,Employee e){
        String query = "SELECT * FROM project WHERE project_id = ?";
        if(empType.equals("Designer")){
            
            Designer d= (Designer) e;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, d.getProjectID());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {           
              
                int numberOfDesignerfromDatabase= resultSet.getInt("number_of_designer");

                String query3 = "UPDATE project SET number_of_designer = ? WHERE project_id = ?";
                    try (PreparedStatement preparedStatement2 = conn.prepareStatement(query3)) {
                          preparedStatement2.setInt(1, numberOfDesignerfromDatabase+1);
                          preparedStatement2.setString(2, d.getProjectID());
              
                          int rowsAffected = preparedStatement2.executeUpdate();
              
                          
                    } catch (SQLException exc) {
                          System.out.println("hata olustu "+exc.getMessage());
                  }  
            } else {
                System.out.println("Proje bulunamadı.");
            }
        } catch (SQLException exc) {
            System.out.println("analist sayisini almaya calisirkern hata" + exc.getMessage());
            // Hata durumunda uygun şekilde işleyin
        }
        

        }
        else if(empType.equals("Analyst")){
            int numberOfAnalystfromDatabase;
            Analyst a= (Analyst) e;

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, a.getProjectID());
    
                ResultSet resultSet = preparedStatement.executeQuery();
    
                if (resultSet.next()) {           
                    numberOfAnalystfromDatabase= resultSet.getInt("number_of_analyst");




                    String query3 = "UPDATE project SET number_of_analyst = ? WHERE project_id = ?";
                    try (PreparedStatement preparedStatement2 = conn.prepareStatement(query3)) {
                          preparedStatement2.setInt(1, numberOfAnalystfromDatabase+1);
                          preparedStatement2.setString(2, a.getProjectID());
              
                          int rowsAffected = preparedStatement2.executeUpdate();
              
                          if (rowsAffected > 0) {
                              System.out.println("Manager ID updated successfully.");
                          } else {
                          System.out.println("No rows were affected. Project not found.");
                      }
                  } catch (SQLException exc) {
                          System.out.println("Manageri projeye set ederken hata olustu "+exc.getMessage());
            
                  // Hata durumunda uygun şekilde işleyin
                  }  







                } else {
                    System.out.println("Proje bulunamadı.");
                }
            } catch (SQLException exc) {
                System.out.println("analist sayisini almaya calisirkern hata" + exc.getMessage());
                // Hata durumunda uygun şekilde işleyin
            }




             


        }else if(empType.equals("Programmer")){
            Programmer p= (Programmer) e;
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, p.getProjectID());
    
                ResultSet resultSet = preparedStatement.executeQuery();
    
                if (resultSet.next()) {           
                    int numberOfProgrammerfromDatabase= resultSet.getInt("number_of_programmer");








                    String query3 = "UPDATE project SET number_of_programmer = ? WHERE project_id = ?";
                    try (PreparedStatement preparedStatement2 = conn.prepareStatement(query3)) {
                          preparedStatement2.setInt(1, numberOfProgrammerfromDatabase+1);
                          preparedStatement2.setString(2, p.getProjectID());
              
                          int rowsAffected = preparedStatement2.executeUpdate();
              
                          
                    } catch (SQLException exc) {
                          System.out.println("hata olustu "+exc.getMessage());
            
        
                  }  


                } else {
                    System.out.println("Proje bulunamadı.");
                }
            } catch (SQLException exc) {
                System.out.println("analist sayisini almaya calisirkern hata" + exc.getMessage());
                // Hata durumunda uygun şekilde işleyin
            }


        }
    }
  
   
    private void setManagerOfTheProjectToDatabase(Manager m){
        String query = "UPDATE project SET manager_id = ? WHERE project_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, m.getIdentificationNumber());
            preparedStatement.setString(2, m.getProjectID());
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Manager ID updated successfully.");
            } else {
                System.out.println("No rows were affected. Project not found.");
            }
        } catch (SQLException e) {
            System.out.println("Manageri projeye set ederken hata olustu "+e.getMessage());
            e.printStackTrace();
            // Hata durumunda uygun şekilde işleyin
        }
    }
    
    

    public void deleteProjectFromDatabase(Project p ){
        try {
            String deleteQuery = "DELETE FROM project WHERE project_id = ?";

            // PreparedStatement oluşturma
            PreparedStatement statement = conn.prepareStatement(deleteQuery);
            statement.setString(1, p.getProjectID());

            // Sorguyu çalıştırma
            int rowsAffected = statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("hata "+e.getMessage());
        }
    }
    public void deleteManagerFromProject(Project pid){
        try {
            String updateProjectsQuery = "UPDATE project SET manager_id = ? WHERE project_id = ?";
            PreparedStatement projectsStatement = conn.prepareStatement(updateProjectsQuery);
            projectsStatement.setString(1, null);
            projectsStatement.setString(2, pid.getProjectID()); 
            projectsStatement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        


    }
    public void deleteEmployeeFromDatabase(String empId){
        try {
            String deleteQuery = "DELETE FROM employee WHERE identification_number = ?";

            // PreparedStatement oluşturma
            PreparedStatement statement = conn.prepareStatement(deleteQuery);
            statement.setString(1, (empId));
            deleteEmpfromWorksOn(empId);

            // Sorguyu çalıştırma
            int rowsAffected = statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("hata "+e.getMessage());
        }
    }
    private void deleteEmpfromWorksOn(String empId){
        try {
            String deleteQuery = "DELETE FROM works_on WHERE identification_id = ?";

            // PreparedStatement oluşturma
            PreparedStatement statement = conn.prepareStatement(deleteQuery);
            statement.setString(1, (empId));

            // Sorguyu çalıştırma
            int rowsAffected = statement.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("hata "+e.getMessage());
        }

    }
    

    
}