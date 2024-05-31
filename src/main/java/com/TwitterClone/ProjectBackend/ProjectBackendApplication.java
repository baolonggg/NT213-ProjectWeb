package com.TwitterClone.ProjectBackend;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectBackendApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(ProjectBackendApplication.class, args);
	}

}
