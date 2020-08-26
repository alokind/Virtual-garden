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
public class Korisnik {
    private int id;
    private String ime;
    private String prezime;
    private String korime;
    private String lozinka;
    private Date datum;
    private String mesto;
    private String telefon;
    private String mejl;
    private String tip;
    private int jeOdobren;

    public Korisnik(String ime, String prezime, String korime, String lozinka, Date datum, String mesto, String telefon, String mejl, String tip) {
        this.ime = ime;
        this.prezime = prezime;
        this.korime = korime;
        this.lozinka = lozinka;
        this.datum = datum;
        this.mesto = mesto;
        this.telefon = telefon;
        this.mejl = mejl;
        this.tip = tip;
    }

    public Korisnik(String ime, String korime, String lozinka, Date datum, String mesto, String mejl, String tip) {
        this.ime = ime;
        this.korime = korime;
        this.lozinka = lozinka;
        this.datum = datum;
        this.mesto = mesto;
        this.mejl = mejl;
        this.tip = tip;
    }
    
    public Korisnik() {
    }

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", korime=" + korime + ", lozinka=" + lozinka + ", datum=" + datum + ", mesto=" + mesto + ", telefon=" + telefon + ", mejl=" + mejl + ", tip=" + tip + ", jeOdobren=" + jeOdobren + '}';
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getJeOdobren() {
        return jeOdobren;
    }

    public void setJeOdobren(int jeOdobren) {
        this.jeOdobren = jeOdobren;
    }
    
    

}
