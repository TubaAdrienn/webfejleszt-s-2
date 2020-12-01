package com.codenotfound.drink;

import com.codenotfound.error.DrinkNotFoundException;
import com.codenotfound.error.DrinkUnSupportedFieldPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DrinkController {

    @Autowired
    private DrinkRepository repository;

    // Find
    @GetMapping("/drinks")
    List<Drink> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/drinks")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Drink newDrink(@RequestBody Drink newDrink) {
        return repository.save(newDrink);
    }

    // Find
    @GetMapping("/drinks/{id}")
    Drink findOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DrinkNotFoundException(id));
    }

    // Save or update
    @PutMapping("/drinks/{id}")
    public Drink saveOrUpdate(@RequestBody Drink newDrink, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setName(newDrink.getName());
                    x.setInstructions(newDrink.getInstructions());
                    x.setType(newDrink.getType());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newDrink.setId(id);
                    return repository.save(newDrink);
                });
    }

    @DeleteMapping("/drinks/{id}")
    void deleteDrink(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/drinks")
    void deleteAll(){
        repository.deleteAll();
    }

}
