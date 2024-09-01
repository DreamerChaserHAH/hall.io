package com.hallio.dms.tests;

import com.hallio.dms.IObject;
import com.hallio.dms.ISavableObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SampleObject extends IObject {

    private String name;
    private int age;

    public SampleObject(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public SampleObject(){
        this.id = -1;
        this.name = "";
        this.age = 0;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        return new LinkedList<String>(Arrays.asList(this.name, String.valueOf(this.age)));
    }

    @Override
    protected String getFilePath() {
        return "sample.txt";
    }

    @Override
    protected void loadFromString(List<String> attributes) {
        this.id = Integer.parseInt(attributes.get(0));
        this.name = attributes.get(1);
        this.age = Integer.parseInt(attributes.get(2));
    }
}
