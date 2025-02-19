package online.auction.system.controller;

import online.auction.system.model.Item;
import online.auction.system.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Item")
public class ItemController {
    @Autowired
    private ItemService service;


    @GetMapping("/getAllItem")
    public ResponseEntity<List<Item>> findAll(){
        return  ResponseEntity.ok(service.getAll());
    }

    @GetMapping("ItemInfo/{requestedId}")
    public ResponseEntity<Optional<Item>> findById(@PathVariable("requestedId") Long requestedId) {
        try {
            return ResponseEntity.ok(service.getById(requestedId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        Item addedItem=service.addItem(item);
        if(addedItem!=null){
            return new ResponseEntity<>(addedItem,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateItem")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        try {
            return ResponseEntity.ok(service.updateItem(item));
        } catch(ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
