package com.orderproject.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info( title = "Order-Api",
		description ="E-Commerce platform for Order API,\"The application should have a REST API that allows users to perform CRUD (create, read, update, delete) operations on a data model representing a simple e-commerce platform.\",",
		summary =  "Order-API",
		termsOfService = "T&C",
		contact = @io.swagger.v3.oas.annotations.info.Contact(
				name = "Pushpendra",
				email = "pushpendra.sen@perennialsys.com"),
		version = "v1"
				),
		servers = {
				@Server(
						description = "Dev",
						url ="http:localhost:9093"
						),
				@Server(
						description = "Test",
						url ="http:localhost:9093"
						)
		}
		)
public class OpenApiConfig {

}
