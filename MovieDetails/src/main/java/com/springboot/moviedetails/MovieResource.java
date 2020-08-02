package com.springboot.moviedetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.moviedetails.model.Movie;
import com.springboot.moviedetails.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/all")
    private MovieSummary getAllMovies() {   
    	System.out.println("Get all Movies");
    	MovieSummary moviesList = restTemplate.getForObject("https://api.themoviedb.org/3/discover/movie?api_key=" +  apiKey, MovieSummary.class);
    	
    	return moviesList;
    	
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        Movie movie = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, Movie.class);
        return movie;

    }

}
