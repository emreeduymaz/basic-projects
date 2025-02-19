package online.auction.system.service;

import online.auction.system.model.Item;
import online.auction.system.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ItemService {

    @Autowired
    private ItemRepository repo;

    public List<Item> getAll(){
        return repo.findAll();
    }

    public Optional<Item> getById(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
            return repo.findById(id);
        }
        throw new ClassNotFoundException("Item does not Exists!");
    }

    public Item addItem(Item item){
        return repo.save(item);
    }

    public Item updateItem(Item item) throws ClassNotFoundException {
        if(repo.existsById(item.getId())){
            return repo.save(item);
        }
        throw new ClassNotFoundException("Item does not Exists!");
    }

    public void deleteById(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
             repo.deleteById(id);
            return;
        }
        throw new ClassNotFoundException("Item Does not Exists!");
    }
}
