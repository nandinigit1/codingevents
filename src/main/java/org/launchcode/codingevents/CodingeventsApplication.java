package org.launchcode.codingevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodingeventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingeventsApplication.class, args);
	}

}

//There was an unexpected error (type=Not Found, status=404).
//No static resource events/events/create.
//        org.springframework.web.servlet.resource.NoResourceFoundException: No static resource events/events/create.


//There was an unexpected error (type=Method Not Allowed, status=405).
//Method 'POST' is not supported.
//		org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported