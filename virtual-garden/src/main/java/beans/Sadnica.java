/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author User
 */
public class Sadnica {
    private int id;
    private String naziv;
    private Date datum;
    private int pozicija;
    private String proizvodjac;
    private int trajanje;
    private int rasadnikId;
    private int korisnikId;
    private int biceIzvadjena;

    public int getBiceIzvadjena() {
        return biceIzvadjena;
    }

    public void setBiceIzvadjena(int biceIzvadjena) {
        this.biceIzvadjena = biceIzvadjena;
    }
    
    

    public Sadnica() {
    }

    @Override
    public String toString() {
        return pozicija + "";
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getPozicija() {
        return pozicija;
    }

    public void setPozicija(int pozicija) {
        this.pozicija = pozicija;
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

    public int getRasadnikId() {
        return rasadnikId;
    }

    public void setRasadnikId(int rasadnikId) {
        this.rasadnikId = rasadnikId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }
    
    
    
    
}
