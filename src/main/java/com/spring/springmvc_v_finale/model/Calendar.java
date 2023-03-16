package com.spring.springmvc_v_finale.model;

import com.spring.springmvc_v_finale.utils.Connex.Connexion;
import com.spring.springmvc_v_finale.utils.DAO.ObjectBDD;
import com.spring.springmvc_v_finale.utils.inter.IdAnnotation;
import com.spring.springmvc_v_finale.utils.inter.KeyAnnotation;
import com.spring.springmvc_v_finale.utils.inter.TableAnnotation;

import java.sql.Timestamp;
import java.util.ArrayList;

@TableAnnotation
public class Calendar extends ObjectBDD {
    @KeyAnnotation
    @IdAnnotation(name = "idCalendar")
    private int idCalendar;
    @KeyAnnotation
    private int idAction;
    @KeyAnnotation
    private Timestamp dateCalendar;
    @KeyAnnotation
    private Timestamp datefin;

    public Timestamp getDatefin() {
        return datefin;
    }

    public void setDatefin(Timestamp datefin) {
        this.datefin = datefin;
    }

    public Calendar(int idAction, Timestamp dateCalendar, Timestamp datefin) {
        this.idAction = idAction;
        this.dateCalendar = dateCalendar;
        this.datefin = datefin;
    }

    public Calendar(int idAction) {
        this.idAction = idAction;
    }

    public Calendar(int idAction, Timestamp dateCalendar) {
        this.idAction = idAction;
        this.dateCalendar = dateCalendar;
    }

    public Calendar(int idCalendar, int idAction, Timestamp dateCalendar) {
        this.idCalendar = idCalendar;
        this.idAction = idAction;
        this.dateCalendar = dateCalendar;
    }

    public int getIdCalendar() {
        return idCalendar;
    }

    public void setIdCalendar(int idCalendar) {
        this.idCalendar = idCalendar;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public Timestamp getDateCalendar() {
        return dateCalendar;
    }

    public void setDateCalendar(Timestamp dateCalendar) {
        this.dateCalendar = dateCalendar;
    }
    public void save() throws Exception {
        super.saveAll(Connexion.getConnection());
    }
    public ArrayList<Personnage> SelectAll() throws Exception {
        return super.SelectAll(Connexion.getConnection());
    }
    public ArrayList<Personnage> SelectAllByQuerry(String sql) throws Exception {
        return SelectAllByQuery(Connexion.getConnection(),sql);
    }
}
