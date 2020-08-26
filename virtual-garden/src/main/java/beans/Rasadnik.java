/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class Rasadnik {
    private int id; 
    private String ime;
    private String mesto;
    private int brSadnica;
    private int duzina; 
    private int sirina;
    private int kolVode; 
    private double temp; 
    private int korisnikId;
    private Timestamp updated;

    public Rasadnik() {
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
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

    public int getBrSadnica() {
        return brSadnica;
    }

    public void setBrSadnica(int brSadnica) {
        this.brSadnica = brSadnica;
    }

    public int getDuzina() {
        return duzina;
    }

    public void setDuzina(int duzina) {
        this.duzina = duzina;
    }

    public int getSirina() {
        return sirina;
    }

    public void setSirina(int sirina) {
        this.sirina = sirina;
    }



    public int getKolVode() {
        return kolVode;
    }

    public void setKolVode(int kolVode) {
        this.kolVode = kolVode;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }
    
    
    
    
}
