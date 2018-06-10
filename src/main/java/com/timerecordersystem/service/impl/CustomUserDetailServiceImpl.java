package com.timerecordersystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.service.EmployeeService;

@Component
public class CustomUserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Employee employee = Optional.ofNullable(this.employeeService.findByPis(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		final List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_USER");
		
		return new User(employee.getPis(), employee.getPassword(), authorityList);
	}

}
