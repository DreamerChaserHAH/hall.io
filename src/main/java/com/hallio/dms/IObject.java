package com.hallio.dms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class IObject implements ILoadableObject, IDeleteableObject, ISavableObject {
    public int id;

    @Override
    public String getAttributesAsString() {
        return String.join(",", getAttributes());
    }

    @Override
    public String getAttributesWithIdAsString(){
        LinkedList<String> attributes = getAttributes();
        String idAsString = String.valueOf(this.id);
        attributes.addFirst(idAsString);
        return String.join(",", attributes);
    }

    @Override
    public void save() {
        // Save the object to the database
        FileManager.writeFile(this.getFilePath(), getAttributesAsString());
    }

    @Override
    public void Load(int id) {
        // Load the object from the database
        List<String> attributes = FileManager.readFile(this.getFilePath());
        this.id = Integer.parseInt(attributes.get(0));
    }

    @Override
    public void LoadFromString(String data) {
        // Load the object from a string
        List<String> attributes = Arrays.asList(data.split(","));
        loadFromString(attributes);
    }

    @Override
    public void delete(int id) {

    }

    protected abstract LinkedList<String> getAttributes();
    protected abstract String getFilePath();
    protected abstract void loadFromString(List<String> attributes);
}
