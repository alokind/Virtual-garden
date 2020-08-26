/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Ocena;
import beans.Proizvod;
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
public class SviProizvodiPreduzecaController {
    
    private Korisnik korisnik;
    private int kolikoKomada;
    
    private ArrayList<Ocena> ocene = new ArrayList();
    
    private Proizvod selectedProizvod;
    
    private ArrayList<Proizvod> prodavnica = new ArrayList<>();
    private ArrayList<Ocena> prosecneOceneZaProizvode = new ArrayList<>();
    
    
    
            
    public ArrayList<Integer> kolicinaZaProizvod(Proizvod proizvod) {
        ArrayList<Integer> komada = new ArrayList<Integer>();
        for (int i = 1; i <= proizvod.getKolicina(); i++) {
            komada.add(i);
        }
        return komada;
    }
    
    public void dohvatiProdavnicuIProsecneOcene(){
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        
        prodavnica = util.dao.OnlineProdavnicaDAO.dohvatiCeluProdavnicu();
        prodavnica.removeIf(obj -> obj.getProdavnicaId()!=korisnik.getId());
        
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
        
    }
    
    public void dohvatiKomentare(){
        ocene = util.dao.OcenaDAO.dohvatiOcenePoProizvodId(selectedProizvod.getId());
    }
    
        
    public void izbrisiProizvodIzPordavnice(Proizvod p){
        util.dao.OnlineProdavnicaDAO.izbrisiProizvodIzProdavnice(p.getId(), korisnik.getId());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno ste uklonili proizvod iz prodavnice.","Uspešno ste uklonili proizvod iz prodavnice.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public int getKolikoKomada() {
        return kolikoKomada;
    }

    public void setKolikoKomada(int kolikoKomada) {
        this.kolikoKomada = kolikoKomada;
    }

    public ArrayList<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(ArrayList<Ocena> ocene) {
        this.ocene = ocene;
    }

    public ArrayList<Proizvod> getProdavnica() {
        return prodavnica;
    }

    public void setProdavnica(ArrayList<Proizvod> prodavnica) {
        this.prodavnica = prodavnica;
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


    public ArrayList<Ocena> getProsecneOceneZaProizvode() {
        return prosecneOceneZaProizvode;
    }

    public void setProsecneOceneZaProizvode(ArrayList<Ocena> prosecneOceneZaProizvode) {
        this.prosecneOceneZaProizvode = prosecneOceneZaProizvode;
    }
    
    

}
