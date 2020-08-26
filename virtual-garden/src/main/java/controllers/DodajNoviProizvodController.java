/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class DodajNoviProizvodController {
    private Korisnik korisnik;
    
    private String naziv;
    private String proizvodjac;
    private String tip;
    private int cena;
    private int kolicina;
    private int trajanje;
    
    
    private UploadedFile file;

    
    public void dohvatiKorisnika(){
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
    }
    
    public void potvrdi(){
        int jeSadnica=0;
        if("Sadnica".equals(tip))
            jeSadnica=1;
        
        util.dao.OnlineProdavnicaDAO.dodajProizvodUProizvodeIProdavnicu(naziv, proizvodjac, jeSadnica, trajanje, cena, korisnik.getId(), kolicina);
    }
    
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    
    
    public void upload() {
        if (file != null) {
            try {
                Scanner s = new Scanner(file.getInputStream()).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                
                System.out.println(result);                
                FacesMessage message = new FacesMessage("Uspešno učitavanje fajla ", file.getFileName());
                FacesContext.getCurrentInstance().addMessage("growl", message);
                
                JsonParser jp = new JsonParser(); //from gson
                JsonElement root = jp.parse(result); //Convert the input stream to a json element
                JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
                
                JsonArray proizvodi = rootobj
                        .getAsJsonArray("proizvodi");
                
                for (JsonElement proizvod : proizvodi) {
                    String naziv = proizvod.getAsJsonObject().get("naziv").getAsString();
                    String proizvodjac = proizvod.getAsJsonObject().get("proizvodjac").getAsString();
                    int jeSadnica = proizvod.getAsJsonObject().get("jeSadnica").getAsInt();
                    int trajanje = proizvod.getAsJsonObject().get("trajanje").getAsInt();
                    int kolicina = proizvod.getAsJsonObject().get("kolicina").getAsInt();
                    int cena = proizvod.getAsJsonObject().get("cena").getAsInt();
                    
                    util.dao.OnlineProdavnicaDAO.dodajProizvodUProizvodeIProdavnicu(naziv, proizvodjac, jeSadnica, trajanje, cena, korisnik.getId(), kolicina);
                }
                
                
            } catch (IOException ex) {
                Logger.getLogger(DodajNoviProizvodController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Uspešno učitavanje fajla ", file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    
    
    
    
    
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
    
    
    
    
    
    
    
    
    
}
