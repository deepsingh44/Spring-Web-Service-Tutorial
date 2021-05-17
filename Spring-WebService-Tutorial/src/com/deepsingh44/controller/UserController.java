package com.deepsingh44.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deepsingh44.dao.UserDao;
import com.deepsingh44.model.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDao userDao;

	@PostMapping("/insert")
	public ResponseEntity<String> register(@RequestBody User user) {
		int i = userDao.insert(user);
		return (i > 0) ? new ResponseEntity<String>("Successfully", HttpStatus.OK)
				: new ResponseEntity<String>("Registration Failed", HttpStatus.FORBIDDEN);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody User user) {
		int i = userDao.delete(user);
		return (i > 0) ? new ResponseEntity<String>("Successfully Deleted", HttpStatus.OK)
				: new ResponseEntity<String>("Deletion Failed", HttpStatus.FORBIDDEN);
	}

	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody User user) {
		int i = userDao.update(user);
		return (i > 0) ? new ResponseEntity<String>("Successfully Update", HttpStatus.OK)
				: new ResponseEntity<String>("Updation Failed", HttpStatus.FORBIDDEN);
	}

	@PostMapping("/userimage")
	public ResponseEntity<String> bookImage(@RequestPart("profileimage") MultipartFile file, HttpServletRequest req) {
		boolean status = false;
		try {
			String root = req.getServletContext().getRealPath("/");
			File folder = new File(root, "profiles");
			if(!folder.exists())folder.mkdir();
			File location = File.createTempFile("profile", ".jpg", folder);
			file.transferTo(location);
			System.out.println(location.getAbsolutePath());
			status = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return (status) ? new ResponseEntity<String>("Successfully Upload", HttpStatus.OK)
				: new ResponseEntity<String>("Upload Failed", HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestParam String email, @RequestParam String pass) {
		User user = userDao.login(email, pass);
		return (user != null) ? new ResponseEntity<User>(user, HttpStatus.OK)
				: new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
	}

}
