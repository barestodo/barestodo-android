package com.barestodo.android.place;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

public class Circle implements Serializable {

    private long id;

    private String name;

    private List<User> members;

    private int nbMembers;
    private int nbPlaces;

    public Circle(String name){
        super();
        this.name=name;
    }
    public Circle(long id,String name){
        super();
        this.name=name;
        this.id=id;
    }
    public Circle(long id,String name,int nbPlaces,int nbMembers){
        super();
        this.name=name;
        this.id=id;
        this.nbMembers=nbMembers;
        this.nbPlaces=nbPlaces;
    }


    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getPlaceCount() {
        return nbPlaces;
    }

    public int getMemberCount() {
        return nbMembers;
    }
}
