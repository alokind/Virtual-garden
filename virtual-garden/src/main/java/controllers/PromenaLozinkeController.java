/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
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
public class PromenaLozinkeController {
    private Korisnik korisnik;
    private String staraLozinka;
    private String novaLozinka1;
    private String novaLozinka2;
    
    
    public void dohvatiKorisnika(){
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
    }
    
    public void promenaLozinke(){
        if (!novaLozinka1.equals(novaLozinka2)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nova lozinka i potvrda nove lozinke nisu iste.",                
                                                                             "Nova lozinka i potvrda nove lozinke nisu iste.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }else if (!Character.isLetter(novaLozinka1.charAt(0))){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nova lozinka mora sadržati minimalno 7 karaktera," +
                                                                             "od toga bar jedno veliko slovo, jedan broj i jedan specijalni karakter," +
                                                                             "i mora počinjati slovom.", 
                                                                             "Lozinka mora sadržati minimalno 7 karaktera," +
                                                                             "od toga bar jedno veliko slovo, jedan broj i jedan specijalni karakter," +
                                                                             "i mora počinjati slovom.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }else if (novaLozinka1.equals(korisnik.getLozinka())){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nova lozinka i stara lozinka ne smeju biti iste.",                
                                                                             "Nova lozinka i stara lozinka ne smeju biti iste.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        } else{
            util.dao.KorisnikDAO.promenaLozinke(korisnik.getId(), novaLozinka1);
            LoginController.logout();
        }
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public String getNovaLozinka1() {
        return novaLozinka1;
    }

    public void setNovaLozinka1(String novaLozinka1) {
        this.novaLozinka1 = novaLozinka1;
    }

    public String getNovaLozinka2() {
        return novaLozinka2;
    }

    public void setNovaLozinka2(String novaLozinka2) {
        this.novaLozinka2 = novaLozinka2;
    }


    
}
