/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Proizvod;
import beans.Rasadnik;
import beans.Sadnica;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class RasadnikController {
    Korisnik korisnik;
    Rasadnik rasadnik;
    Sadnica selectedSadnica;
    String selectedSadnicaPolje;
    String selectedProizvodString;
    ArrayList<Sadnica> sadnice = new ArrayList<>();
    ArrayList<Proizvod> magacin = new ArrayList<>();
    ArrayList<Proizvod> preparatiUMagacinu = new ArrayList<>();
    ArrayList<Proizvod> sadniceUMagacinu = new ArrayList<>();
    
    boolean zaVadjenje = false;
    
    Proizvod selectedProzivod;

    public boolean isZaVadjenje() {
        return zaVadjenje;
    }

    public void setZaVadjenje(boolean zaVadjenje) {
        this.zaVadjenje = zaVadjenje;
    }
    
    
    

    public String getSelectedSadnicaPolje() {
        return selectedSadnicaPolje;
    }

    public void setSelectedSadnicaPolje(String selectedSadnicaPolje) {
        this.selectedSadnicaPolje = selectedSadnicaPolje;
    }

    public String getSelectedProizvodString() {
        return selectedProizvodString;
    }

    public void setSelectedProizvodString(String selectedProizvodString) {
        this.selectedProizvodString = selectedProizvodString;
    }
    
    
    

    public ArrayList<Proizvod> getMagacin() {
        return magacin;
    }

    public void setMagacin(ArrayList<Proizvod> magacin) {
        this.magacin = magacin;
    }

    public ArrayList<Proizvod> getPreparatiUMagacinu() {
        return preparatiUMagacinu;
    }

    public void setPreparatiUMagacinu(ArrayList<Proizvod> preparatiUMagacinu) {
        this.preparatiUMagacinu = preparatiUMagacinu;
    }

    public ArrayList<Proizvod> getSadniceUMagacinu() {
        return sadniceUMagacinu;
    }

    public void setSadniceUMagacinu(ArrayList<Proizvod> sadniceUMagacinu) {
        this.sadniceUMagacinu = sadniceUMagacinu;
    }

    public Proizvod getSelectedProzivod() {
        return selectedProzivod;
    }

    public void setSelectedProzivod(Proizvod selectedProzivod) {
        this.selectedProzivod = selectedProzivod;
    }


    public Sadnica getSelectedSadnica() {
        return selectedSadnica;
    }

    public void setSelectedSadnica(Sadnica selectedSadnica) {
        this.selectedSadnica = selectedSadnica;
    }
    
    public void izaberiSadnicu(Sadnica s){
        selectedSadnica = s;
        if (s.getBiceIzvadjena()==1) {
            zaVadjenje = true;
            return;
        }
        if(s.getDatum()==null)
            zaVadjenje = false;
        else
            zaVadjenje = progresSadnice(s)==100;
    
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

    public ArrayList<Sadnica> getSadnice() {
        return sadnice;
    }

    public void setSadnice(ArrayList<Sadnica> sadnice) {
        this.sadnice = sadnice;
    }
    
    public void onSadnicaChange() {
        if(selectedSadnica !=null && selectedSadnica.getNaziv()!=null)
            magacin = preparatiUMagacinu;
        else
            magacin = sadniceUMagacinu;
    }
    
    public  ArrayList<Sadnica> popunjeneSadnice(ArrayList<Sadnica> sadnice){
        ArrayList<Sadnica> popunjeneSadnice =  new ArrayList<>();
        for (int i = 1; i<=rasadnik.getDuzina()*rasadnik.getSirina(); i++){
            Sadnica s = new Sadnica();
            s.setPozicija(i);
            s.setId(-1);
            popunjeneSadnice.add(s);
        }
        for (Sadnica sadnica : sadnice) {
            popunjeneSadnice.set(sadnica.getPozicija()-1, sadnica);
        }
        return popunjeneSadnice;
    }
    
    public int progresSadnice(Sadnica s){
        if (s.getDatum()==null) return -1;
        long daniDoVadjenja = ChronoUnit.DAYS.between(new Date().toInstant(), s.getDatum().toInstant());
        int procenat = (int) ((((s.getTrajanje() - daniDoVadjenja)*1.0)/s.getTrajanje())*100);
        if (procenat>=100) 
            return 100;
        else 
            return procenat;
    }
    
    public void postaviBiceIzvadjena(){
        util.dao.SadniceDAO.postaviBiceIzvadjena(selectedSadnica.getId());
    }

    
    public void menjajVodu(int zaKoliko){
        util.dao.RasadnikDAO.smanjiVoduIliTemp(zaKoliko, "kolVode", rasadnik.getId());
        rasadnik.setKolVode(rasadnik.getKolVode()+zaKoliko);
        HttpSession session = util.SessionUtils.getSession();
        session.setAttribute("rasadnik", util.dao.RasadnikDAO.dohvatiRasadnikPoId(rasadnik.getId()));
    }
    
    public void menjajTemp(int zaKoliko){
        util.dao.RasadnikDAO.smanjiVoduIliTemp(zaKoliko, "temp", rasadnik.getId());
        rasadnik.setTemp(rasadnik.getTemp()+zaKoliko);
        HttpSession session = util.SessionUtils.getSession();
        session.setAttribute("rasadnik", util.dao.RasadnikDAO.dohvatiRasadnikPoId(rasadnik.getId()));
    }
            
    public void dohvatiRasadnikISadnice(){
        HttpSession session = util.SessionUtils.getSession();
        rasadnik = (Rasadnik) session.getAttribute("rasadnik");
        korisnik = (Korisnik) session.getAttribute("korisnik");
        sadnice = util.dao.SadniceDAO.dohvatiSadniceZaRasadnikId(rasadnik.getId());
        sadnice = popunjeneSadnice(sadnice);
    }
    
    public void dohvatiProizvodeUMagacinu(){
        preparatiUMagacinu = util.dao.ProizvodDAO.dohvatiProizvodeUMagacinuZaRasadnikId(rasadnik.getId());
        sadniceUMagacinu = util.dao.ProizvodDAO.dohvatiProizvodeUMagacinuZaRasadnikId(rasadnik.getId());
        
        preparatiUMagacinu.removeIf(s -> s.getJeSadnica()==1);
        sadniceUMagacinu.removeIf(s -> s.getJeSadnica()!=1);
    }
    
    public void primeniIzMagacina(){
        int proizvodId = Integer.parseInt(selectedProizvodString);
        System.out.println(proizvodId);
        selectedProzivod = util.dao.ProizvodDAO.dohvatiProizvodPoId(proizvodId);
        if(selectedProzivod.getJeSadnica()==1){
            util.dao.RasadnikDAO.dodajUSadniceIUkloniIzMagacina(selectedSadnica, rasadnik, selectedProzivod);
            dohvatiRasadnikISadnice();
            dohvatiProizvodeUMagacinu();
        }else{
            util.dao.RasadnikDAO.primeniPreparatIUkloniIzMagacina(selectedSadnica, rasadnik, selectedProzivod);
            dohvatiRasadnikISadnice();
            dohvatiProizvodeUMagacinu();
        }
    }
    
    
    
 
    public void onCountryChange() {
        if(selectedSadnicaPolje !=null && !selectedSadnicaPolje.equals("")){
            int sadnicaPoRedu = Integer.parseInt(selectedSadnicaPolje)-1;
            selectedSadnica = sadnice.get(sadnicaPoRedu);
            if (sadnice.get(sadnicaPoRedu).getId()==-1)
                magacin = sadniceUMagacinu;
            else
                magacin = preparatiUMagacinu;
        }
        else
            magacin = new ArrayList<>();
    }
     
    public void displayLocation() {
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, selectedProizvodString,selectedProizvodString); 
             
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    
    
}
