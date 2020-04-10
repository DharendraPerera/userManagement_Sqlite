/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usermanagement;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Dilshan
 */
public class User {
    private SimpleStringProperty id,name,city;

    public User(String id, String name, String city) {
         this.name = new SimpleStringProperty(name) ;
        this.city = new SimpleStringProperty(city);
        this.id = new SimpleStringProperty(id);
    }
    

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCity() {
        return city.get();
    }
    
    
}
