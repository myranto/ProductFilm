package com.spring.springmvc_v_finale.model;

import com.spring.springmvc_v_finale.utils.Connex.Connexion;
import com.spring.springmvc_v_finale.utils.DAO.ObjectBDD;
import com.spring.springmvc_v_finale.utils.inter.ForeignKeyAnnotation;
import com.spring.springmvc_v_finale.utils.inter.KeyAnnotation;
import com.spring.springmvc_v_finale.utils.inter.TableAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@TableAnnotation
public class Mis_en_Action extends ObjectBDD {
    @KeyAnnotation
    private int idaction;
    @KeyAnnotation
    private int idperso;

    @KeyAnnotation
    private int idplateau;
    @ForeignKeyAnnotation(name = "idperso",references = "idperso")
    private Personnage perso;
    @ForeignKeyAnnotation(name = "idplateau",references = "idplateau")
    private Plateau plate;

    public Personnage getPerso() {
        return perso;
    }

    public void setPerso(Personnage perso) {
        this.perso = perso;
    }

    public Plateau getPlate() {
        return plate;
    }

    public void setPlate(Plateau plate) {
        this.plate = plate;
    }

    public Mis_en_Action() {
    }

    public Mis_en_Action(int idperso, int idplateau) {
        this.idperso = idperso;
        this.idplateau = idplateau;
    }

    public Mis_en_Action(int idaction, int idperso, int idplateau) {
        this.idaction = idaction;
        this.idperso = idperso;
        this.idplateau = idplateau;
    }

    public int getIdaction() {
        return idaction;
    }

    public void setIdaction(int idaction) {
        this.idaction = idaction;
    }

    public int getIdperso() {
        return idperso;
    }

    public void setIdperso(int idperso) {
        this.idperso = idperso;
    }

    public int getIdplateau() {
        return idplateau;
    }

    public void setIdplateau(int idplateau) {
        this.idplateau = idplateau;
    }
    public void save(Connection con) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super.save(con);
    }
    public void save() throws Exception {
        super.saveAll(Connexion.getConnection());
    }
    public ArrayList<Mis_en_Action> SelectAll() throws Exception {
        return super.SelectAll(Connexion.getConnection());
    }
    public ArrayList<Mis_en_Action> findByAction(int action) throws Exception {
        String sql = "select * from "+getNomTable()+" where idaction="+action;
        return SelectAllByQuery(Connexion.getConnection(),sql);
    }
}
