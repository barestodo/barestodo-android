package com.barestodo.android.place;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

public class PlaceList {

    private long id;

    private String name;

    private List<User> members;


    public PlaceList(String name){
        super();
        this.name=name;
    }
    public PlaceList(long id,String name){
        super();
        this.name=name;
        this.id=id;
    }


    public String getName() {
        return name;
    }
}
