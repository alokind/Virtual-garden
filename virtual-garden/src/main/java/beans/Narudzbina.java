/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Narudzbina {
    private int komada;
    private int rasadnikId;
    private ArrayList<Proizvod> proizvodi;
    private String ime;
    private String prezime;
    private String mestoRasadnika;
    private int korisnikId;
    private Timestamp stize;
    private int prodavnicaId;
    private int odbijenaPrihvacenaNaCekanju;
    private int ukupnaVrednost;

    public int getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(int ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }
    
    

    public int getOdbijenaPrihvacenaNaCekanju() {
        return odbijenaPrihvacenaNaCekanju;
    }

    public void setOdbijenaPrihvacenaNaCekanju(int odbijenaPrihvacenaNaCekanju) {
        this.odbijenaPrihvacenaNaCekanju = odbijenaPrihvacenaNaCekanju;
    }
    
    

    public int getProdavnicaId() {
        return prodavnicaId;
    }

    public void setProdavnicaId(int prodavnicaId) {
        this.prodavnicaId = prodavnicaId;
    }
    
    

    public Timestamp getStize() {
        return stize;
    }

    public void setStize(Timestamp stize) {
        this.stize = stize;
    }

    public int getKomada() {
        return komada;
    }

    public void setKomada(int komada) {
        this.komada = komada;
    }

    public int getRasadnikId() {
        return rasadnikId;
    }

    public void setRasadnikId(int rasadnikId) {
        this.rasadnikId = rasadnikId;
    }

    public ArrayList<Proizvod> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(ArrayList<Proizvod> proizvodi) {
        this.proizvodi = proizvodi;
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

    public String getMestoRasadnika() {
        return mestoRasadnika;
    }

    public void setMestoRasadnika(String mestoRasadnika) {
        this.mestoRasadnika = mestoRasadnika;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }
    
    
    
    
    
    
}
