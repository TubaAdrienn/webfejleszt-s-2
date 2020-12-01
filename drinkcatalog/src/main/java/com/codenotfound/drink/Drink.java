package com.codenotfound.drink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Drink implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String instructions;
    private String type;

    // avoid this "No default constructor for entity"
    public Drink() {
    }

    public Drink(Long id, String name, String instructions, String type) {
        this.id = id;
        this.name = name;
        this.instructions=instructions;
        this.type=type;
    }

    public Drink(String name, String instructions, String type) {
        this.name = name;
        this.instructions=instructions;
        this.type=type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id) &&
                Objects.equals(name, drink.name) &&
                Objects.equals(instructions, drink.instructions) &&
                Objects.equals(type, drink.type);
    }
}
