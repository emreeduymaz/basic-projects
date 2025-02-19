package online.auction.system.service;

import online.auction.system.model.Users;
import online.auction.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;



    public List<Users> getAll(){
        return repo.findAll();
    }

    public Optional<Users> getById(Long id){
        return repo.findById(id);
    }

    public Users addUser(Users user){
        return repo.save(user);
    }

    public Users updateUser(Users user){
        if(repo.existsById(user.getId())){
            return repo.save(user);
        }
        throw new IllegalArgumentException("User does not Exists!");
    }

    public void deleteUser(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
            repo.deleteById(id);
        }
        else{
            throw new ClassNotFoundException("Users does not Exists!");
        }
    }

    public boolean existsById(Long id){
        return repo.existsById(id);
    }

}
