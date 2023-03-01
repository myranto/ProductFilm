package com.spring.springmvc_v_finale.controller;

import com.spring.springmvc_v_finale.model.Notification;
import com.spring.springmvc_v_finale.model.Scene;
import com.spring.springmvc_v_finale.model.View_scene;
import com.spring.springmvc_v_finale.utils.Connex.Connexion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@Controller
public class SceneController {
    public static int page =2;
    @GetMapping("/")
    public ModelAndView index(HttpServletRequest req) throws Exception {
        return ToList(req);
    }
    //mandefa anle izy any am page de creation scene
    @GetMapping("/create_scene")
    public ModelAndView sendToCreate(){
        ModelAndView mod = new ModelAndView("scene/ajout_scene");
        return mod;
    }
//    mandefa anle izy any am page de modification scene
// mila pathvariable izay id anle scene izy
    @GetMapping("/modify/{idscene}")
    public ModelAndView sendToModify(@PathVariable("idscene") int idscene) throws Exception {
        ModelAndView mod = new ModelAndView("scene/modification_scene");
        Scene scene = new Scene(idscene);
        scene = scene.findById();
        mod.addObject("scene",scene);
        mod.addObject("mess","");
        return mod;
    }

//    code maka anle scene depuis formulaire de modif ou creation scene
    public Scene getScene(HttpServletRequest req,int action){
        String nom = req.getParameter("nom");
        String debut = req.getParameter("debut");
        String time_debut = req.getParameter("time_debut");
        String fin = req.getParameter("fin");
        String time_fin = req.getParameter("time_fin");
        String datedebut = (action==0)?debut + " "+time_debut+":00":debut + " "+time_debut;
        String datefin = (action==0)?fin+ " "+time_fin+":00":fin+ " "+time_fin;
        System.out.println(datefin+" "+action);
        System.out.println(datedebut+" "+action);
        Timestamp d_debut  = null;
        Timestamp fins = null;
        try {
         d_debut  = Timestamp.valueOf(datedebut);
        }catch (IllegalArgumentException e){
            d_debut  = Timestamp.valueOf(datedebut+":00");
        }
        try {
         fins = Timestamp.valueOf(datefin);
        }
        catch (IllegalArgumentException e){
            fins  = Timestamp.valueOf(datefin+":00");
        }
        Scene scene = new Scene(nom,d_debut,fins);
        return scene;
    }
// mila pathvariable izay id anle scene izy

    @PostMapping("/update_scene/{idscene}")
    public ModelAndView update_scene(@PathVariable("idscene") int idscene,HttpServletRequest req) throws Exception {
        ModelAndView mod = new ModelAndView("scene/modification_scene");

        Scene scene = getScene(req,1);
        scene.setIdscene(idscene);
        scene.updateById();
        mod.addObject("scene",scene);
        mod.addObject("mess","update reussi");
        return mod;
    }
//    valider l'enreistrement de la scene
    @PostMapping("/validate_scene")
    public ModelAndView validate_scene(HttpServletRequest req) throws Exception {
//        ModelAndView mod = new ModelAndView("scene/ajout_scene");

        Scene scene = getScene(req,0);
        scene.save();
//        mod.addObject("mess","success lesy");
        return ToList(req);
    }
//    send list of scene
    @GetMapping("/list_scene")
    public ModelAndView ToList(HttpServletRequest req) throws Exception {
        ModelAndView mod = new ModelAndView("list/list_scene");
        Scene scene = new Scene();
        int pages =  (req.getSession().getAttribute("page")==null)?page: (int) req.getSession().getAttribute("page");
        if (req.getSession().getAttribute("page")==null)
            req.getSession().setAttribute("page",pages);
        int first =  (req.getParameter("first")==null)?0: Integer.parseInt(req.getParameter("first"));
        int t = scene.SelectAll().size();
        int size = calculatePage(pages,t);
        mod.addObject("count",size);
        mod.addObject("list",scene.paginateAll(pages,first));
        mod.addObject("action","list");
        return mod;
    }
//    parametrage de nombre a afficher pour la pagination
    @PostMapping("/changepage")
    public ModelAndView changePage(@RequestParam("page") int page,HttpServletRequest req) throws Exception {
        req.getSession().setAttribute("page",page);
        return ToList(req);
    }
//    search with pagination
    @GetMapping("/search")
    public ModelAndView search(HttpServletRequest req) throws Exception {
        ModelAndView mod = new ModelAndView("list/list_scene");
        String search = req.getParameter("search");
        Scene e = new Scene();
        int pages =  (req.getSession().getAttribute("page")==null)?page: (int) req.getSession().getAttribute("page");
        int first =  (req.getParameter("first")==null)?0: Integer.parseInt(req.getParameter("first"));
        String sql="select * from scene where lower(nom) like lower('%"+search+"%') order by idscene asc";

        if (search==null)
            sql = (String) req.getSession().getAttribute("query");
        req.getSession().setAttribute("query",sql);
        ArrayList<Scene> list = e.paginateSearch(sql,pages,first);
        int t = e.SelectAllByQuerry(sql).size();
        int size = calculatePage(pages,t);
        mod.addObject("count",size);
        mod.addObject("list",list);
        mod.addObject("action","search");
        return mod;
    }
    private int calculatePage(int pages,int t){
        double size = (double) t/pages;
        if (t%pages!=0)
            size++;
        return (int) size;
    }
    public static String convertTimestampTodate(Timestamp date){
        Date t = new Date(date.getTime());
        return String.valueOf(t);
    }
//    show all alert or notification about task
    @GetMapping("/alert")
    public ModelAndView getAlert() throws Exception {
        ModelAndView mod = new ModelAndView("notification/alert");
        ArrayList<Notification> notif = new Notification().SelectAll();
        mod.addObject("notif",notif);
        return mod;
    }
}
