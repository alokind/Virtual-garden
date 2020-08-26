/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class Proizvod {
    private int id;
    private String naziv;
    private String proizvodjac;
    private int trajanje;
    private int rasadnikId;
    private int jeSadnica;
    private int kolicina;
    private Timestamp stize;
    private int mozeDaSePonisti;
    private int prodavnicaId;
    private double prosecnaOcena;
    private String prodavnicaIme;
    private int kolikoKomada;
    private int onlineProdavnicaid;
    private int isporukaProizvodaId;
    private int brojOcena;
    private int cena;

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
    
    

    public int getBrojOcena() {
        return brojOcena;
    }

    public void setBrojOcena(int brojOcena) {
        this.brojOcena = brojOcena;
    }
    
    

    public int getIsporukaProizvodaId() {
        return isporukaProizvodaId;
    }

    public void setIsporukaProizvodaId(int isporukaProizvodaId) {
        this.isporukaProizvodaId = isporukaProizvodaId;
    }
    
    

    public int getOnlineProdavnicaid() {
        return onlineProdavnicaid;
    }

    public void setOnlineProdavnicaid(int onlineProdavnicaid) {
        this.onlineProdavnicaid = onlineProdavnicaid;
    }
    
    

    public int getKolikoKomada() {
        return kolikoKomada;
    }

    public void setKolikoKomada(int kolikoKomada) {
        this.kolikoKomada = kolikoKomada;
    }
    
    
    

    public String getProdavnicaIme() {
        return prodavnicaIme;
    }

    public void setProdavnicaIme(String prodavnicaIme) {
        this.prodavnicaIme = prodavnicaIme;
    }
    
    

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
    
    
    

    public int getProdavnicaId() {
        return prodavnicaId;
    }

    public void setProdavnicaId(int prodavnicaId) {
        this.prodavnicaId = prodavnicaId;
    }

    
    
    
    
    public int getMozeDaSePonisti() {
        return mozeDaSePonisti;
    }

    public void setMozeDaSePonisti(int mozeDaSePonisti) {
        this.mozeDaSePonisti = mozeDaSePonisti;
    }
    
    

    public Timestamp getStize() {
        return stize;
    }

    public void setStize(Timestamp stize) {
        this.stize = stize;
    }
    
    

    public Proizvod() {
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public String toString() {
        String format = naziv + " (" + proizvodjac + ")";
        format += this.jeSadnica==1?", raste ":", ubrzava za ";
        format += trajanje + " dana [" + kolicina + "kom]";
        return format;
    }

    public int getRasadnikId() {
        return rasadnikId;
    }

    public void setRasadnikId(int rasadnikId) {
        this.rasadnikId = rasadnikId;
    }    

    public int getJeSadnica() {
        return jeSadnica;
    }

    public void setJeSadnica(int jeSadnica) {
        this.jeSadnica = jeSadnica;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }
    
    
}
