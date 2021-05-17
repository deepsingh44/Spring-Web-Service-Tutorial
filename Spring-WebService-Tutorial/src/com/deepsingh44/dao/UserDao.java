package com.deepsingh44.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deepsingh44.model.User;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public int insert(User user) {
		int i = 0;
		sessionFactory.getCurrentSession().save(user);
		return i = 1;
	}

	@Transactional
	public int delete(User user) {
		int i = 0;
		sessionFactory.getCurrentSession().delete(user);
		return i = 1;
	}

	@Transactional
	public int update(User user) {
		int i = 0;
		sessionFactory.getCurrentSession().update(user);
		return i = 1;
	}

	@Transactional
	public User login(String email, String pass) {
		User user = sessionFactory.getCurrentSession().get(User.class, email);
		if (user != null) {
			if (user.getPass().equals(pass)) {
				return user;
			}
		}
		return null;
	}

}
