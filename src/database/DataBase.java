/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.mariadb.jdbc.Driver.*;
/**
 *
 * @author Motaz
 */
class Person{
    public int id;
    public String fName, mName, lName, email, phone;
    public Person(int id, String fName, String mName, String lName, String email, String phone) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
    }
}

public class DataBase {
    int affectedRow;
    ResultSet st;
    ArrayList <Person> stArrayList;
    PreparedStatement pStatement;
    Connection con;
    ResultSet rs;
    Person retrievedPerson;
    public DataBase() throws SQLException{
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            stArrayList = new ArrayList<>();
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/java","root","3791932");
                         getAll();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        DataBase db = new DataBase();
        if(!db.stArrayList.isEmpty()){
        Person now = db.getFirst();            
        }
    }
    

    
    public void getAll() throws SQLException{
        pStatement = con.prepareStatement("SELECT * FROM PERSONS;",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
        rs = pStatement.executeQuery();
        if (stArrayList.size()>0){
            stArrayList.clear();
        }
        while(rs.next()){
            retrievedPerson = new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            stArrayList.add(retrievedPerson);
            
        }
    }
    
    
    public void update(int id , String fName, String mName, String lName, String email, String phone) throws SQLException{
        for(int i = 0; i < stArrayList.size();i++)
        {
            if (stArrayList.get(i).id==id) {
             pStatement = con.prepareStatement("UPDATE Persons SET FirstName  = ? , MiddleName  = ?,LastName  = ?,Email  = ?,Phone  = ? WHERE ID = ?;",
             ResultSet.TYPE_SCROLL_INSENSITIVE,
             ResultSet.CONCUR_UPDATABLE);    
             pStatement.setString(1, fName);
             pStatement.setString(2, mName);
             pStatement.setString(3, lName);
             pStatement.setString(4, email);
             pStatement.setString(5, phone);
             pStatement.setInt(6,id);

             affectedRow = pStatement.executeUpdate();
             getAll();
            }
        }
    }
        public void insert(String fName, String mName, String lName, String email, String phone) throws SQLException{
            int lastId; 
            if(!stArrayList.isEmpty())             {
             lastId = stArrayList.get(stArrayList.size()-1).id;                 
             }
            else{
             lastId = 1;
            }
             pStatement = con.prepareStatement("INSERT INTO Persons VALUES ( ? , ? ,? , ? , ? , ? );",
             ResultSet.TYPE_SCROLL_INSENSITIVE,
             ResultSet.CONCUR_UPDATABLE);    
             pStatement.setInt(1,lastId);
             pStatement.setString(2, fName);
             pStatement.setString(3, mName);
             pStatement.setString(4, lName);
             pStatement.setString(5, email);
             pStatement.setString(6, phone);
             affectedRow = pStatement.executeUpdate();
             getAll();
        }
    
    
    public int Delete(int id) throws SQLException{
        for(int i = 0; i < stArrayList.size();i++)
        {
            if (stArrayList.get(i).id==id) {
             pStatement = con.prepareStatement("DELETE FROM Persons WHERE ID = ?;",
                     
             ResultSet.TYPE_SCROLL_INSENSITIVE,
             ResultSet.CONCUR_UPDATABLE);    
             pStatement.setInt(1,id);
             affectedRow = pStatement.executeUpdate();
             getAll();
             return i;
            }
        }
        return -1;
    }
    
    public Person getFirst() throws SQLException{
        return stArrayList.get(0);
    }
    
        public Person getLast() throws SQLException{
        return stArrayList.get(stArrayList.size()-1);
    }
        
        
    public Person getNext(int id) throws SQLException{
        int i;
        
        for(i = 0 ; i<stArrayList.size()-1;i++){
            if(stArrayList.get(i).id == id ){
                return stArrayList.get(i+1);
            }
        }
                return null;
    }
    
        public Person getPrev(int id) throws SQLException{
            int i;
        for(i = 1 ; i<stArrayList.size();i++){
            if(stArrayList.get(i).id == id){
                return stArrayList.get(i-1);
            }
        }
            return null;
    }
        public int searchPerson(int id){
            int i;
            for(i = 0 ; i<stArrayList.size();i++){
            if(stArrayList.get(i).id == id){
                return i;
            }
        }
            return -1;            
        }
}
