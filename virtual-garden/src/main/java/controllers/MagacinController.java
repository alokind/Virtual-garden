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
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author User
 */
@ManagedBean
@ViewScoped
public class MagacinController implements Serializable {

    private Rasadnik rasadnik;
    private Korisnik korisnik;
    private ArrayList<Proizvod> filtriraniProizvodi;
    private ArrayList<Proizvod> magacin;
    
    private Proizvod selectedProizvod;
    
    public void dohvatiRasadnikKorisnikaIMagacin(){
        HttpSession session = util.SessionUtils.getSession();
        rasadnik = (Rasadnik) session.getAttribute("rasadnik");
        korisnik = (Korisnik) session.getAttribute("korisnik");
        magacin = util.dao.ProizvodDAO.dohvatiProizvodeUMagacinuZaRasadnikId(rasadnik.getId());
        magacin.addAll(util.dao.ProizvodDAO.dohvatiProizvodeNaIsporuciZaRasadnikId(rasadnik.getId()));

    }

    public Proizvod getSelectedProizvod() {
        return selectedProizvod;
    }

    public void setSelectedProizvod(Proizvod selectedProizvod) {
        this.selectedProizvod = selectedProizvod;
    }

    public ArrayList<Proizvod> getFiltriraniProizvodi() {
        return filtriraniProizvodi;
    }

    public void setFiltriraniProizvodi(ArrayList<Proizvod> filtriraniProizvodi) {
        this.filtriraniProizvodi = filtriraniProizvodi;
    }


    
    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public ArrayList<Proizvod> getMagacin() {
        return magacin;
    }

    public void setMagacin(ArrayList<Proizvod> magacin) {
        this.magacin = magacin;
    }
    
    
    public void onRowSelect(SelectEvent<Proizvod> event) {
        FacesMessage msg = new FacesMessage("Odabran proizvod", event.getObject().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void otkaziNarudzbinu(Proizvod p){
        util.dao.ProizvodDAO.izbrisiProizvodIzIsporuke(p, rasadnik, korisnik.getId());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno ste otkazali narudzbinu\n"+p.toString(),"Uspesno ste otkazali narudzbinu"+p.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
    
}
