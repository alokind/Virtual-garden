/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Narudzbina;
import beans.Proizvod;
import beans.Rasadnik;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static util.dao.ProizvodDAO.updateIsporuceneProizvode;

/**
 *
 * @author User
 */
public class IsporukaProizvodaDAO {
    
    
    
    public static ArrayList<Narudzbina> dohvatiNarudzbine(int prodavnicaId, int odbijenaPrihvacenaNaCekanju){
        ArrayList<Narudzbina> narudzbine = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            
            try {
                    ps = conn.prepareStatement("SELECT *, sum(kolicina) as komada, k.ime as kime, r.mesto as rmesto " +
                                                "FROM isporukaproizvoda i, rasadnici r, korisnici k " +
                                                "WHERE r.korisnikId=k.id AND i.rasadnikId=r.id AND i.prodavnicaId=? AND i.odbijenaPrihvacenaNaCekanju=? " +
                                                "GROUP BY i.stize");
                    ps.setInt(1, prodavnicaId);
                    ps.setInt(2, odbijenaPrihvacenaNaCekanju);
                    ResultSet rs = ps.executeQuery();
                    
                    while (rs.next()){
                        Narudzbina n = new Narudzbina();
                        
                        n.setIme(rs.getString("kime"));
                        n.setKomada(rs.getInt("komada"));
                        n.setKorisnikId(rs.getInt("korisnikId"));
                        n.setMestoRasadnika(rs.getString("rmesto"));
                        n.setPrezime(rs.getString("prezime"));
                        n.setProizvodi(util.dao.IsporukaProizvodaDAO.dohvatiProizvodeZaTimestampNarudzbine(rs.getTimestamp("stize")));
                        n.setRasadnikId(rs.getInt("rasadnikId"));
                        n.setStize(rs.getTimestamp("stize"));
                        n.setProdavnicaId(rs.getInt("prodavnicaId"));
                        n.setOdbijenaPrihvacenaNaCekanju(rs.getInt("odbijenaPrihvacenaNaCekanju"));
                        
                        narudzbine.add(n);
                    }
                    return narudzbine;
                    
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
        return null;
            
    }
    
    public static void izbrisiNarudzbinu(Narudzbina n){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
            
            try {
                conn.setAutoCommit(false);             
                ps = conn.prepareStatement("UPDATE isporukaproizvoda SET odbijenaPrihvacenaNaCekanju=0 WHERE rasadnikId=? AND prodavnicaId=? AND stize=? AND odbijenaPrihvacenaNaCekanju=? ");
                ps.setInt(1, n.getRasadnikId());
                ps.setInt(2, n.getProdavnicaId());
                ps.setTimestamp(3, n.getStize());
                ps.setInt(4, n.getOdbijenaPrihvacenaNaCekanju());    
                
                ps2 = conn.prepareStatement("INSERT INTO onlineprodavnica (prodavnicaId,proizvodId,kolicina) " +
                                            "SELECT prodavnicaId, proizvodId, kolicina " +
                                            "FROM isporukaproizvoda i " +
                                            "WHERE i.stize = ? " +
                                            "AND i.prodavnicaId=? " +
                                            "AND i.odbijenaPrihvacenaNaCekanju=0 " +
                                            "ON DUPLICATE KEY UPDATE onlineprodavnica.kolicina = onlineprodavnica.kolicina + i.kolicina");
                
                ps2.setTimestamp(1, n.getStize());
                ps2.setInt(2, n.getProdavnicaId());

                ps.executeUpdate();
                ps2.executeUpdate();
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                if(ps!=null)
                    ps.close();
                if(ps2!=null)
                    ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    public static void potvrdiNaCekanjuNarudzbinu(Narudzbina n, int trajanjeDostave, int odbijenaPrihvacenaNaCekanju){
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
            
            try {
                conn.setAutoCommit(false);
                ps = conn.prepareStatement("UPDATE isporukaproizvoda " +
                                            "SET stize=DATE_ADD(NOW(), INTERVAL ? SECOND), " +
                                            "	odbijenaPrihvacenaNaCekanju=? " +
                                            "WHERE stize=? " +
                                            "AND prodavnicaId=? " +
                                            "AND rasadnikId=? ");

                ps.setInt(1, trajanjeDostave);
                ps.setInt(2, odbijenaPrihvacenaNaCekanju);
                ps.setTimestamp(3, n.getStize());
                ps.setInt(4, n.getProdavnicaId());
                ps.setInt(5, n.getRasadnikId());
                ps.executeUpdate();
                
                if (odbijenaPrihvacenaNaCekanju!=2){
                    ps2 = conn.prepareStatement("UPDATE korisnici SET brDostavljaca=brDostavljaca-1 WHERE id=?");
                    ps2.setInt(1, n.getProdavnicaId());
                    ps2.executeUpdate();
                }
                    
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
            try {
                conn.setAutoCommit(true);
                if(ps!=null)
                    ps.close();
                if(ps2!=null)
                    ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            util.DB.getInstance().putConnection(conn);
        }
    }
    
    public static HashMap<String, Integer> dohvatiBrojNarudzbinaUZadnjih30Dana(int prodavnicaId){
        HashMap<String,Integer> mapa = new HashMap<>();
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            
            try {             
                ps = conn.prepareStatement("SELECT DATE(i.stize) as datum, COUNT(*) as count " +
                                            "FROM isporukaproizvoda as i " +
                                            "WHERE i.stize BETWEEN NOW() - INTERVAL 30 DAY AND NOW() " +
                                            "AND prodavnicaId=? " +
                                            "GROUP BY DATE(i.stize) ");
                ps.setInt(1, prodavnicaId);
                ResultSet rs = ps.executeQuery();
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  


                while (rs.next()){
                    mapa.put((dateFormat.format(new Date (rs.getDate("datum").getTime()))), rs.getInt("count"));

                }
                return mapa;
                    
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
        return null;
    }
    
    public static ArrayList<Narudzbina> dohvatiNarudzbineZaIzvestaj(int prodavnicaId){
        ArrayList<Narudzbina> narudzbine = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        
        PreparedStatement ps = null;
            
            try {
                    ps = conn.prepareStatement("SELECT *, sum(i.kolicina) as komada, k.ime as kime, r.mesto as rmesto, sum(p.cena * i.kolicina) as pcena " +
                                                "FROM isporukaproizvoda i, rasadnici r, korisnici k, proizvodi p " +
                                                "WHERE r.korisnikId=k.id AND i.rasadnikId=r.id AND i.proizvodId=p.id " +
                                                "AND i.stize BETWEEN NOW() - INTERVAL 30 DAY AND NOW() " +
                                                "AND i.prodavnicaId=? " +
                                                "GROUP BY i.stize");
                    ps.setInt(1, prodavnicaId);
                    ResultSet rs = ps.executeQuery();
                    
                    while (rs.next()){
                        Narudzbina n = new Narudzbina();
                        
                        n.setIme(rs.getString("kime"));
                        n.setKomada(rs.getInt("komada"));
                        n.setKorisnikId(rs.getInt("korisnikId"));
                        n.setMestoRasadnika(rs.getString("rmesto"));
                        n.setPrezime(rs.getString("prezime"));
                        n.setProizvodi(util.dao.IsporukaProizvodaDAO.dohvatiProizvodeZaTimestampNarudzbine(rs.getTimestamp("stize")));
                        n.setRasadnikId(rs.getInt("rasadnikId"));
                        n.setStize(rs.getTimestamp("stize"));
                        n.setProdavnicaId(rs.getInt("prodavnicaId"));
                        n.setOdbijenaPrihvacenaNaCekanju(rs.getInt("odbijenaPrihvacenaNaCekanju"));
                        n.setUkupnaVrednost(rs.getInt("pcena"));
                        
                        narudzbine.add(n);
                    }
                    return narudzbine;
                    
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
        return null;
            
    }
    
    
    public static ArrayList<Proizvod> dohvatiProizvodeZaTimestampNarudzbine(Timestamp proizvodTimestamp){
        ArrayList<Proizvod> proizvodi = new ArrayList();
        Connection conn = util.DB.getInstance().getConnection();
        PreparedStatement ps = null;
        
        //UPDATE ISPORUCENE PROIZVODE
        updateIsporuceneProizvode();
        
        try {
            ps = conn.prepareStatement("SELECT * FROM isporukaproizvoda i, proizvodi p WHERE i.stize=? AND i.proizvodId=p.id");
            ps.setTimestamp(1, proizvodTimestamp);
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
    
}
