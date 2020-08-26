/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class AdminController {
    
    private ArrayList<Korisnik> korisnici = new ArrayList<>();
    private Korisnik korisnik;
    
    
    public void dohvatiKorisnike(){
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        
        korisnici=util.dao.KorisnikDAO.dohvatiSveKorisnike();
    }
        
    public void onRowEdit(RowEditEvent<Korisnik> event) {
        util.dao.KorisnikDAO.updateKorisnik(event.getObject());
        dohvatiKorisnike();
        FacesMessage msg = new FacesMessage("Izmenjen korisnik", event.getObject().getKorime());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent<Korisnik> event) {
        FacesMessage msg = new FacesMessage("Izmena poništena", event.getObject().getKorime());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void ukloniKorisnika(int korisnikId){
        util.dao.KorisnikDAO.ukloniKorisnika(korisnikId);
        dohvatiKorisnike();
        FacesMessage msg = new FacesMessage("Korisnik je uklonjen");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
    
    
    
    
    
    public ArrayList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ArrayList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    
    
    
    
    
}
