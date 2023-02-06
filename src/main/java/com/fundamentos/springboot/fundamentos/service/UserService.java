package com.fundamentos.springboot.fundamentos.service;


import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRespository;

    public UserService(UserRepository userRespository) {
        this.userRespository = userRespository;
    }

    @Transactional
    public void saveTransactional(List<User> users) {
        users.stream()
                .peek(user -> LOG.info("usuario insertado: " + user))
                .forEach(userRespository::save);

    }

    public List<User> getAllUsers() {
        return userRespository.findAll();
    }

    public User save(User newUser) {
        return userRespository.save(newUser);
    }

    public void delete(Long id) {
        userRespository.delete(new User(id));
    }

    public User update(User newUser, Long id) {
              return userRespository.findById(id)
                .map(user -> {
                            user.setEmail(newUser.getEmail());
                            user.setBirthDate(newUser.getBirthDate());
                            user.setName(newUser.getName());
                            return userRespository.save(user);
                        }
                ).get();
    }

}
