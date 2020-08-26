/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Proizvod;
import beans.Rasadnik;
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
public class ProizvodDAO {
    
    public static void updateIsporuceneProizvode(){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        PreparedStatement ps5 = null;
        try {
            
            conn.setAutoCommit(false);
            ps1 = conn.prepareStatement("INSERT INTO proizvodiumagacinu (proizvodId, rasadnikId, kolicina) " +
                                        "SELECT proizvodId, rasadnikId, SUM(kolicina) " +
                                        "FROM isporukaproizvoda i " +
                                        "WHERE i.odbijenaPrihvacenaNaCekanju=1 " +
                                        "AND stize<NOW() " +
                                        "GROUP BY rasadnikId, proizvodId  " +
                                        "ON DUPLICATE KEY UPDATE  " +
                                        "kolicina = kolicina + values(kolicina)");
            
            ps2 = conn.prepareStatement("SET SQL_SAFE_UPDATES=0");
            
            ps3 = conn.prepareStatement("UPDATE korisnici as k,\n" +
                                        "	(SELECT prodavnicaId, COUNT(*) as brGotovihDostavljaca FROM ( " +
                                        "		SELECT *, COUNT(stize) " +
                                        "		FROM isporukaproizvoda " +
                                        "		WHERE stize<NOW() AND odbijenaPrihvacenaNaCekanju=1 " +
                                        "		GROUP BY stize) AS c " +
                                        "	group by prodavnicaId) as g " +
                                        "SET k.brDostavljaca = k.brDostavljaca + g.brGotovihDostavljaca " +
                                        "WHERE k.id = g.prodavnicaId");
                     
            

            ps4 = conn.prepareStatement("UPDATE isporukaproizvoda SET isporukaproizvoda.odbijenaPrihvacenaNaCekanju=3 WHERE stize < NOW() AND isporukaproizvoda.odbijenaPrihvacenaNaCekanju=1 ");
            
            ps5 = conn.prepareStatement("SET SQL_SAFE_UPDATES=1");

            ps1.executeUpdate();
            ps2.execute();
            ps3.executeUpdate();
            ps4.executeUpdate();
            ps5.execute();

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                conn.setAutoCommit(true);
                if(ps1!=null)
                    ps1.close();
                if(ps2!=null)
                    ps2.close();
                if(ps3!=null)
                    ps3.close();
                if(ps4!=null)
                    ps4.close();
                if(ps5!=null)
                    ps5.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    public static ArrayList<Proizvod> dohvatiProizvodeUMagacinuZaRasadnikId(int rasadnikId){
        ArrayList<Proizvod> proizvodi = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        //UPDATE ISPORUCENE PROIZVODE
        updateIsporuceneProizvode();
        
        try {
            ps = conn.prepareStatement("SELECT * FROM proizvodiumagacinu pum, proizvodi p WHERE pum.proizvodId=p.id AND pum.rasadnikId=?");
            ps.setInt(1, rasadnikId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Proizvod p = new Proizvod();
                
                p.setId(rs.getInt("proizvodId"));
                p.setNaziv(rs.getString("naziv"));
                p.setProizvodjac(rs.getString("proizvodjac"));
                p.setRasadnikId(rs.getInt("rasadnikId"));
                p.setTrajanje(rs.getInt("trajanje"));
                p.setKolicina(rs.getInt("kolicina"));
                p.setJeSadnica(rs.getInt("jeSadnica"));
                p.setMozeDaSePonisti(0);
                p.setProdavnicaId(-1);
                        
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
    
    public static ArrayList<Proizvod> dohvatiProizvodeNaIsporuciZaRasadnikId(int rasadnikId){
        ArrayList<Proizvod> proizvodi = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        //UPDATE ISPORUCENE PROIZVODE
        updateIsporuceneProizvode();
        
        try {
            ps = conn.prepareStatement("SELECT * FROM isporukaproizvoda i, proizvodi p WHERE i.proizvodId=p.id AND i.rasadnikId=? AND i.odbijenaPrihvacenaNaCekanju=-1");
            ps.setInt(1, rasadnikId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                Proizvod p = new Proizvod();
                
                p.setIsporukaProizvodaId(rs.getInt("idisporukaproizvoda"));
                p.setId(rs.getInt("proizvodId"));
                p.setNaziv(rs.getString("naziv"));
                p.setProizvodjac(rs.getString("proizvodjac"));
                p.setRasadnikId(rs.getInt("rasadnikId"));
                p.setTrajanje(rs.getInt("trajanje"));
                p.setKolicina(rs.getInt("kolicina"));
                p.setJeSadnica(rs.getInt("jeSadnica"));
                p.setMozeDaSePonisti(1);
                p.setProdavnicaId(rs.getInt("prodavnicaId"));
                p.setCena(rs.getInt("cena"));
                
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
    
    public static Proizvod dohvatiProizvodPoId(int proizvodId){
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("SELECT * FROM proizvodiumagacinu pum, proizvodi p WHERE pum.proizvodId=p.id AND  p.id=?");
            ps.setInt(1, proizvodId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                Proizvod p = new Proizvod();
                
                p.setId(proizvodId);
                p.setNaziv(rs.getString("naziv"));
                p.setProizvodjac(rs.getString("proizvodjac"));
                p.setRasadnikId(rs.getInt("rasadnikId"));
                p.setTrajanje(rs.getInt("trajanje"));
                p.setKolicina(rs.getInt("kolicina"));
                p.setJeSadnica(rs.getInt("jeSadnica"));
                
                return p;
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
        return null;
    }
    
    public static void izbrisiProizvodIzIsporuke(Proizvod p, Rasadnik r, int korisnikId){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
            
            try {
                conn.setAutoCommit(false);
                ps = conn.prepareStatement("UPDATE isporukaproizvoda SET odbijenaPrihvacenaNaCekanju=0 WHERE proizvodId=? AND rasadnikId=? AND prodavnicaId=? AND idisporukaproizvoda=?");
                ps.setInt(1,p.getId());
                ps.setInt(2, r.getId());
                ps.setInt(3, p.getProdavnicaId());
                ps.setInt(4, p.getIsporukaProizvodaId());
                
                ps2 = conn.prepareStatement("INSERT INTO onlineprodavnica (prodavnicaId,proizvodId,kolicina) " +
                            "SELECT prodavnicaId, proizvodId, kolicina " +
                            "FROM isporukaproizvoda i " +
                            "WHERE i.proizvodId = ? " +
                            "AND i.prodavnicaId=? " +
                            "AND i.idisporukaproizvoda=? " +
                            "AND i.odbijenaPrihvacenaNaCekanju=0 " +
                            "ON DUPLICATE KEY UPDATE onlineprodavnica.kolicina = onlineprodavnica.kolicina + i.kolicina");
                ps2.setInt(1, p.getId());
                ps2.setInt(2, p.getProdavnicaId());
                ps2.setInt(3, p.getIsporukaProizvodaId());
                
                ps3 = conn.prepareStatement("DELETE FROM ocena WHERE proizvodId=? AND korisnikId=? AND ocena=0 AND idocena<>0");
                ps3.setInt(1, p.getId());
                ps3.setInt(2, korisnikId);
                
                
                ps.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                if(ps!=null)
                    ps.close();
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

    
}
