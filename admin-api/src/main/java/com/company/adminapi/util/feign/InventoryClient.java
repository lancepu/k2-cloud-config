package com.company.adminapi.util.feign;

import com.company.adminapi.models.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {



    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    Inventory createInventory(@RequestBody @Valid Inventory inventory);

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    Inventory getInventory(@PathVariable int id);

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    List<Inventory> getAllInventories();

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    Inventory updateInventory(@RequestBody @Valid Inventory inventory, @PathVariable int id);

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
    String deleteInventory(@PathVariable int id);
}
