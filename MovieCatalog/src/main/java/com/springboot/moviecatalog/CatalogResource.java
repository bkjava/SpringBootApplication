package com.springboot.moviecatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.moviecatalog.model.CatalogItem;
import com.springboot.moviecatalog.model.Movie;
import com.springboot.moviecatalog.model.MovieSummary;
import com.springboot.moviecatalog.model.UserRating;

@EnableRetry
@RestController
@RequestMapping("/catalog")
public class CatalogResource {
	
	Logger logger = LoggerFactory.getLogger(CatalogResource.class);
	 
    @Autowired
    private RestTemplate restTemplate;
    
    private HttpServletRequest request;
    
    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    private String getClientIp() {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        logger.info("remoteAddr:"+remoteAddr);
        
        return remoteAddr;
    }
    
    @Retryable(maxAttempts = 3, value = Exception.class)
    @RequestMapping("/healthCheck")
    public List<String> getHealthCheck() {

    	getClientIp();
    	
    	String temp = "movie-catalog-service:"+ restTemplate.getForObject("http://movie-catalog-service/actuator/health", String.class);
    	List<String> healthCheckList= new ArrayList<String>();
    	healthCheckList.add(temp);
    	temp = "ratings-data-service:"+ restTemplate.getForObject("http://ratings-data-service/actuator/health", String.class);
    	healthCheckList.add(temp);
    	temp = "movie-info-service:"+ restTemplate.getForObject("http://movie-info-service/actuator/health", String.class);
    	healthCheckList.add(temp);
    	    	
        return healthCheckList;

    }

    @Retryable(maxAttempts = 3, value = Exception.class)
    @RequestMapping("/userCatalog/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    	getClientIp();
    	
        logger.info("Inside get catalog");    	
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
        logger.info(userRating.getRatings().toString());
        
        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
                })
                .collect(Collectors.toList());

    }
    

	@Recover
	public String recover(Exception e) {
	 return "Test";
	}
    
	@Retryable(maxAttempts = 3, value = Exception.class)
    @RequestMapping("/movieCatalog/{movieId}")
    public Movie getMovieCatalog(@PathVariable("movieId") String movieId) {

    	getClientIp();
    	logger.info("Inside getMovieCatalog");    	
    	Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);        
    	logger.info(movie.toString());
    	
    	return movie;

    }
    
	@Retryable(maxAttempts = 3, value = Exception.class)
    @RequestMapping("/movieCatalogs")
    public MovieSummary getMovieCatalogs() {
    	getClientIp();
    	logger.info("Inside getMovieCatalogs");
    	MovieSummary movie = restTemplate.getForObject("http://movie-info-service/movies/all", MovieSummary.class);        
    	logger.debug(movie.getTotal_results());
    	return movie;

    }
}