/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Rasadnik;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.internet.AddressException;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class PoljoprivrednikController {
    private Korisnik korisnik;
    private ArrayList<Rasadnik> rasadnici = new ArrayList<>();
    
    private String naziv;
    private String mesto;
    private int sirina;
    private int duzina;
    
    private int daLiJePoslatMejl = 0;
    
    
            
    public void dohvatiKorisnika(){
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
    }
    
    public void dohvatiRasadnike(){
        util.dao.RasadnikDAO.updateStatus(korisnik.getId());
        rasadnici = util.dao.RasadnikDAO.dohvatiRasadnikeZaKorisnika(korisnik.getId());
        String poruka = "";
        String porukaMejl="";
        for (Rasadnik rasadnik : rasadnici) {
            if(rasadnik.getTemp()<12 || rasadnik.getKolVode()<75){
                poruka += "Rasadnik '" + rasadnik.getIme()+"' zahteva održavanje.";
                poruka += "<br>";
                porukaMejl += "Rasadnik '" + rasadnik.getIme()+"' zahteva održavanje.\n";
            }
        }
        
        if (!("".equals(poruka)) && (daLiJePoslatMejl==0)){
            daLiJePoslatMejl = 1;
            posaljiMejl(porukaMejl, korisnik.getMejl());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, poruka, poruka);
            FacesContext.getCurrentInstance().addMessage("message", message);
        }
    }
    
    public void detaljiRasadnika(int rasadnikId){
        HttpSession session = util.SessionUtils.getSession();
        Rasadnik rasadnik = util.dao.RasadnikDAO.dohvatiRasadnikPoId(rasadnikId);
        session.setAttribute("rasadnik", rasadnik);
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().redirect("detaljiRasadnika.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void dodajRasadnik(){
        util.dao.RasadnikDAO.dodajRasadnik(korisnik.getId(), naziv, mesto, sirina, duzina);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno ste dodali novi rasadnik.", 
                                                                            "Uspesno ste dodali novi rasadnik.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void posaljiMejl(String poruka, String kome) {

    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public int getSirina() {
        return sirina;
    }

    public void setSirina(int sirina) {
        this.sirina = sirina;
    }

    public int getDuzina() {
        return duzina;
    }

    public void setDuzina(int duzina) {
        this.duzina = duzina;
    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public ArrayList<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(ArrayList<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }
    
    
    
}
