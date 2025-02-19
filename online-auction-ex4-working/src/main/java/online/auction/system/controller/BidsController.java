package online.auction.system.controller;

import online.auction.system.model.Bids;
import online.auction.system.service.BidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Bid")
public class BidsController {
    @Autowired
    private BidsService service;
    @GetMapping("/getAllBids")
    public ResponseEntity<List<Bids>> findAll(){
        return  ResponseEntity.ok(service.getAll());
    }

    @GetMapping("BidsInfo/{requestedId}")
    public ResponseEntity<Optional<Bids>> findById(@PathVariable("requestedId") Long requestedId) {
        try {
            return ResponseEntity.ok(service.getById(requestedId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/addBidsToAuction")
    public ResponseEntity<Bids> addBid(@RequestBody Bids bid){
       Bids addedBid=service.addBids(bid);
        if(addedBid!=null){
            return new ResponseEntity<>(addedBid,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateBid")
    public ResponseEntity<Bids> updateBid(@RequestBody Bids bid) {
        try {
            return ResponseEntity.ok(service.updateBid(bid));
        } catch(ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/deleteBid/{id}")
    public ResponseEntity deleteBid(@PathVariable Long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
