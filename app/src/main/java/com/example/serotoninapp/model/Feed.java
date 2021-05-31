package com.example.serotoninapp.model;

import androidx.annotation.NonNull;

import com.example.serotoninapp.model.entry.Entry;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name = "feed", strict = false)
public class Feed implements Serializable {

    @ElementList(inline = true, name = "entry")
    private List<Entry> entrys;

    public List<Entry> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<Entry> entrys) {
        this.entrys = entrys;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public String toString() {
        return "Feed: \n [ Entrys: " + entrys + "]";
    }
}
