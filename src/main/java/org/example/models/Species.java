package org.example.models;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.List;

@Type("species")
public class Species {
    @Id
    public String id;
    public String name;
    public String classification;
    public String designation;
    public String average_height;
    public String skin_colors;
    public String hair_colors;
    public String eye_colors;
    public String average_lifespan;
    public String homeworld;
    public String language;
    public List<String> people;
    public List<String> films;
    public String created;
    public String edited;
    public String url;
}
