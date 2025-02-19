package online.auction.system.service;

import online.auction.system.model.Auction;
import online.auction.system.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository repo;

    public List<Auction> getAll(){
        return repo.findAll();
    }

    public Optional<Auction> getById(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
            return repo.findById(id);
        }
        throw new ClassNotFoundException("Auction does not Exists!");
    }

    public Auction addAuction(Auction auction){
        return repo.save(auction);
    }

    public Auction update(Auction auction) throws ClassNotFoundException {
        if(repo.existsById(auction.getId())){
            return repo.save(auction);
        }
        throw new ClassNotFoundException("Auction does not Exists!");
    }

    public void deleteAuction(Long id) throws ClassNotFoundException {
        if(repo.existsById(id)){
             repo.deleteById(id);
             return;
        }
        throw new ClassNotFoundException("Auction Does Not Exists!");
    }

}
