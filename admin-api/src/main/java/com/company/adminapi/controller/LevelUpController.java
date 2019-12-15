package com.company.adminapi.controller;

import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.models.LevelUp;
import com.company.adminapi.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/levelup")
public class LevelUpController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUpViewModel> getAllLevelUps(){
        return serviceLayer.getAllLevelUps();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public LevelUpViewModel createLevelUp(@RequestBody @Valid LevelUp levelUp){
        return serviceLayer.addLevelUp(levelUp);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUpViewModel getLevelUp(@PathVariable int id) {
        return serviceLayer.getLevelUp(id);
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUpViewModel getLevelUpByCustomerId(@PathVariable int customerId) {
        return serviceLayer.getLevelUpByCustomerId(customerId);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUpViewModel updateLevelUp(@RequestBody @Valid LevelUp levelUp, @PathVariable int id) {
        if(id!= levelUp.getLevelUpId()){
            throw new IllegalArgumentException("This levelUp ID was not found...");
        }
        return serviceLayer.updateLevelUp(levelUp);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteLevelUp(@PathVariable int id) {
        return serviceLayer.deleteLevelUp(id);
    }
}