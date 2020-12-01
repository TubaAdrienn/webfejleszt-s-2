package com.codenotfound.primefaces;

import com.codenotfound.drink.Drink;
import com.codenotfound.drink.DrinkController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.io.IOException;
import java.util.*;


@Controller("submitController")
@SessionScope
public class SubmitController {

    String drinkUrl = "http://localhost:8080/drinks";
    JSONObject drinkJsonObject;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();
    public ArrayList<Drink> drinks = new ArrayList<Drink>();
    private String msg = "Nope.";
    private boolean editOn=false;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Drink drink = new Drink();

    public Long getDrinkId() {
        return this.drink.getId();
    }

    public void setDrinkId(Long id) {
        this.drink.setId(id);
    }

    public void setDrinkName(String name) {
        this.drink.setName(name);
    }

    public void setDrinkInstr(String instr) {
        this.drink.setInstructions(instr);
    }

    public void setDrinkType(String type) {
        this.drink.setType(type);
    }

    public String getDrinkName() {
        return this.drink.getName();
    }

    public String getDrinkInstr() {
        return this.drink.getInstructions();
    }

    public String getDrinkType() {
        return this.drink.getType();
    }

    public void saveDrink() throws IOException {
        if (this.drink.getName() != null && this.drink.getType() != null && this.drink.getInstructions() != null) {
            if (!this.drink.getName().isEmpty() && !this.drink.getType().isEmpty() && !this.drink.getInstructions().isEmpty()) {
                if (drinkExists() == false) {
                    createJson();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> request =
                            new HttpEntity<String>(drinkJsonObject.toString(), headers);

                    String personResultAsJsonStr =
                            restTemplate.postForObject(drinkUrl, request, String.class);
                    JsonNode root = objectMapper.readTree(personResultAsJsonStr);
                    drink = new Drink();
                    getAllDrinks();
                    msg="Success.";
                }
                else{
                  msg="Nope";
                }
            }
        }
    }

    public void updateDrink() throws IOException {
        createJson();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<String>(drinkJsonObject.toString(), headers);

        restTemplate.put(drinkUrl+"/"+drink.getId(), request);
        drink = new Drink();
        getAllDrinks();
    }

    private boolean drinkExists() {
        if (drinks != null) {
            return drinks.stream().anyMatch(x -> x.getName().equals(this.getDrinkName())
                    && x.getType().equals(this.getDrinkType()) && x.getInstructions().equals(this.getDrinkInstr()));
        } else return true;
    }

    private Map<String, String> createPatchBody() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", this.drink.getId().toString());
        map.put("name", this.drink.getName());
        map.put("instructions", this.drink.getInstructions());
        map.put("type", this.drink.getType());
        return map;
    }

    private void createJson() {
        drinkJsonObject = new JSONObject();
        drinkJsonObject.put("name", drink.getName());
        drinkJsonObject.put("instructions", drink.getInstructions());
        drinkJsonObject.put("type", drink.getType());
    }

    public Drink[] getDrinks() throws IOException {
        ResponseEntity<Drink[]> response =
                restTemplate.getForEntity(
                        drinkUrl,
                        Drink[].class);
        Drink[] drinks = response.getBody();
        return drinks;
    }

    public void editDrink(Drink drink) {
        BeanUtils.copyProperties(drink, this.drink());
    }

    public Drink drink() {
        if (drink == null) {
            drink = new Drink();
        }
        return drink;
    }

    public void deleteDrink(Drink drink) throws IOException {
        HttpEntity<String> request =
                new HttpEntity<String>(headers);
        restTemplate.delete(drinkUrl + "/" + drink.getId(), request);
        getAllDrinks();
    }

    @PostConstruct
    public void getAllDrinks() throws IOException {
        if (!drinks.isEmpty()) {
            this.drinks.clear();
        }
        drinks = new ArrayList<Drink>(Arrays.asList(this.getDrinks()));
    }
}
