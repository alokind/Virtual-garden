/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Polaganje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class StudentDAO {
    
    public static ArrayList<Polaganje> dohvatiPolaganja(String indeks){
        ArrayList<Polaganje> polaganja = new ArrayList<Polaganje>();
        
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("select * from ispiti where korime=?");
            ps.setString(1, indeks);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Polaganje p = new Polaganje();
                
                p.setDatum(new Date(rs.getDate("datum").getTime()));
                p.setIndeks(indeks);
                p.setOcena(rs.getInt("ocena"));
                p.setSifraPredmeta(rs.getString("sifraPredmet"));
                
                polaganja.add(p);
            }
            
            return polaganja;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            util.DB.getInstance().putConnection(conn);
        }
        
        return null;
    }
    
}
