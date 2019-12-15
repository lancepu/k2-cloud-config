package com.company.adminapi.util.feign;

import com.company.adminapi.models.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {



    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp);

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.GET)
    LevelUp getLevelUp(@PathVariable int id);

    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    List<LevelUp> getAllLevelUps();

    @RequestMapping(value = "/levelup/customer/{customerId}", method = RequestMethod.GET)
    LevelUp getLevelUpByCustomerId(@PathVariable int customerId);

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.PUT)
    LevelUp updateLevelUp(@RequestBody @Valid LevelUp levelUp, @PathVariable int id);

    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.DELETE)
    String deleteLevelUp(@PathVariable int id);
}
