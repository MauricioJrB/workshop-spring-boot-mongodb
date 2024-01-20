package com.workshopmongo.config;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.workshopmongo.domain.Post;
import com.workshopmongo.domain.User;
import com.workshopmongo.dto.AuthorDTO;
import com.workshopmongo.dto.CommentDTO;
import com.workshopmongo.repository.PostRepository;
import com.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
	
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2024"), "Going to travel", "I'm going to travel to São Paulo. Hugs!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2024"), "Good morning", "Wake up happy today!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Good voyage, bro.", sdf.parse("21/03/2024"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Enjoy!", sdf.parse("22/03/2024"), new AuthorDTO(bob));	
		CommentDTO c3 = new CommentDTO("Have a nice day!", sdf.parse("23/03/2024"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
	
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
