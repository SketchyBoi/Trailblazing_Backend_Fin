package com.nighthawk.spring_portfolio.mvc.usr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class UsrDetailsService implements UserDetailsService {  // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (Person, Roles, and Scrum)
    @Autowired  // Inject PersonJpaRepository
    private UsrJpaRepository usrJpaRepository;
    @Autowired  // Inject RoleJpaRepository
    private UsrRoleJpaRepository usrRoleJpaRepository;
    @Autowired  // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;

    /* UserDetailsService Overrides and maps Person & Roles POJO into Spring Security */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usr usr = usrJpaRepository.findByEmail(email); // setting variable usr equal to the method finding the usrname in the database
        if(usr==null) {
			throw new UsernameNotFoundException("User not found with usrname: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usr.getRoles().forEach(role -> { //loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); //create a SimpleGrantedAuthority by passed in role, adding it all to the authorities list, list of roles gets past in for spring security
        });
        // train spring security to Usr and Authorities
        return new org.springframework.security.core.userdetails.User(usr.getEmail(), usr.getPassword(), authorities);
    }

    /* Person Section */

    public  List<Usr>listAll() {
        return usrJpaRepository.findAllByOrderByNameAsc();
    }

    // custom query to find match to name or email
    public  List<Usr>list(String name, String email) {
        return usrJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<Usr>listLike(String term) {
        return usrJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<Usr>listLikeNative(String term) {
        String like_term = String.format("%%%s%%",term);  // Like required % rappers
        return usrJpaRepository.findByLikeTermNative(like_term);
    }

    // encode password prior to sava
    public void save(Usr usr) {
        usr.setPassword(passwordEncoder.encode(usr.getPassword()));
        usrJpaRepository.save(usr);
    }

    public Usr get(long id) {
        return (usrJpaRepository.findById(id).isPresent())
                ? usrJpaRepository.findById(id).get()
                : null;
    }

    public Usr getByEmail(String email) {
        return (usrJpaRepository.findByEmail(email));
    }

    public void delete(long id) {
        usrJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Usr usr: listAll()) {
            if (usr.getPassword() == null || usr.getPassword().isEmpty() || usr.getPassword().isBlank()) {
                usr.setPassword(passwordEncoder.encode(password));
            }
            if (usr.getRoles().isEmpty()) {
                UsrRole role = usrRoleJpaRepository.findByName(roleName);
                if (role != null) { // verify role
                    usr.getRoles().add(role);
                }
            }
        }
    }


    /* Roles Section */

    public void saveRole(UsrRole role) {
        UsrRole roleObj = usrRoleJpaRepository.findByName(role.getName());
        if (roleObj == null) {  // only add if it is not found
            usrRoleJpaRepository.save(role);
        }
    }

    public  List<UsrRole>listAllRoles() {
        return usrRoleJpaRepository.findAll();
    }

    public UsrRole findRole(String roleName) {
        return usrRoleJpaRepository.findByName(roleName);
    }

    public void addRoleToUsr(String email, String roleName) { // by passing in the two strings you are giving the usr that certain role
        Usr usr = usrJpaRepository.findByEmail(email);
        if (usr != null) {   // verify person
            UsrRole role = usrRoleJpaRepository.findByName(roleName);
            if (role != null) { // verify role
                boolean addRole = true;
                for (UsrRole roleObj : usr.getRoles()) {    // only add if usr is missing role
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) usr.getRoles().add(role);   // everything is valid for adding role
            }
        }
    }
    
}