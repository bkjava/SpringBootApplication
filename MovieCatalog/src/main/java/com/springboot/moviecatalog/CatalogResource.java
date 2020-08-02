package com.springboot.moviecatalog;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.moviecatalog.model.CatalogItem;
import com.springboot.moviecatalog.model.Movie;
import com.springboot.moviecatalog.model.MovieSummary;
import com.springboot.moviecatalog.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/userCatalog/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
                })
                .collect(Collectors.toList());

    }
    
    @RequestMapping("/movieCatalog/{movieId}")
    public Movie getMovieCatalog(@PathVariable("movieId") String movieId) {

    	System.out.println(movieId);
    	Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);        
    	return movie;

    }
    
    @RequestMapping("/movieCatalogs")
    public MovieSummary getMovieCatalogs() {
    	MovieSummary movie = restTemplate.getForObject("http://movie-info-service/movies/all", MovieSummary.class);        
    	return movie;

    }
}