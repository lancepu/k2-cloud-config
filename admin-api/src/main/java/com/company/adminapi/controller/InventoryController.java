package com.company.adminapi.controller;

import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.models.Inventory;
import com.company.adminapi.viewmodel.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InventoryViewModel> getAllInventories(){
        return serviceLayer.getAllInventories();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody @Valid Inventory inventory){
        return serviceLayer.addInventory(inventory);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InventoryViewModel getInventory(@PathVariable int id) {
        return serviceLayer.getInventory(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InventoryViewModel updateInventory(@RequestBody @Valid Inventory inventory, @PathVariable int id) {
        if(id!= inventory.getInventoryId()){
            throw new IllegalArgumentException("This inventory ID was not found...");
        }
        return serviceLayer.updateInventory(inventory);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteInventory(@PathVariable int id) {
        return serviceLayer.deleteInventory(id);
    }
}
