package org.example.models;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.util.List;

@Type("film")
public class Film {

    @Id
    public String id;
    public String title;
    public String episode_id;
    public String opening_crawl;
    public String director;
    public String producer;
    public String release_date;
    public List<String> characters;
    public List<String> planets;
    public List<String> starships;
    public List<String> vehicles;
    public List<String> species;
    public String created;
    public String edited;
}
