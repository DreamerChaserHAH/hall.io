package com.lucid.fileio;

public interface ISavableObject {
    /*
     * Get all attributes inside the class that implements this class as a string
     *
     */
    public abstract String getAttributesAsString();
    public abstract String getAttributesWithIdAsString();
    public abstract void save();

}
