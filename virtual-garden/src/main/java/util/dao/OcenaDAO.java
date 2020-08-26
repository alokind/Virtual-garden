/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Ocena;
import beans.Proizvod;
import java.sql.Connection;
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
public class OcenaDAO {
    public static ArrayList<Ocena> dohvatiProsecneOceneZaProizvode(){
        ArrayList<Ocena> ocene = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT ocena.proizvodId, AVG(ocena.ocena) as prosek, COUNT(*) as brOcena FROM ocena WHERE ocena.ocena>0 GROUP BY ocena.proizvodId");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Ocena o = new Ocena();
                
                o.setProsecnaOcena(rs.getDouble("prosek"));
                o.setProizvodId(rs.getInt("proizvodId"));
                o.setBrOcena(rs.getInt("brOcena"));
                
                ocene.add(o);
            }  
                return ocene;
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
    
    public static ArrayList<Ocena> dohvatiOcenePoProizvodId(int proizvodId){
        ArrayList<Ocena> ocene = new ArrayList<>();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT * FROM ocena o, korisnici k WHERE k.id=o.korisnikId AND o.proizvodId=? AND o.ocena>0");
            ps.setInt(1, proizvodId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Ocena o = new Ocena();
                
                o.setIdocena(rs.getInt("idocena"));
                o.setKomentar(rs.getString("komentar"));
                o.setProizvodId(rs.getInt("proizvodId"));
                o.setKorisnikId(rs.getInt("korisnikId"));
                o.setKorime(rs.getString("korime"));
                o.setOcena(rs.getInt("ocena"));
                
                ocene.add(o);
            }
            
            return ocene;
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return null;
    }
    
    public static boolean mozeDaKomentarise(int proizvodId, int korisnikId){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT COUNT(*) as moze FROM ocena o WHERE o.proizvodId=? AND o.korisnikId=? AND o.ocena=0");
            ps.setInt(1, proizvodId);
            ps.setInt(2, korisnikId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                if (rs.getInt("moze")==1)
                    return true;
                else
                    return false;
            }
            
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return false;
    }
    
    public static int dodajKomentar(String komentar, int ocena, int proizvodId, int korisnikId){
        ArrayList<Ocena> ocene = new ArrayList<>();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("UPDATE ocena SET komentar=?, ocena=? WHERE proizvodId=? AND korisnikId=? AND ocena=0 AND idocena>0");
            ps.setString(1, komentar);
            ps.setInt(2, ocena);
            ps.setInt(3, proizvodId);
            ps.setInt(4, korisnikId);
            
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return -1;
    }
    
}
