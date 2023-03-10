/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netbackuppredconsole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class HashNB {
    
    static final int AliasID = BaseColumn.Alias.ordinal();
    static final int NameID = BaseColumn.Name.ordinal();
    static final int LoginID = BaseColumn.Login.ordinal();
    static final int PasswordID = BaseColumn.Password.ordinal();    
    static final int PathID = BaseColumn.Path.ordinal();    
    
    protected static final String COMMA_DELIMITER = ",";
//    protected static final String CSV_FILE = "Q:\\Java-School\\Project_2_DSWA\\flights_small.csv"; 
    protected static final String CSV_FILE = "Q:\\Java-School\\nb_domain.csv"; 
//    protected static FormattedOutput fout;
    protected Stream<String> linesStream;
    protected Stream<String[]> linesOfArray;
    
    public class HashVal {
        private String name;
        private String login;
        private String password;
        private String path;

        public HashVal(String name, String login, String password, String path) {
            this.name = name;
            this.login = login;
            this.password = password;
            this.path = path;
        }
        
        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public String getPswd() {
            return password;
        }

        public String getPath() {
            return path;
        }

    }
    Map<String,HashVal> hash;
    List<Map.Entry<String,HashVal>> list;   

    public void SetHash() throws IOException {
   
        readInput();

        hash = new TreeMap<String,HashVal>();  
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
        
        System.out.println("Hash Size:" + hash.size());        
    }
    
    public void SetKeyList(ObservableList<String> nbNames) {
      Set<Map.Entry<String,HashVal>> set = hash.entrySet();
      
      for (Map.Entry<String,HashVal> me : set) {
        nbNames.add(me.getKey());
      }
        
    }
    
    public HashVal getHashVal(String key) {
        return hash.get(key);
    }
    
    protected void readInput() throws IOException {
         
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
    


}


