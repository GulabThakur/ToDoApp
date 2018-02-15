package com.bridgeit.ToDoApp.jUnitTesting;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.bridgeit.ToDoApp.config.WebConfig;
import com.bridgeit.ToDoApp.model.UserModel;
//import com.memorynotfound.model.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {WebConfig.class})
public class JunitControllerIntgrate {

	private static final String BASE_URI = "http://localhost:8080/ToDoApp/";
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;
    
    @Autowired
    private RestTemplate template;
    
    @Test
    public void get_all_sucess() {
    	  ResponseEntity<UserModel[]> response = template.getForEntity(BASE_URI, UserModel[].class);
          assertThat(response.getStatusCode(), is(HttpStatus.OK));
          validateCORSHttpHeaders(response.getHeaders());
          
    }
	private void validateCORSHttpHeaders(HttpHeaders headers) {
	        assertThat(headers.getAccessControlAllowOrigin(), is("*"));
	        assertThat(headers.getAccessControlAllowHeaders(), hasItem("*"));
	        assertThat(headers.getAccessControlMaxAge(), is(3600L));
	        assertThat(headers.getAccessControlAllowMethods(), hasItems(
	                HttpMethod.GET,
	                HttpMethod.POST,
	                HttpMethod.PUT,
	                HttpMethod.OPTIONS,
	                HttpMethod.DELETE));
	    }
	

	
}
