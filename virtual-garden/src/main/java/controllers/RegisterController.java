/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class RegisterController {
    
    private Korisnik korisnik = new Korisnik();
    private String potvrdaLozinke;

    public String getPotvrdaLozinke() {
        return potvrdaLozinke;
    }

    public void setPotvrdaLozinke(String potvrdaLozinke) {
        this.potvrdaLozinke = potvrdaLozinke;
    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    
    
    public String register(String tip){
        korisnik.setTip(tip);
        
        if (!korisnik.getLozinka().equals(potvrdaLozinke)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lozinka i potvrda lozinke nisu iste",                
                                                                             "Lozinka i potvrda lozinke nisu iste");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }else if (!Character.isLetter(korisnik.getLozinka().charAt(0))){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lozinka mora sadržati minimalno 7 karaktera," +
                                                                             "od toga bar jedno veliko slovo, jedan broj i jedan specijalni karakter," +
                                                                             "i mora počinjati slovom.", 
                                                                             "Lozinka mora sadržati minimalno 7 karaktera," +
                                                                             "od toga bar jedno veliko slovo, jedan broj i jedan specijalni karakter," +
                                                                             "i mora počinjati slovom.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }else if (util.dao.KorisnikDAO.checkIfUsernameExists(korisnik)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisničko ime već postoji.", 
                                                                             "Korisničko ime već postoji.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }else{
            if (util.dao.KorisnikDAO.register(korisnik)){
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešna registracija.", 
                                                                                "Uspešna registracija.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }else{
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neuspešna registracija.", 
                                                                                 "Neuspešna registracija.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
                
        }
    }
}
