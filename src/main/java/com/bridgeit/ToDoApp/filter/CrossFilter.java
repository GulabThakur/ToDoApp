package com.bridgeit.ToDoApp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
public class CrossFilter extends OncePerRequestFilter {

	private final Logger LOG = LoggerFactory.getLogger(CrossFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filter)
			throws ServletException, IOException {
		
		LOG.info(" Adding CORS Hearder.....................");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		filter.doFilter(req, resp);
		

	}

}
