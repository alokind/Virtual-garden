/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Narudzbina;
import beans.Proizvod;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class PreduzeceRezultatiController {
    private Korisnik korisnik;
    private LineChartModel lineModel;
            
    public void dohvatiKorisnika(){
        util.dao.ProizvodDAO.updateIsporuceneProizvode();
        HttpSession session = util.SessionUtils.getSession();
        korisnik = (Korisnik) session.getAttribute("korisnik");
        init();
    }
    

    public void init() {
        createLineModel();
    }
    
    public void createLineModel2() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
         
        LineChartDataSet dataSet = new LineChartDataSet();
        ArrayList<Object> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40);
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("My First Dataset");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
         
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
         
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);
         
        lineModel.setOptions(options);
        lineModel.setData(data);
    }
    
    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
        
        ArrayList<Object> datumi = new ArrayList<>();
        ArrayList<Object> vrednosti = new ArrayList<>();
        
        HashMap <String, Integer> mapa = util.dao.IsporukaProizvodaDAO.dohvatiBrojNarudzbinaUZadnjih30Dana(korisnik.getId());
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  

        Date currDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.add(Calendar.DATE, -30);
        
        Date startDate = cal.getTime();
        Date endDate = currDate;
        
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            datumi.add((date).toString());
            
            Date datum = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            if (mapa.containsKey(dateFormat.format(datum)))
                vrednosti.add(mapa.get(dateFormat.format(datum)));
            else
                vrednosti.add(0);
        }

         
        LineChartDataSet dataSet = new LineChartDataSet();
        dataSet.setData(vrednosti);
        dataSet.setFill(true);
        dataSet.setLabel("Broj narudžbina");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
        
        data.setLabels(datumi);
         
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Broj narudžbina po datumima u poslednjih 30 dana");
        options.setTitle(title);
         
        
        lineModel.setOptions(options);
        lineModel.setData(data);
    }
    
    public void createPDF() throws DocumentException {
        OutputStream os = null;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition",  "inline=filename=izvestaj.pdf");

            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            
            
            Text text = new Text("Izveštaj firme "+korisnik.getIme()+" za " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            text.setFontSize(22);

            
            Paragraph naslov = new Paragraph("Izveštaj preduzeca "+korisnik.getIme()+" za " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            naslov.setAlignment(Paragraph.ALIGN_CENTER);
            
            document.add(naslov);
            document.add(new Paragraph("\n"));
            
            ArrayList<Narudzbina> narudzbine = util.dao.IsporukaProizvodaDAO.dohvatiNarudzbineZaIzvestaj(korisnik.getId());
            
            for (Narudzbina n : narudzbine) {
                document.add(new Paragraph("-"+n.getIme()+" "+n.getPrezime()+", "+n.getMestoRasadnika()+", --"+n.getUkupnaVrednost()+" din."));
                List proizvodi = new List();
                proizvodi.setIndentationLeft(30);
                for (Proizvod p : n.getProizvodi()) {
                    proizvodi.add(new ListItem(p.getKolicina()+" x "+p.getNaziv()+", "+p.getProizvodjac()+" -"+p.getCena()+" din/kom"));
                }
                document.add(proizvodi);
            }
                    
            document.close();
            
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(PreduzeceRezultatiController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(PreduzeceRezultatiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
    
    
    
    
    
    
    
}
