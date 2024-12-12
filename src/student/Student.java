package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Student {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    
    
    
    //insert data into student table
    public void insert(String id, String sname, String date, String gender, String email,
            String phone, String father, String mother, String address1, String address2, String imagePath){
        String sql = "insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, sname);
        ps.setString(3, date);
        ps.setString(4, gender);
        ps.setString(5, email);
        ps.setString(6, phone);
        ps.setString(7, father);
        ps.setString(8, mother);
        ps.setString(9, address1);
        ps.setString(10, address2);
        ps.setString(11, imagePath);
        
        if(ps.executeUpdate()>0){
            JOptionPane.showMessageDialog(null,"New Student added successfully");
        }
        }catch (SQLException ex){
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //check student email address is already exist
    public boolean isEmailExist(String email){
        try{
            ps = con.prepareStatement("Select * from student where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //check student phone number is already exist
    public boolean isPhoneExist(String phone){
        try{
            ps = con.prepareStatement("Select * from student where phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex){
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    //get all the student values form database student table
    public void getStudentValue(JTable table, String searchValue){
        String sql = "select * from student where concat(id,name,email,phone)like ? order by id desc";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model =(DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[11];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public boolean delete(String studentId) {
        try {
            // Prepare the SQL DELETE query with a WHERE clause
            ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setString(1, studentId); // Set the value of the parameter (student_id)

            // Execute the DELETE query
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Student record deleted successfully.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No record found with the specified ID.");
            }
        } catch (SQLException ex) {
            // Handle SQL exceptions and log errors
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the student record.");
        }
        return false;
    }
}
        
    

    

