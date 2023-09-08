package hr.algebra.bll.service;

import hr.algebra.dal.dao.UserRepository;
import hr.algebra.dal.entity.User;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository _userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return _userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        List<User> allDBUsers = _userRepository.findAll();
        Optional<User> optionalUser = allDBUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            return foundUser;
        } else {
            throw new CustomNotFoundException("User does not exist in the database!");
        }
    }

    @Override
    public User save(User user) {
        return _userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        _userRepository.deleteByUserName(username);
    }
}
