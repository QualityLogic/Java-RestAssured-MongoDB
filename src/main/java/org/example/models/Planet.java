package org.example.models;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.List;

@Type("planet")
public class Planet {

    @Id
    public String id;
    public String name;
    public String rotation_period;
    public String orbital_period;
    public String diameter;
    public String climate;
    public String gravity;
    public String terrain;
    public String surface_water;
    public String population;
    public List<String> residents;
    public List<String> films;
    public String created;
    public String edited;
    public String url;
}
