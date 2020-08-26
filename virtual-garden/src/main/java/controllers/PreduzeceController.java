/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Narudzbina;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
public class PreduzeceController {
    private Korisnik korisnik;
    private ArrayList<Narudzbina> narudzbine = new ArrayList<>();
    private ArrayList<Narudzbina> naIsporuci = new ArrayList<>();
    
    private int slobodniDostavljaci;
    
    
            
    public void dohvatiKorisnikaINarudzbine(){
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        
        narudzbine = util.dao.IsporukaProizvodaDAO.dohvatiNarudzbine(korisnik.getId(),2);
        narudzbine.addAll(util.dao.IsporukaProizvodaDAO.dohvatiNarudzbine(korisnik.getId(),-1));
        slobodniDostavljaci = util.dao.KorisnikDAO.brojSlobodnihDostavljaca(korisnik.getId());
    }
    
    public void otkaziNarudzbinu(Narudzbina n){
        util.dao.IsporukaProizvodaDAO.izbrisiNarudzbinu(n);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno ste otkazali narudžbinu","Uspešno ste otkazali narudžbinu");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void potvrdiNarudzbinu(Narudzbina n){

        try {
            if(util.dao.KorisnikDAO.brojSlobodnihDostavljaca(korisnik.getId())<=0){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Svi dostavljači su zauzeti. Narudžbina na čekanju.", 
                                                                                    "Svi dostavljači su zauzeti. Narudžbina na čekanju.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                util.dao.IsporukaProizvodaDAO.potvrdiNaCekanjuNarudzbinu(n, 0, 2);
                dohvatiKorisnikaINarudzbine();
                return;
            }
            
            String sURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric"
                    + "&origins="+korisnik.getMesto().replace(" ", "_")+"&destinations="+n.getMestoRasadnika().replace(" ", "_")+"&key=***";
            
            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            
            int duration = rootobj
                    .getAsJsonArray("rows").get(0)
                    .getAsJsonObject().getAsJsonArray("elements").get(0)
                    .getAsJsonObject().get("duration")
                    .getAsJsonObject().get("value").getAsInt();
            
            util.dao.IsporukaProizvodaDAO.potvrdiNaCekanjuNarudzbinu(n, duration, 1);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspešno prihvaćena narudžbina.", 
                                                                                "Uspešno prihvaćena narudžbina.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            dohvatiKorisnikaINarudzbine();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PreduzeceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PreduzeceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    public int getSlobodniDostavljaci() {
        return slobodniDostavljaci;
    }

    public void setSlobodniDostavljaci(int slobodniDostavljaci) {
        this.slobodniDostavljaci = slobodniDostavljaci;
    }

    public ArrayList<Narudzbina> getNaIsporuci() {
        return naIsporuci;
    }

    public void setNaIsporuci(ArrayList<Narudzbina> naIsporuci) {
        this.naIsporuci = naIsporuci;
    }
    
    
    

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public ArrayList<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(ArrayList<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }
    
    
    
    
    
    
    
    
    
    
}
