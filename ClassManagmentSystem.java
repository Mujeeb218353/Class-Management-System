package classmanagmentsystem;
import java.awt.HeadlessException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
class dataBase{
    Connection con;
    Statement st;
    ResultSet rs;
    dataBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            st=con.createStatement();
        } catch (ClassNotFoundException|SQLException ex) {
            Logger.getLogger(dataBase.class.getName()).log(Level.SEVERE,
            null, 1);
        }
    }
    public ResultSet signInCheck(String username,String password){
        String sql= "SELECT* FROM studentsignininfo WHERE username='"+username+"' and password='"+password+"'";
        try{
            rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);   
        }
        return rs;
    }
    public ResultSet teacherCheck(String username,String password){
        String sql= "SELECT* FROM studentsignininfo WHERE username='"+username+"' and password='"+password+"' and Rank='teacher'";
        try{
            rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);   
        }
        return rs;
    }
    public ResultSet studentCheck(String username,String password){
        String sql= "SELECT* FROM studentsignininfo WHERE username='"+username+"'and password='"+password+"' "
                + "and Rank = 'student'";
        try{
            rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);   
        }
        return rs;
    }
    public ResultSet forgotPassCheck(String username,String food){
        String sql= "SELECT `username`, `password`, `food`, `Rank` FROM `studentsignininfo` WHERE username='"+username+"'and food='"+food+"'";
        try{
            rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);   
        }
        return rs;
    }
    void forgotPass(String username,String password,String food){
        String sql = "UPDATE studentsignininfo SET password='"+password+"' WHERE username IN ('"+username+"') AND food IN ('"+food+"')";
        try{
            st.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void updatePassword(String user,String pass,String newpass){
        String sql = "UPDATE studentsignininfo SET password='"+newpass+"' WHERE username IN ('"+user+"') AND password IN ('"+pass+"')";
        try{
            st.executeUpdate(sql);
            System.out.println("Password Changed Successfully...");
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public ResultSet infoGetter(String user){
        String sql = "SELECT `ID`,`Name` FROM `students` WHERE  username='"+user+"' ";
        try{
            rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
        return rs;
    }
    void enrollCourse(int id,String name,String username,String enrollStatus,int courseId,int teacherId,String teacher_name,String course_name){
        String sql = "INSERT INTO `studentsenrollment`(`ID`, `Name`, `username`, `teacher`, `course_Id`, `course`, `t_course_id`,`status`, `marks`, `enrolledStatus`) "
                + "VALUES ('"+id+"','"+name+"','"+username+"','"+teacher_name+"','"+courseId+"','"+course_name+"',"
                + "'"+teacherId+"','NULL','0','"+enrollStatus+"')";
        try{
            st.executeUpdate(sql);
        }catch(HeadlessException  | SQLException e){
            System.out.println(e);
        }
    }
    public ResultSet techerNameGetter(int id){
        String sql = "SELECT `t_name` FROM `teacher` WHERE ID_course = '"+id+"'";
     try{
             rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet courseNameGetter(int id){
        String sql = "SELECT  `Course` FROM `courses` WHERE ID = '"+id+"'";
     try{
             rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
        return rs;
    }
    public ResultSet showStudents(String name,int id){
        String sql = "SELECT `ID`, `Name`  FROM `studentsenrollment` WHERE teacher='"+name+"' and t_course_id='"+id+"'";
        try{
             rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
       return rs; 
    }
    void assignMarks(String teacherName,int teacherId,int totalMarks,String status,int studentId){
        String sql = "UPDATE `studentsenrollment` SET `status`='"+status+"',`marks`='"+totalMarks+"' WHERE  t_course_id = '"+teacherId+"' and"
                + " ID = '"+studentId+"' and teacher = '"+teacherName+"' ";
        try{
            st.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public ResultSet showMarks(String username){
        String sql = "SELECT `ID`, `Name`, `course_Id`, `course`, `status`, `marks`"
                + "   FROM `studentsenrollment` WHERE username = '"+username+"' ";
        try{
             rs=st.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
       return rs;
    }
    void delt(){
        try{
            String sql = "DELETE FROM `studentsenrollment` WHERE username = 'mujeeb157@gmail.com' and `course_Id` = '6' ";
                   
            st.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void update(){
        String sql = "UPDATE `studentsenrollment` SET `status`='Fail',`marks`='49' WHERE username = 'mujeeb157@gmail.com' and `course_Id`='5' ";
        try{
            st.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
public class ClassManagmentSystem {
    String username,password;
    dataBase db = new dataBase();
    public static void main(String[] args) { 
        dataBase db = new dataBase();
        Scanner sc = new Scanner(System.in);
        ClassManagmentSystem cms = new ClassManagmentSystem();
        //db.delt();
        //db.update();
        System.out.println("**************** Welcome to Class Management System ********************\n");
        while(true){
            System.out.println("1. Sign In");
            System.out.println("2. Forgot Password");
            System.out.println("3. Exit");
            System.out.print("Enter your choice:");
            int choice=sc.nextInt();
            switch(choice){
                case 1:
                    cms.signIn();
                    break;
                case 2:
                    cms.forgotPass();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again...");
            }
        }
    }
    void signIn(){
        ClassManagmentSystem cms = new ClassManagmentSystem();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username:");
        username = sc.next();
        System.out.print("Enter Password:");
        password = sc.next();
        ResultSet rs;
        rs = db.signInCheck(username, password);
        try{
            if(rs.next()){
                System.out.println("Sign In successfully...");
                cms.techerOrStudent(username,password);
            }else{
                System.out.println("Incorrect Username or Password...");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void techerOrStudent(String username,String password){
        ClassManagmentSystem cms = new ClassManagmentSystem();
        ResultSet rs,rs1;
        rs = db.teacherCheck(username, password);
        try{
            if(rs.next()){
                cms.teacherDashboard(username, password);
            }else{
                rs1 = db.studentCheck(username, password);
                if(rs1.next()){
                    cms.studentDashboard(username, password);
                }else{
                    System.out.println("No Record Found...");
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void forgotPass(){
        String food;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username:");
        username = sc.nextLine();
        System.out.print("Enter Your Favourite Food Name:");
        food = sc.nextLine();
        ResultSet rs;
        rs = db.forgotPassCheck(username, food);
        try{
            if(rs.next()){
                rs.getString("username");
                rs.getString("food");
                System.out.print("Enter New Password:");
                password = sc.nextLine();
                db.forgotPass(username, password, food);
                System.out.println("Password Updated Successfully...");
            }else{
                System.out.println("No Record Found...");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void teacherDashboard(String username,String password){
        Scanner sc = new Scanner(System.in);
        ClassManagmentSystem cms = new ClassManagmentSystem();
        teacher t = new teacher();
        System.out.println("Welcome To Teacher Dashboard...");
        int choice;
        while(true){
            System.out.println("1. Assign Marks");
            System.out.println("2. Update Password");
            System.out.println("3. Back");
            System.out.print("Enter your choice:");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    t.assignMarks(username,password);
                    break;
                case 2:
                    cms.updatePass(username,password);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again");
                    break;
            }
        }
    }
    void studentDashboard(String username,String password){
        System.out.println("Welcome To Student Dashboard...");
        Scanner sc = new Scanner(System.in);
        ClassManagmentSystem cms = new ClassManagmentSystem();
        student s = new student();
        int choice;
        while(true){
            System.out.println("1. Course Enrollment");
            System.out.println("2. View Marks");
            System.out.println("3. Update Password");
            System.out.println("4. Back");
            System.out.print("Enter your choice:");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    s.courseEnrollmentAndChecking(username,password);
                    break;
                case 2:
                    s.viewMarks(username);
                    break;
                case 3:
                    cms.updatePass(username,password);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again...");
                    break;
            }
        }
    }
    void updatePass(String user,String pass){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter New Password:");
        String newPass = sc.nextLine();
        db.updatePassword(user, pass, newPass);
    }
}
class teacher{
    dataBase db = new dataBase();
    void assignMarks(String user,String pass){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Your Course ID:");
        int teacherId =sc.nextInt();
        student s = new student();
        teacher t = new teacher();
        String teacherName = s.teacherNameGetter(teacherId);
        ResultSet rs = db.showStudents(teacherName,teacherId);
        t.showStudents(teacherName,teacherId);
        System.out.print("Enter Student ID to Assign Marks:");
        int studentId = sc.nextInt();
        int assignment1_Marks,assignment2_Marks,quiz1_Marks,quiz2_Marks,mid_Marks,final_Marks,parcipation_Marks,project_marks;
        System.out.print("Enter Assignment 1 Marks(5):");
        assignment1_Marks = sc.nextInt();
        System.out.print("Enter Assignment 2 Marks(5):");
        assignment2_Marks = sc.nextInt();
        System.out.print("Enter Quiz 1 Marks(5):");
        quiz1_Marks = sc.nextInt();
        System.out.print("Enter Quiz 2 Marks(5):");
        quiz2_Marks = sc.nextInt();
        System.out.print("Enter Mid Exam Marks(20):");
        mid_Marks = sc.nextInt();
        System.out.print("Enter Final Exam Marks(40):");
        final_Marks = sc.nextInt();
        System.out.print("Enter Class Participation Marks(5):");
        parcipation_Marks = sc.nextInt();
        System.out.print("Enter Presentation/Project Marks(15):");
        project_marks = sc.nextInt();
        int total_Marks = assignment1_Marks+assignment2_Marks+quiz1_Marks+quiz2_Marks+mid_Marks+final_Marks+parcipation_Marks+project_marks;
        String status;
        if(total_Marks>100){
            total_Marks=100;
        }
        if(total_Marks>=50 && total_Marks<=100){
            status = "Pass";
        }else{
            status = "Fail";
        }
        db.assignMarks(teacherName,teacherId,total_Marks,status,studentId);
    }
    void showStudents(String teacherName,int teacherId){
        ResultSet rs = db.showStudents(teacherName,teacherId);
        int n=0;
        int []dataId = new int[100];
        String []data= new String[100];
        try{
            int i=0,id;
            while(rs.next()){
                if(rs == null){
                    System.out.println("Students aren't Enrolled...");
                    return;
                }else{
                String name = rs.getString("Name");
                id = rs.getInt("ID");
                dataId[i] = id;
                data[i] = name;
                n++;
                i++;
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        System.out.println("   ID     Name");
        for(int i=0;i<n;i++){
            System.out.format("| %-7d | %-40s |\n",dataId[i],data[i]);
        }
    }
}
class student{
    dataBase db = new dataBase();
    void courseEnrollmentAndChecking(String user,String pass) {
        Scanner sc = new Scanner(System.in);
        student s = new student();
        ResultSet rs = db.infoGetter(user);
        try{
            if(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                for(int i=1;i<=5;i++){
                    s.courseEnrollment(id, name, user);
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    void courseEnrollment(int id,String name,String username){
        Scanner sc = new Scanner(System.in);
        String enrollStatus = "Enrolled";
        student s = new student();
        System.out.print("Enter Course ID:");
        int courseId = sc.nextInt();
        System.out.print("Enter Teacher ID:");
        int teacherId = sc.nextInt();
        db.enrollCourse(id,name,username,enrollStatus,courseId,teacherId,
s.teacherNameGetter(teacherId),s.courseNameGetter(courseId));
    }
    String teacherNameGetter(int teacherId){
        ResultSet t = db.techerNameGetter(teacherId);
        String t_name=null;
        try{
            if(t.next()){
                t_name = t.getString("t_name");
                return t_name;
            }else{
                System.out.println("No Record Found...");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return t_name;
    }
    String courseNameGetter(int courseId){
        ResultSet t = db.courseNameGetter(courseId);
        String c_name=null;
        try{
            if(t.next()){
                c_name = t.getString("Course");
                return c_name;
            }else{
                System.out.println("No Record Found...");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return c_name;
    }
    void viewMarks(String username){
        ResultSet rs = db.showMarks(username);
        int n=0,marks,id=0,courseId;
        String name="",course,status;
        String []course_name = new String[100];
        int []marks_course = new int[100];
        int []course_Id = new int[100];
        String []Status = new String[100];
        int overall_marks=0;
        try{
            int i=0;
            while(rs.next()){
                status = rs.getString("status"); 
                if(status.equals("NULL")){
                    continue;
                }   
                name = rs.getString("Name");
                id = rs.getInt("ID");
                course = rs.getString("course");
                courseId  = rs.getInt("course_Id");
                marks = rs.getInt("marks");
                course_name[i] = course;
                course_Id[i] = courseId;
                marks_course[i] = marks;
                Status[i] = status;
                n++;
                i++;
                overall_marks+=100;
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        int total_marks = 0;
        for(int i=0;i<n;i++){
            total_marks += marks_course[i];
        }
        System.out.println("\n***************************** Mark Sheet *********************************");
        System.out.println("Name:"+name);
        System.out.println("ID:"+id);
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Course ID    Course                                       Marks   Status");
        String grade;
        for(int i=0;i<n;i++){
        System.out.format("| %-10s | %-40s | %-6d | %-7s |\n",marks_course[i],course_name[i],marks_course[i],Status[i]);
        }
        float percentage = (total_marks*100)/overall_marks;
        if(percentage>=80 && percentage<=100){
            grade  = "A-1";
        }else if(percentage>=70 && percentage<80){
            grade  = "A";
        }else if(percentage>=60 && percentage<70){
            grade  = "B";
        }else if(percentage>=50 && percentage<60){
            grade  = "C";
        }else if(percentage>=40 && percentage<50){
            grade  = "D";
        }else if(percentage>=33 && percentage<40){
            grade  = "E";
        }else{
            grade  = "F";
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Total Mark:"+total_marks);
        System.out.println("Grade:"+grade);
        System.out.println("Percentage:"+percentage+"%");
        System.out.println("-----------------------------------------------------------------------------");
    }
}