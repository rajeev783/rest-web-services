package in.ztm.restwebservices.controller;

import in.ztm.restwebservices.Entity.User;
import in.ztm.restwebservices.Exception.UserNotFoundException;
import in.ztm.restwebservices.repository.UserDao;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDao userService;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userService.findAll();
	}
	
	@GetMapping("users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		User user = userService.findById(id);
		System.out.println(user.toString());
		if(user.getId()==null)
			throw new UserNotFoundException(id + " not found");
		
		EntityModel<User> model= EntityModel.of(user);
		System.out.println(model);
		try{
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		WebMvcLinkBuilder linkToDeleteUser = linkTo(methodOn(this.getClass()).deleteUser(id));
		System.out.println(linkToUsers);
		model.add(linkToUsers.withRel("all-users"));
		model.add(linkToDeleteUser.withRel("delete-user"));
		System.out.println(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
		
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user){
		User savedUser = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId())
		.toUri();
		return ResponseEntity.created(location).build();
		
	}
	@DeleteMapping("users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id){
		userService.deleteById(id);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
				return ResponseEntity.created(location).build();
		
	}
	
}
