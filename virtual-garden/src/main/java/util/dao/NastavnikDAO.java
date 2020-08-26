/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Polaganje;
import beans.Student;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class NastavnikDAO {
    public static Student searchByIndex(String indeks){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select * from"
                    + " korisnici k where k.korime=?");
            
            ps.setString(1, indeks);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                ps = conn.prepareStatement("select ime, prezime, count(*) as broj, avg(i.ocena) as prosek "
                        + "from korisnici k, ispiti i "
                        + "where k.korime = i.korime and i.ocena>5 and k.korime=?");
                
                ps.setString(1, indeks);
                
                ResultSet rs2= ps.executeQuery();
                
                if (rs2.next()){
                    Student student = new Student();
                    
                    student.setIme(rs2.getString("ime"));
                    student.setIndeks(indeks);
                    student.setPrezime(rs2.getString("prezime"));
                    student.setBrPolozenih(rs2.getInt("broj"));
                    student.setProsek(rs2.getDouble("prosek"));
                    
                    return student;
                }
                
                return null;
            }
            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }
    
    public static boolean searchStudent(String indeks){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select * from"
                    + " korisnici k where k.korime=?");
            
            ps.setString(1, indeks);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        return false;
    }
    
    public static void insertGrade(Polaganje p){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into "
                    + "ispiti(korime, sifraPredmet, datum, ocena) "
                    + "values (?,?,?,?)");
            
            ps.setString(1, p.getIndeks());
            ps.setString(2, p.getSifraPredmeta());
            ps.setDate(3, new Date(p.getDatum().getTime()));
            ps.setInt(4, p.getOcena());
            
            ps.executeUpdate();
                    
                    } catch (SQLException ex) {
            Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(NastavnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        
    }
}
