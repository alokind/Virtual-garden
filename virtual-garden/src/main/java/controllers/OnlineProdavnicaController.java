/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Ocena;
import beans.Proizvod;
import beans.Rasadnik;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class OnlineProdavnicaController {
    
    Korisnik korisnik;
    Rasadnik rasadnik;
    int kolikoKomada;
    
    private ArrayList<Ocena> ocene = new ArrayList();
    
    Proizvod selectedProizvod;
    boolean mozeDaKomentarise;
    String noviKomentar;
    int novaOcena;
    
    private ArrayList<Proizvod> prodavnica = new ArrayList<>();
    private ArrayList<Proizvod> filtriranaProdavnica = new ArrayList<>();
    private ArrayList<Rasadnik> rasadnici;
    ArrayList<Ocena> prosecneOceneZaProizvode;
    
    private int rasadnikId;
    
    
            
    public ArrayList<Integer> kolicinaZaProizvod(Proizvod proizvod) {
        ArrayList<Integer> komada = new ArrayList<Integer>();
        for (int i = 1; i <= proizvod.getKolicina(); i++) {
            komada.add(i);
        }
        return komada;
    }
    
    public void kupi(){
        System.out.println(util.dao.OnlineProdavnicaDAO.kupi(filtriranaProdavnica, korisnik, rasadnikId));
        for (Proizvod proizvod : filtriranaProdavnica) {
            if (proizvod.getKolikoKomada()>=1){
                System.out.println(proizvod.getOnlineProdavnicaid() );
                proizvod.setKolicina(proizvod.getKolicina()-proizvod.getKolikoKomada());
                System.out.println(proizvod);
                System.out.println(proizvod.getKolikoKomada());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešna kupovina\n"+proizvod.toString()+" komada - "+ proizvod.getKolikoKomada()+"\n", 
                                                                                    "Uspešna kupovina\n"+proizvod.toString()+" komada - "+ proizvod.getKolikoKomada()+"\n");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
        dohvatiProdavnicuIProsecneOcene();
        
    }
    
    public void dohvatiProdavnicuIProsecneOcene(){
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        rasadnici = util.dao.RasadnikDAO.dohvatiRasadnikeZaKorisnika(korisnik.getId());
        
        prodavnica = util.dao.OnlineProdavnicaDAO.dohvatiCeluProdavnicu();
        filtriranaProdavnica = util.dao.OnlineProdavnicaDAO.dohvatiCeluProdavnicu();
        prosecneOceneZaProizvode = util.dao.OcenaDAO.dohvatiProsecneOceneZaProizvode();
        HashMap<Integer, Double> oceneMapa = new HashMap<Integer,Double>();
        for (Ocena ocena : prosecneOceneZaProizvode) {
            oceneMapa.put(ocena.getProizvodId(), ocena.getProsecnaOcena());
        }
        for (Proizvod proizvod : prodavnica) {
            if (oceneMapa.get(proizvod.getId())!=null)
                proizvod.setProsecnaOcena(oceneMapa.get(proizvod.getId()));
            else
                proizvod.setProsecnaOcena(0);
            
        }
        for (Proizvod proizvod : filtriranaProdavnica) {
            if (oceneMapa.get(proizvod.getId())!=null)
                proizvod.setProsecnaOcena(oceneMapa.get(proizvod.getId()));
            else
                proizvod.setProsecnaOcena(0);
        }
        
    }
    
    public void postaviKolikoKomada(int onlinePordavnicaId){
        for (Proizvod proizvod : filtriranaProdavnica) {
            if (proizvod.getOnlineProdavnicaid()==onlinePordavnicaId){
                proizvod.setKolikoKomada(kolikoKomada);
            }
        }
        for (Proizvod proizvod : prodavnica) {
            if (proizvod.getOnlineProdavnicaid()==onlinePordavnicaId){
                proizvod.setKolikoKomada(kolikoKomada);
            }
        }
    }

    public boolean isMozeDaKomentarise() {
        return mozeDaKomentarise;
    }

    public void setMozeDaKomentarise(boolean mozeDaKomentarise) {
        this.mozeDaKomentarise = mozeDaKomentarise;
    }
    
    public void dohvatiKomentare(){
        ocene = util.dao.OcenaDAO.dohvatiOcenePoProizvodId(selectedProizvod.getId());
        mozeDaKomentarise = util.dao.OcenaDAO.mozeDaKomentarise(selectedProizvod.getId(), korisnik.getId());
        int jedan = 1;
    }
    
    public void unesiOcenu(){
        util.dao.OcenaDAO.dodajKomentar(noviKomentar, novaOcena, selectedProizvod.getId(), korisnik.getId());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Osvežite stranicu da biste videli svoju ocenu", 
                                                                            "Osvežite stranicu da biste videli svoju ocenu");
        FacesContext.getCurrentInstance().addMessage(null, message);
        dohvatiProdavnicuIProsecneOcene();
    }

    public int getKolikoKomada() {
        return kolikoKomada;
    }

    public void setKolikoKomada(int kolikoKomada) {
        this.kolikoKomada = kolikoKomada;
    }

    public int getRasadnikId() {
        return rasadnikId;
    }

    public void setRasadnikId(int rasadnikId) {
        this.rasadnikId = rasadnikId;
    }

    public String getNoviKomentar() {
        return noviKomentar;
    }

    public void setNoviKomentar(String noviKomentar) {
        this.noviKomentar = noviKomentar;
    }

    public int getNovaOcena() {
        return novaOcena;
    }

    public void setNovaOcena(int novaOcena) {
        this.novaOcena = novaOcena;
    }
    
    

    public ArrayList<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(ArrayList<Ocena> ocene) {
        this.ocene = ocene;
    }

    public ArrayList<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(ArrayList<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }

    public ArrayList<Proizvod> getProdavnica() {
        return prodavnica;
    }

    public void setProdavnica(ArrayList<Proizvod> prodavnica) {
        this.prodavnica = prodavnica;
    }

    public ArrayList<Proizvod> getFiltriranaProdavnica() {
        return filtriranaProdavnica;
    }

    public void setFiltriranaProdavnica(ArrayList<Proizvod> filtriranaProdavnica) {
        this.filtriranaProdavnica = filtriranaProdavnica;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Proizvod getSelectedProizvod() {
        return selectedProizvod;
    }

    public void setSelectedProizvod(Proizvod selectedProizvod) {
        this.selectedProizvod = selectedProizvod;
    }
    
    

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }
    
    
    
    
    
}
