/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usermanagement;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dilshan
 */
public class FXMLDocumentController implements Initializable {
    
   
    private ResultSet rs;
    private String cindex="0";
    private DbHandler dbHandler = new DbHandler();
    private Label label;
    @FXML
    private TableColumn<?, ?> uid;
    @FXML
    private TableColumn<?, ?> uname;
    @FXML
    private TableColumn<?, ?> ucity;
    @FXML
    private Button adduser;
    @FXML
    private TextField addname;
    @FXML
    private TextField addcity;
  
    @FXML
    private TableView<User> udtable;
    @FXML
    private Label updatelable;
    @FXML
    private Button uupdate;
    @FXML
    private Button ucancel;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
      ObservableList<User> observableList = FXCollections.observableArrayList(
 //one nm obeject add karanna puluwan
        //  new User("2","Namae1", "city")
    
    );
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       dbHandler.CreateTables();
       uname.setCellValueFactory( new PropertyValueFactory<>("name") );
         ucity.setCellValueFactory( new PropertyValueFactory<>("city") );
       uid.setCellValueFactory( new PropertyValueFactory<>("id") );
       udtable.setItems(observableList);
       dbHandler.addUser("Dilshan", "moratuwa");
       try {
            rs=dbHandler.getUsers();
                 while(rs.next()) {
                     User u = new User(rs.getString("id"), rs.getString("name"),rs.getString("city"));
             udtable.getItems().add(u);
             
          }
        } catch (Exception e) {
        }
       
       udtable.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
               try {
                  
                   if (event.getButton()==MouseButton.PRIMARY&&event.getClickCount()==2) {
                                                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Waening");
            alert.setHeaderText("Are you sure to delete \n"+udtable.getSelectionModel().getSelectedItem().getName()+" ?");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
                  updatelable.setVisible(true);
                       adduser.setVisible(false);
                       uupdate.setVisible(true);
                       ucancel.setVisible(true);
                       addname.setText(udtable.getSelectionModel().getSelectedItem().getName());
                       addcity.setText(udtable.getSelectionModel().getSelectedItem().getCity());
                       updatelable.setText("Enter new Value For "+udtable.getSelectionModel().getSelectedItem().getName());
                       cindex=udtable.getSelectionModel().getSelectedItem().getId().trim();
                 
            }else if(rs==ButtonType.CANCEL){
            cancelUpdate();
            }
});
                      
                       
                       
                   }else if(event.getButton()==MouseButton.SECONDARY){
                                                       Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Waening");
            alert.setHeaderText("Are you sure to delete \n"+udtable.getSelectionModel().getSelectedItem().getName()+" ?");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
                 String i =udtable.getSelectionModel().getSelectedItem().getId().trim();
                 if(dbHandler.DeleteUser(i)){
                refershTable();
            }
                 
            }
}); 
                   }
                   
                   
                 
        
                   
                   
                   
                    
               } catch (Exception e) {
               }
         
           
           
           
           }
           
           
           
       });
        
       
       
       
       
    }    
 
    @FXML
    private void addnewUser(MouseEvent event) {
        
        if (addname.getText().isEmpty()) {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter  name");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
});  
        }else if(addcity.getText().isEmpty()){
                       Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter  City");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
}); 
            
        }else{
            if (dbHandler.addUser(addname.getText(), addcity.getText())) {
                addname.clear();
                       addcity.clear();
                refershTable();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
}); 
            }

        }
        
        
        
    }
      private void refershTable(){
          udtable.getItems().clear();
         try {
            rs=dbHandler.getUsers();
                 while(rs.next()) {
                     User u = new User(rs.getString("id"), rs.getString("name"),rs.getString("city"));
             udtable.getItems().add(u);
             
          }
        } catch (Exception e) {
        }
   }

    @FXML
    private void updateUser(MouseEvent event) {

            if (dbHandler.UpdateUser(cindex,addname.getText(), addcity.getText())) {
                cancelUpdate();
                refershTable();
                
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
}); 
            }

        }
        
        
  

    @FXML
    private void updateCancel(MouseEvent event) {
         cancelUpdate();
        
    }
    private void cancelUpdate(){
        updatelable.setVisible(false);
                       adduser.setVisible(true);
                       uupdate.setVisible(false);
                       ucancel.setVisible(false);
                       addname.clear();
                       addcity.clear();
    }
   
    
}
