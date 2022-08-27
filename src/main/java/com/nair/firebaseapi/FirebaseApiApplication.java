package com.nair.firebaseapi;

import com.nair.firebaseapi.domain.Customer;
import com.nair.firebaseapi.repository.FirebaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FirebaseApiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FirebaseApiApplication.class, args);
		FirebaseRepository firebaseRepository = context.getBean(FirebaseRepository.class);
		Customer customer = firebaseRepository.findCustomerById("5b21ad42-25f7-11ed-861d-0242ac120002");
	}

}
