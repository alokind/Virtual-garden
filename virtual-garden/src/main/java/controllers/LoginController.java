/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LoginController {
    
    private Korisnik korisnik;
    
    private String korime;
    private String lozinka;

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    
    private String poruka;

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    
    public String login(){
        korisnik = util.dao.KorisnikDAO.login(korime, lozinka);
        if (korisnik==null){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nema takvog korisnika",                
                                                                            "Nema takvog korisnika");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }else if (korisnik.getJeOdobren()!=1){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Korisnik još uvek nije odobren od strane administratora.", 
                                                                            "Korisnik još uvek nije odobren od strane administratora.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }else{
            HttpSession session = util.SessionUtils.getSession();
            session.setAttribute("korisnik", korisnik);
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
                fc.getExternalContext().redirect(korisnik.getTip()+".xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return korisnik.getTip();
        }
    }
    
    public void checkSessionNotLoggedIn(){
        HttpSession session = util.SessionUtils.getSession();
        if (session.getAttribute("korisnik")!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
                fc.getExternalContext().redirect(((Korisnik)session.getAttribute("korisnik")).getTip()+".xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void checkSessionLoggedIn(){
        HttpSession session = util.SessionUtils.getSession();
        if (session.getAttribute("korisnik")==null){
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
                fc.getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String logout(){
        HttpSession session = util.SessionUtils.getSession();
        session.invalidate();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
    
}
