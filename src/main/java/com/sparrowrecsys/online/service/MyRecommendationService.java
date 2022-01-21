package com.sparrowrecsys.online.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrowrecsys.online.datamanager.DataManager;
import com.sparrowrecsys.online.datamanager.Movie;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * RecommendationService, provide recommendation service based on different input
 */

public class MyRecommendationService extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");

            //genre - movie category
            String genre = request.getParameter("genre");
            String year = request.getParameter("year");
            //number of returned movies
            String size = request.getParameter("size");
            //ranking algorithm
            String sortby = request.getParameter("sortby");

            HashMap<String, HashSet<String>> indexTypeFields = new HashMap<String, HashSet<String>>();
            if (StringUtils.isNotEmpty(genre)) {
                String[] fields = StringUtils.split(genre, ",");
                indexTypeFields.put(DataManager.DIM_GENRE, new HashSet<>(Arrays.asList(fields)));
            }
            if (StringUtils.isNotEmpty(year)) {
                String[] fields = StringUtils.split(year, ",");
                indexTypeFields.put(DataManager.DIM_YEAR, new HashSet<>(Arrays.asList(fields)));
            }

            //a simple method, just fetch all the movie in the genre
            List<Movie> movies = DataManager.getInstance().getMoviesFromBitmap(indexTypeFields, Integer.parseInt(size), sortby);

            //convert movie list to json format and return
            ObjectMapper mapper = new ObjectMapper();
            String jsonMovies = mapper.writeValueAsString(movies);
            response.getWriter().println(jsonMovies);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("");
        }
    }
}
