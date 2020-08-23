package com.springboot.movieratings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.movieratings.model.Rating;
import com.springboot.movieratings.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
	
	Logger logger = LoggerFactory.getLogger(RatingsResource.class);

    @RequestMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
    	logger.info("getMovieRating");
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
    	logger.info("getUserRatings");
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }

}
