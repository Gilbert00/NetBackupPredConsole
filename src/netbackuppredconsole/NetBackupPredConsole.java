/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netbackuppredconsole;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.beans.value.*;
//import static java.util.stream.Collectors.*;
import java.util.stream.Stream;
import java.io.*;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author User
 */

enum ColumnType {
    INT, STR, DATE
}

enum BaseColumn {
   Alias(ColumnType.STR),
   Name(ColumnType.STR),
   Login(ColumnType.STR),
   Password(ColumnType.STR),
   Path(ColumnType.STR)
   ;
   
   private ColumnType type;
   
   BaseColumn(ColumnType t) {type = t; }
   
   ColumnType getType(){ return type; }
}

public class NetBackupPredConsole extends Application {
    
//    protected static final String COMMA_DELIMITER = ",";
//    protected static final String CSV_FILE = "Q:\\Java-School\\Project_2_DSWA\\flights_small.csv"; 
//    protected static final String CSV_FILE = "Q:\\Java-School\\nb_domain.csv"; 
//    protected static FormattedOutput fout;
    protected Stream<String> linesStream;
    protected Stream<String[]> linesOfArray;
    
    protected HashNB hashNB;
    protected static String aliasSelected = "";
    
//    Map<String,HashVal> hash;
//    List<Map.Entry<String,HashVal>> list;

    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        String bold = "-fx-font-weight: bold";

        Label lbNameHead = new Label("Name:");
        Label lbLoginHead = new Label("Login:");
        Label lbPswdHead = new Label("Password:");
        Label lbName = new Label();
        Label lbLogin = new Label();
        Label lbPswd = new Label();
        lbName.setStyle(bold);
        lbLogin.setStyle(bold);
        lbPswd.setStyle(bold);
        
        hashNB = new HashNB();
        hashNB.SetHash();
                
        ObservableList<String> nbNames = FXCollections.observableArrayList();
        hashNB.SetKeyList(nbNames);
        
//       ObservableList<String> nbNames = 
//                FXCollections.observableArrayList("Old ChO", "New Cho", "Ndj", "Rzn");
        ListView<String> lvNames = new ListView<String>(nbNames);
        double lvW = 100;
        double lvH = 70;
        lvNames.setPrefSize(lvW, lvH);
        MultipleSelectionModel<String> lvSelModel = lvNames.getSelectionModel();
        lvSelModel.selectedItemProperty().addListener(
                new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> changed, 
                        String oldVal, String newVal) {
                    //TO-DO !!!
                    aliasSelected = newVal;
                    HashNB.HashVal val = hashNB.getHashVal(aliasSelected);
                    lbName.setText(val.getName());
                    lbLogin.setText(val.getLogin());
                    lbPswd.setText(val.getPswd());
                  System.out.println(newVal);
                }
                });
        
//        SetHash();
        
        Button btnOK = new Button();
        btnOK.setText("OK");
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("OK");
                HashNB.HashVal val = hashNB.getHashVal(aliasSelected);
                content.putString(val.getPswd());
                content.putHtml(val.getPswd());
                clipboard.setContent(content);
            }
        });
        
        Button btnCancel = new Button();
        btnCancel.setText("Cancel");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cancel");
            }
        });

//        Label lbNameHead = new Label("Name:");
//        Label lbLoginHead = new Label("Login:");
//        Label lbPswdHead = new Label("Password:");
//        Label lbName = new Label();
//        Label lbLogin = new Label();
//        Label lbPswd = new Label();
        
        double spacingVboxBtn = 8;
        HBox hboxBtn = new HBox(spacingVboxBtn);        

        double spacingVboxLabel = 8;
        VBox vboxLabel = new VBox(spacingVboxLabel);        
        
        double spacingVbox0 = 8;
        VBox vbox0 = new VBox(spacingVbox0);
        
        double hgap = 10;
        double vgap = 10;
//        StackPane root = new StackPane();
        FlowPane root = new FlowPane(hgap, vgap);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lvNames, vbox0);
//        root.getChildren().add(btn);

        vbox0.getChildren().addAll(vboxLabel, hboxBtn);
        hboxBtn.getChildren().addAll(btnOK, btnCancel);
        vboxLabel.getChildren().addAll(lbNameHead, lbName, lbLoginHead, lbLogin, lbPswdHead, lbPswd);

        double sceneW = 400;
        double sceneH = 300;
        
        Scene scene = new Scene(root, sceneW, sceneH);
        
        primaryStage.setTitle("NB Domains");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        launch(args);
    }
    
}
