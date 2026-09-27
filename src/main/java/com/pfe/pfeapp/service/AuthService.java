package com.pfe.pfeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pfe.pfeapp.dto.Login;
import com.pfe.pfeapp.dto.Register;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.UserRepository;
import com.pfe.pfeapp.repository.UsernameAutoriseRepository;
import com.pfe.pfeapp.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsernameAutoriseRepository usernameAutoriseRepo;

    public void register(Register request) {
    	
    	
    	//verification si le username existe dans la liste autorisée des usernames:
    	if(!usernameAutoriseRepo.existsByUsernameAutorise(request.getUsernameTelegram())) {
    		throw new RuntimeException("Ce username Telegram n'est pas autorisé à s'inscrire");
    	}
    	
    	//verification si ce username a deja un compte actif:
        if (userRepository.existsByUsernameTelegram(request.getUsernameTelegram())) {
            throw new RuntimeException("Ce username Telegram est déjà utilisé");
        }

        User user = new User();
        user.setNomComplet(request.getNomComplet());
        user.setUsernameTelegram(request.getUsernameTelegram());
        user.setPassword(passwordEncoder.encode(request.getPassword())); 
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public String login(Login request) {
        User user = userRepository.findByUsernameTelegram(request.getUsernameTelegram())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Identifiants invalides"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Identifiants invalides");
        }

        return jwtUtil.generateToken(user.getUsernameTelegram(), user.getRole().name());
    }
}