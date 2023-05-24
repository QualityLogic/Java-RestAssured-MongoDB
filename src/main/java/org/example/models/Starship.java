package org.example.models;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.List;

@Type("starship")
public class Starship {
    @Id
    public String id;
    public String name;
    public String model;
    public String manufacturer;
    public String cost_in_credits;
    public String length;
    public String max_atmosphering_speed;
    public String crew;
    public String passengers;
    public String cargo_capacity;
    public String consumables;
    public String hyperdrive_rating;
    public String MGLT;
    public String starship_class;
    public List<String> pilots;
    public List<String> films;
    public String created;
    public String edited;
}
