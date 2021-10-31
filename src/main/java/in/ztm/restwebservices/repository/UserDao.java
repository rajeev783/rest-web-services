package in.ztm.restwebservices.repository;

import in.ztm.restwebservices.Entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

	private static int usersCount = 4;
	
	private static List<User> users = new ArrayList<>();
	static{
		users.add(new User(1, "Ram", new Date()));
		users.add(new User(2, "Laxman", new Date()));
		users.add(new User(3, "Bharat", new Date()));
		users.add(new User(4, "Shatrughna", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user){
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	public User findById(int id){
		List<User> tempuser= users.stream()
				.filter(u -> u.getId() == id)
				.collect(Collectors.toList());
		return tempuser.get(0);
	}
	
	public void deleteById(int id){
		users.forEach(
			user ->{
				if(user.getId() == id)
					users.remove(user);
				return ;
			});
		
		
		
	}
}
