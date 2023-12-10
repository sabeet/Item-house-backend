package dev.sabeet.todoi.controller;

import dev.sabeet.todoi.model.Item;
import dev.sabeet.todoi.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Item>> getAllItems(){
        try {
            List<Item> itemList = new ArrayList<>();
            itemRepo.findAll().forEach(itemList::add);

            if(itemList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getItemByID/{id}")
    public ResponseEntity<Item> getItemByID(@PathVariable Long id){
        Optional<Item> itemData = itemRepo.findById(id);

        if(itemData.isPresent()){
            return new ResponseEntity<>(itemData.get(),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
        Item item1 = itemRepo.save(item);
        return new ResponseEntity<>(item1, HttpStatus.OK);
    }

    @PostMapping("/updateItemById/{id}")
    public ResponseEntity<Object> updateItemByID(@PathVariable Long id, @RequestBody Item newItemData){
        Optional<Item> itemData = itemRepo.findById(id);

        if(itemData.isPresent()){
            Item updatedItemData = itemData.get();
            updatedItemData.setDescription(newItemData.getDescription());
            updatedItemData.setTitle(newItemData.getTitle());
            updatedItemData.setOwner(newItemData.getOwner());

            Item itemObj = itemRepo.save(updatedItemData);
            return new ResponseEntity<>(itemObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteItemById/{id}")
    public ResponseEntity<HttpStatus> deleteItemByID(@PathVariable Long id){
        itemRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
