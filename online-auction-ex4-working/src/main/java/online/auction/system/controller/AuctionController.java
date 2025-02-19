package online.auction.system.controller;

import online.auction.system.model.Auction;
import online.auction.system.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService service;

    @GetMapping("/getAllAuctions")
    public ResponseEntity<List<Auction>> findAll(){
        return  ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getAuction/{id}")

    public ResponseEntity<Optional<Auction>> findById(@PathVariable("id") Long requestedId) {
        try {
            return ResponseEntity.ok(service.getById(requestedId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/createAuction")
    public ResponseEntity<Auction> addUser(@RequestBody Auction auction){
        Auction addedAuction=service.addAuction(auction);
        if(addedAuction!=null){
            return new ResponseEntity<>(addedAuction,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateAuction")
    public ResponseEntity<Auction> updateAuction(@RequestBody Auction auction) {
        try {
            return ResponseEntity.ok(service.update(auction));
        } catch(ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/deleteAuction/{id}")
    public ResponseEntity deleteAuction(@PathVariable Long id){
        try {
            service.deleteAuction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
