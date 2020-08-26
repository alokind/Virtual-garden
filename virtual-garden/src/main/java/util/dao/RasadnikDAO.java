/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Proizvod;
import beans.Rasadnik;
import beans.Sadnica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RasadnikDAO {
    
    public static void updateStatus(int korisnikId){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
            
            try {
                    conn.setAutoCommit(false);
                    ps1 = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
                    
                    ps2 = conn.prepareStatement("UPDATE rasadnici " +
                                                "SET kolVode = GREATEST(0, kolVode - TIMESTAMPDIFF(hour, updated, NOW())), " +
                                                "    temp = GREATEST(0, temp - TIMESTAMPDIFF(hour, updated, NOW())*0.5), " +
                                                "    updated = NOW() " +
                                                "WHERE korisnikId=?");
                    ps2.setInt(1, korisnikId);
                    
                    ps3 = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");
                    
                    ps1.execute();
                    ps2.executeUpdate();
                    ps3.execute();
                    
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                if(ps1!=null)
                    ps1.close();
                if(ps2!=null)
                    ps2.close();
                if(ps3!=null)
                    ps3.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    public static ArrayList<Rasadnik> dohvatiRasadnikeZaKorisnika(int korisnikId){
        ArrayList<Rasadnik> rasadnici = new ArrayList<Rasadnik>();
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null, ps2 = null;
            
            try {
                    ps = conn.prepareStatement("select * from rasadnici r, korisnici k where k.id=r.korisnikId AND k.id=?");
                    ps.setInt(1, korisnikId);
                    ResultSet rs = ps.executeQuery();
                    
                    while (rs.next()){
                        Rasadnik r = new Rasadnik();
                        
                        ps2 = conn.prepareStatement("select COUNT(*) as brSadnica FROM sadnice s WHERE s.rasadnikId=?");
                        ps2.setInt(1, rs.getInt("id"));
                        ResultSet rs2 = ps2.executeQuery();
                        rs2.next();
                        
                        r.setId(rs.getInt("id"));
                        r.setSirina(rs.getInt("sirina"));
                        r.setDuzina(rs.getInt("duzina"));
                        r.setBrSadnica(rs2.getInt("brSadnica"));
                        r.setKolVode(rs.getInt("kolVode"));
                        r.setKorisnikId(rs.getInt("korisnikId"));
                        r.setTemp(rs.getDouble("temp"));
                        r.setIme(rs.getString("ime"));
                        r.setMesto(rs.getString("mesto"));
                        
                        rasadnici.add(r);
                    }
                    return rasadnici;
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                ps.close();
                ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return null;
            
    }
    
    public static Rasadnik dohvatiRasadnikPoId(int rasadnikId){
            
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null, ps2 = null;
            
            try {
                    ps = conn.prepareStatement("select * from rasadnici r where r.id=?");
                    ps.setInt(1, rasadnikId);
                    ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()){
                        Rasadnik r = new Rasadnik();
                        
                        ps2 = conn.prepareStatement("select COUNT(*) as brSadnica FROM sadnice s WHERE s.rasadnikId=?");
                        ps2.setInt(1, rs.getInt("id"));
                        ResultSet rs2 = ps2.executeQuery();
                        rs2.next();
                        
                        r.setId(rs.getInt("id"));
                        r.setSirina(rs.getInt("sirina"));
                        r.setDuzina(rs.getInt("duzina"));
                        r.setBrSadnica(rs2.getInt("brSadnica"));
                        r.setKolVode(rs.getInt("kolVode"));
                        r.setKorisnikId(rs.getInt("korisnikId"));
                        r.setTemp(rs.getDouble("temp"));
                        r.setIme(rs.getString("ime"));
                        r.setMesto(rs.getString("mesto"));
                        
                        return r;
                    }
                    return null;
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                ps.close();
                ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return null;
            
    }

    public static void smanjiVoduIliTemp(double zaKoliko, String sta, int rasadnikId){
            
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            
            try {
                    ps = conn.prepareStatement("UPDATE rasadnici r SET r."+sta+" = r."+sta+" + ? where r.id=?");
                    ps.setDouble(1, zaKoliko);
                    ps.setInt(2, rasadnikId);
                    ps.executeUpdate();
                    
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
            
    }
            
    public static void primeniPreparatIUkloniIzMagacina(Sadnica sadnica, Rasadnik rasadnik, Proizvod proizvod) {
       Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
            
            try {
                conn.setAutoCommit(false);
                ps1 = conn.prepareStatement("UPDATE sadnice  SET vadjenje=DATE_SUB(vadjenje, INTERVAL ? day)  WHERE id=?");
                ps1.setInt(1, proizvod.getTrajanje());
                ps1.setInt(2, sadnica.getId());   
                
                ps2 = conn.prepareStatement("UPDATE proizvodiumagacinu SET kolicina = kolicina - 1 WHERE proizvodId=?");
                ps2.setInt(1, proizvod.getId());
                
                ps3 = conn.prepareStatement("DELETE FROM proizvodiumagacinu WHERE kolicina <= 0");
                
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                ps1.close();
                ps2.close();
                ps3.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }

    public static void dodajUSadniceIUkloniIzMagacina(Sadnica sadnica, Rasadnik rasadnik, Proizvod proizvod) {
       Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
            
            try {
                conn.setAutoCommit(false);
                ps1 = conn.prepareStatement("INSERT INTO sadnice (proizvodId, rasadnikId, pozicija, vadjenje, biceIzvadjena) VALUES (?,?,?,?,0)");
                ps1.setInt(1, proizvod.getId());
                ps1.setInt(2, rasadnik.getId());   
                ps1.setInt(3, sadnica.getPozicija());
                ps1.setDate(4, new java.sql.Date(new Date().getTime()+ TimeUnit.DAYS.toMillis(proizvod.getTrajanje())));
                
                ps2 = conn.prepareStatement("UPDATE proizvodiumagacinu SET kolicina = kolicina - 1 WHERE proizvodId=?");
                ps2.setInt(1, proizvod.getId());
                
                ps3 = conn.prepareStatement("DELETE FROM proizvodiumagacinu WHERE kolicina <= 0");
                
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                ps1.close();
                ps2.close();
                ps3.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    public static void dodajRasadnik(int korisnikId, String naziv, String mesto, int sirina, int duzina){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            
            try {
                    ps = conn.prepareStatement("INSERT INTO rasadnici (ime, mesto, kolVode, temp, korisnikId, duzina, sirina) VALUES (?,?,200,18,?,?,?)");
                    ps.setString(1, naziv);
                    ps.setString(2, mesto);
                    ps.setInt(3, korisnikId);
                    ps.setInt(4, duzina);
                    ps.setInt(5, sirina);
                    
                    ps.executeUpdate();
                    

            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                if (ps!=null)
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    

    
}
