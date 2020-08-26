/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
import beans.Proizvod;
import beans.Rasadnik;
import beans.Sadnica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static util.dao.ProizvodDAO.updateIsporuceneProizvode;

/**
 *
 * @author User
 */
public class OnlineProdavnicaDAO {
    
    public static boolean kupi(ArrayList<Proizvod> proizvodi, Korisnik korisnik, int rasadnikId){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        
        try {
            conn.setAutoCommit(false);
            for (Proizvod proizvod : proizvodi) {
                if (proizvod.getKolikoKomada()>=1){

                ps2 = conn.prepareStatement("UPDATE onlineprodavnica SET kolicina=kolicina-? WHERE id=?");
                ps2.setInt(1, proizvod.getKolikoKomada());
                ps2.setInt(2, proizvod.getOnlineProdavnicaid());
                ps2.executeUpdate();
                
                Timestamp timestamp = new Timestamp(new Date().getTime());

                ps1 = conn.prepareStatement("INSERT INTO isporukaproizvoda (rasadnikId, prodavnicaId, proizvodId, kolicina, stize, odbijenaPrihvacenaNaCekanju) VALUES (?,?,?,?,?,-1)");
                ps1.setInt(1, rasadnikId);
                ps1.setInt(2, proizvod.getProdavnicaId());
                ps1.setInt(3, proizvod.getId());
                ps1.setInt(4, proizvod.getKolikoKomada());
                ps1.setTimestamp(5, timestamp);
                ps1.executeUpdate();
                
                ps3 = conn.prepareStatement("INSERT INTO ocena (proizvodId, korisnikId, komentar, ocena) " +
                                            "SELECT ?,?,'',0 " +
                                            "WHERE NOT EXISTS " +
                                            "(SELECT * from ocena WHERE proizvodId=? AND korisnikId=?)");
                ps3.setInt(1, proizvod.getId());
                ps3.setInt(2, korisnik.getId());
                ps3.setInt(3, proizvod.getId());
                ps3.setInt(4, korisnik.getId());
                ps3.executeUpdate();
                }
            
            }
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                conn.setAutoCommit(true);
                if(ps1 != null)
                    ps1.close();
                if(ps3 != null)
                    ps3.close();
                if(ps2 != null){
                    ps2.close();
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return false;
    }
    
    public static ArrayList<Proizvod> dohvatiCeluProdavnicu(){
        ArrayList<Proizvod> proizvodi = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT *, op.id as opid, proi.id as proiId, prod.id as prodid, prod.ime as prodIme, proi.naziv as proiIme, proi.proizvodjac as proizvodjac, op.kolicina as kolicina FROM onlineprodavnica op, korisnici prod, proizvodi proi WHERE op.prodavnicaId=prod.id AND op.proizvodId=proi.id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Proizvod p = new Proizvod();
                
                p.setJeSadnica(rs.getInt("jeSadnica"));
                p.setTrajanje(rs.getInt("trajanje"));
                p.setOnlineProdavnicaid(rs.getInt("opid"));
                p.setProdavnicaId(rs.getInt("prodid"));
                p.setId(rs.getInt("proiId"));
                p.setNaziv(rs.getString("proiIme"));
                p.setProdavnicaIme(rs.getString("prodIme"));
                p.setProizvodjac(rs.getString("proizvodjac"));
                p.setKolicina(rs.getInt("kolicina"));
                
                proizvodi.add(p);
            }  
                return proizvodi;
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
    
    public static void izbrisiProizvodIzProdavnice(int proizvodId, int korisnikId){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            try {
                
                ps = conn.prepareStatement("DELETE FROM onlineprodavnica WHERE proizvodId=? AND prodavnicaID=?");
                ps.setInt(1, proizvodId);
                ps.setInt(2, korisnikId);
                
                
                ps.executeUpdate();
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    
    public static boolean dodajProizvodUProizvodeIProdavnicu(String naziv, String proizvodjac, int jeSadnica, int trajanje, int cena, int prodavnicaId, int kolicina){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement("INSERT INTO proizvodi (naziv, proizvodjac, jeSadnica, trajanje, cena) VALUES (?,?,?,?,?)");
            ps1.setString(1, naziv);
            ps1.setString(2, proizvodjac);
            ps1.setInt(3, jeSadnica);
            ps1.setInt(4, trajanje);
            ps1.setInt(5, cena);
            ps1.executeUpdate();

            ps2 = conn.prepareStatement("INSERT INTO onlineProdavnica (prodavnicaId, proizvodId, kolicina) VALUES (?, (SELECT MAX(id) FROM proizvodi),?)");
            ps2.setInt(1, prodavnicaId);
            ps2.setInt(2, kolicina);
            ps2.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                conn.setAutoCommit(true);
                if(ps1 != null)
                    ps1.close();
                if(ps2 != null){
                    ps2.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
        return false;
    }
}
