package online.auction.system.service;

import online.auction.system.model.Bids;
import online.auction.system.repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidsService {
    @Autowired
    private BidsRepository repo;

    public List<Bids> getAll(){
        return repo.findAll();
    }

    public Optional<Bids> getById(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
            return repo.findById(id);
        }
        throw new ClassNotFoundException("Bids does not Exists!");
    }

    public Bids addBids(Bids bid){
        return repo.save(bid);
    }

    public Bids updateBid(Bids bids) throws ClassNotFoundException {
        if(repo.existsById(bids.getId())){
            return repo.save(bids);
        }
        throw new ClassNotFoundException("Bid does not Exists!");
    }

    public void deleteById(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
            repo.deleteById(id);
            return;
        }
        throw new ClassNotFoundException("Bid Does not Exists!");
    }

}
