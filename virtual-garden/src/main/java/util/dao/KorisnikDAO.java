/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
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
public class KorisnikDAO {
    public static Korisnik login(String username, String password){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("select * from korisnici where korime=? and lozinka=?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                Korisnik k = new Korisnik();
                
                k.setId(rs.getInt("id"));
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                k.setKorime(rs.getString("korime"));
                k.setLozinka(rs.getString("lozinka"));
                k.setDatum(new java.util.Date(rs.getDate("datum").getTime()));
                k.setMesto(rs.getString("mesto"));
                k.setMejl(rs.getString("mejl"));
                k.setTelefon(rs.getString("telefon"));
                k.setTip(rs.getString("tip"));
                k.setJeOdobren(rs.getInt("jeOdobren"));
                
                return k;

            } else 
                return null;
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
        return null;
    }
    
    public static boolean register(Korisnik korisnik){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("INSERT INTO projekat.korisnici (korime, lozinka, ime, prezime, datum, mesto, telefon, mejl, tip, jeOdobren) VALUES (?,?,?,?,?,?,?,?,?,0)");
            ps.setString(1, korisnik.getKorime());
            ps.setString(2, korisnik.getLozinka());
            ps.setString(3, korisnik.getIme());
            ps.setString(4, korisnik.getPrezime());
            ps.setDate(5, new Date(korisnik.getDatum().getTime()));
            ps.setString(6, korisnik.getMesto());
            ps.setString(7, korisnik.getTelefon());
            ps.setString(8, korisnik.getMejl());
            ps.setString(9, korisnik.getTip());
            
            ps.executeUpdate();
            return true;
            
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
        return false;
    }
    
    public static boolean checkIfUsernameExists(Korisnik korisnik){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("select count(*) from korisnici where korime=?");
            ps.setString(1, korisnik.getKorime());
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                if(rs.getInt(1)>=1)
                    return true;
                else
                    return false;
            }
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
        return true;
    }
    
    public static void promenaLozinke(int korisnikId, String novaLozinka){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("UPDATE korisnici SET lozinka=? WHERE id=?");
            ps.setString(1, novaLozinka);
            ps.setInt(2, korisnikId);
            
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
    
    
    public static int brojSlobodnihDostavljaca(int korisnikId){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT brDostavljaca FROM korisnici WHERE id=?");
            ps.setInt(1, korisnikId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                return rs.getInt("brDostavljaca");
            } 
            
            return -1;
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
        return -1;
    }
    
        public static ArrayList<Korisnik> dohvatiSveKorisnike(){
        ArrayList<Korisnik> korisnici = new ArrayList<> ();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT * FROM korisnici");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Korisnik k = new Korisnik();
                
                
                k.setId(rs.getInt("id"));
                k.setIme(rs.getString("ime"));
                k.setPrezime(rs.getString("prezime"));
                k.setKorime(rs.getString("korime"));
                k.setLozinka(rs.getString("lozinka"));
                k.setDatum(new java.util.Date(rs.getDate("datum").getTime()));
                k.setMesto(rs.getString("mesto"));
                k.setMejl(rs.getString("mejl"));
                k.setTelefon(rs.getString("telefon"));
                k.setTip(rs.getString("tip"));
                k.setJeOdobren(rs.getInt("jeOdobren"));
                
                korisnici.add(k);
            } 
            
            return korisnici;
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
        return null;
    }
        
    public static boolean updateKorisnik(Korisnik korisnik){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("UPDATE korisnici SET korime=?, lozinka=?, ime=?, prezime=?, datum=?, mesto=?, telefon=?, mejl=?, tip=?, jeOdobren=? WHERE id=?");
            ps.setString(1, korisnik.getKorime());
            ps.setString(2, korisnik.getLozinka());
            ps.setString(3, korisnik.getIme());
            ps.setString(4, korisnik.getPrezime());
            ps.setDate(5, new Date(korisnik.getDatum().getTime()));
            ps.setString(6, korisnik.getMesto());
            ps.setString(7, korisnik.getTelefon());
            ps.setString(8, korisnik.getMejl());
            ps.setString(9, korisnik.getTip());
            ps.setInt(10, korisnik.getJeOdobren());
            ps.setInt(11, korisnik.getId());
            
            ps.executeUpdate();
            return true;
            
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
        return false;
    }   

    public static void ukloniKorisnika(int korisnikId) {
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("DELETE FROM korisnici WHERE id=?");
            ps.setInt(1, korisnikId);
            
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
