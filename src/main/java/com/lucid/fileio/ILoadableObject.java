package com.lucid.fileio;

public interface ILoadableObject {

    ///<summary>
    /// Load the object from the database
    ///</summary>
    public void Load(int id);

    ///<summary>
    /// Load the object from a string
    ///</summary>
    public abstract void LoadFromString(String data);
}
