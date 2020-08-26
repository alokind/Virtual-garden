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
public class Ocena {
    private int idocena;
    private int proizvodId;
    private String komentar;
    private int ocena;
    private double prosecnaOcena;
    private int korisnikId;
    private String korime;
    private int brOcena;

    public int getBrOcena() {
        return brOcena;
    }

    public void setBrOcena(int brOcena) {
        this.brOcena = brOcena;
    }
    
    

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }
    
    

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }
    
    

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
    
    
    
    public int getIdocena() {
        return idocena;
    }

    public void setIdocena(int idocena) {
        this.idocena = idocena;
    }

    public int getProizvodId() {
        return proizvodId;
    }

    public void setProizvodId(int proizvodId) {
        this.proizvodId = proizvodId;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }
    
    
    
    

    
    
    
}
