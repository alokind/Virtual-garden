/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
import beans.Sadnica;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SadniceDAO {
    public static ArrayList<Sadnica>  dohvatiSadniceZaRasadnikId(int rasadnikId){
        ArrayList<Sadnica> sadnice = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn.setAutoCommit(false);
            ps2 = conn.prepareStatement("DELETE FROM sadnice WHERE biceIzvadjena=1 AND vadjenje<CURDATE() AND rasadnikId=?");
            ps2.setInt(1, rasadnikId);
            ps2.executeUpdate();
            
            ps1 = conn.prepareStatement("SELECT * FROM sadnice s, rasadnici r, proizvodi p WHERE s.rasadnikId=r.id AND s.proizvodId=p.id AND r.id=?");
            ps1.setInt(1, rasadnikId);
            ResultSet rs = ps1.executeQuery();
            
            while (rs.next()){
                Sadnica s = new Sadnica();
                
                s.setId(rs.getInt("id"));
                s.setDatum(new java.util.Date(rs.getDate("vadjenje").getTime()));
                s.setKorisnikId(rs.getInt("korisnikId"));
                s.setNaziv(rs.getString("naziv"));
                s.setPozicija(rs.getInt("pozicija"));
                s.setProizvodjac(rs.getString("proizvodjac"));
                s.setRasadnikId(rs.getInt("rasadnikId"));
                s.setTrajanje(rs.getInt("trajanje"));
                s.setBiceIzvadjena(rs.getInt("biceIzvadjena"));
                
                sadnice.add(s);
            }  
                return sadnice;
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                conn.setAutoCommit(true);
                ps1.close();
                ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }
    
        public static void postaviBiceIzvadjena(int sadnicaId){
        ArrayList<Sadnica> sadnice = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("UPDATE sadnice SET biceIzvadjena = 1, vadjenje=DATE_ADD(CURDATE(),INTERVAL 1 day) WHERE id=?");
            ps.setInt(1, sadnicaId);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
}
