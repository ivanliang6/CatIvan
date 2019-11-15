package com.example.ivancat;
import java.io.Serializable;

public class Cat implements Serializable {
    public String id;
    public String name;
    public String origin;
    public String description;
    public String life_span;
    public String dog_friendly;
    public String wikipedia_url;
    public String temperament;
    public Weight weight;

    class Weight implements Serializable {
        public String imperial;
        public String metric;
    }





}
