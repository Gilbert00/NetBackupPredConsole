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
    
    protected static final String COMMA_DELIMITER = ",";
//    protected static final String CSV_FILE = "Q:\\Java-School\\Project_2_DSWA\\flights_small.csv"; 
    protected static final String CSV_FILE = "Q:\\Java-School\\nb_domain.csv"; 
//    protected static FormattedOutput fout;
    protected Stream<String> linesStream;
    protected Stream<String[]> linesOfArray;
    
    static final int AliasID = BaseColumn.Alias.ordinal();
    static final int NameID = BaseColumn.Name.ordinal();
    static final int LoginID = BaseColumn.Login.ordinal();
    static final int PasswordID = BaseColumn.Password.ordinal();    
    static final int PathID = BaseColumn.Path.ordinal();    
    
    private class HashVal {
        String name;
        String login;
        String password;
        String path;

        public HashVal(String name, String login, String password, String path) {
            this.name = name;
            this.login = login;
            this.password = password;
            this.path = path;
        }
    }
    
    Map<String,HashVal> hash;
    List<Map.Entry<String,HashVal>> list;

    private void SetHash() throws IOException {
   
        readInput();

        hash = new TreeMap<>();  
        String[] record;
        String key;
        HashVal val;
        Iterator<String[]> itr = linesOfArray.iterator();
        while(itr.hasNext()){
              record = itr.next();
              key = record[AliasID];
              val = new HashVal(record[NameID], record[LoginID], 
                                record[PasswordID], record[PathID]);
              hash.put(key, val);
        }
        
//        hash.
    }
    
    
    protected void readInput() throws IOException {
 //       List<List<String>> records = new ArrayList<List<String>>();
//        String[] values;
 
        
// BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/input.txt")) 
// int buffer = 16384 * 16384;   1048576     
//        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            BufferedReader br = new BufferedReader(new FileReader(CSV_FILE));
            linesStream = br.lines();
            linesOfArray = linesStream.map(line -> line.split(COMMA_DELIMITER)).skip(1);
//           System.out.println("lines:" + linesStream.count());
//            System.out.println("linesOfArray:" + linesOfArray.count());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        Label lbNameHead = new Label("Name:");
        Label lbLoginHead = new Label("Login:");
        Label lbPswdHead = new Label("Password:");
        Label lbName = new Label();
        Label lbLogin = new Label();
        Label lbPswd = new Label();
        
        ObservableList<String> nbNames = 
                FXCollections.observableArrayList("Old ChO", "New Cho", "Ndj", "Rzn");
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
                    lbName.setText(newVal);
//                   System.out.println(newVal);
                }
                });
        
        SetHash();
        
        Button btnOK = new Button();
        btnOK.setText("OK");
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("OK");
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
    public static void main(String[] args) {
        launch(args);
    }
    
}
