package com.springboot.moviecatalog.model;

public class MovieSummary {
	private String total_results;
	private Movie[] results;
	
	public String getTotal_results() {
		return total_results;
	}
	public void setTotal_results(String total_results) {
		this.total_results = total_results;
	}
	public Movie[] getResults() {
		return results;
	}
	public void setResults(Movie[] results) {
		this.results = results;
	}
}
