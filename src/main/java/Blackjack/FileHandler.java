package Blackjack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class FileHandler implements FileHandlerInterface {

    public List<List<String>> readFile(String filename, String splitter) throws FileNotFoundException {
        List<List<String>> lines = new ArrayList<List<String>>();
        File file = Paths.get(filename).toFile();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineInfo = line.split(splitter);
            lines.add(Arrays.asList(lineInfo));
        }
        scanner.close();
        return lines; 
    }

    public void writeToFile(String filename, String write, boolean append) throws FileNotFoundException {
        File file = Paths.get(filename).toFile();
        try {
            FileWriter f = new FileWriter(file, append);
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter writer = new PrintWriter(b);
            writer.println(write);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> getUserNamesFromFile(String value) throws FileNotFoundException {
        //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
        //File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
        //Scanner scanner = new Scanner(filename);
        List<List<String>> file = readFile("src/main/resources/DataBlackjack.txt", ",");
        Map<String, String> UserNameMap = new HashMap<>();

        if (value.equals("Password")) {
            /*
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                String UserName = lineInfo[0];
                String PassWord = lineInfo[1];

                UserNameMap.put(UserName,PassWord);
            } */
            for (List<String> list : file) {
                String userName = list.get(0);
                String passWord = list.get(1);

                UserNameMap.put(userName, passWord);
            }
        }
        if (value.equals("Balance")) {
            /*
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                String UserName = lineInfo[0];
                String Balance = lineInfo[2];

                UserNameMap.put(UserName,Balance);
            } */
            for (List<String> list : file) {
                String userName = list.get(0);
                String balance = list.get(2);

                UserNameMap.put(userName, balance);
            }
        }
        //scanner.close();
        return UserNameMap;   
    }

    public void registerUserToFile(String userName, String password) throws FileNotFoundException {
        //File filename1 = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
        //File filename = new File(FileHandler.class.getResource("DataBlackjack").getFile() + "DataBlackjack.txt");
        /*
        File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
        try {
            FileWriter f = new FileWriter(filename, true);
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter writer = new PrintWriter(b);
            writer.println(UserName+","+Password+",100.0");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } */
        String registerUser = userName + "," + password + ",100.0"; 
        writeToFile("src/main/resources/DataBlackjack.txt", registerUser, true);
    }

    public boolean CheckRegisterOrLogin(String string, String UserName, String Password) {
        if (string.equals("Register")) {
            return validateUserAtRegister(UserName, Password);
        }
        if (string.equals("Login")) {
            return validateUserAtLogin(UserName, Password);
        }
        else return false;
    }

    private boolean validatePassword(String UserName, String Password) {
        if (Password.length() < 10 || UserName.equals(Password) || Password.toLowerCase().contains("password") || Password.contains(",") || Password.contains(".")) {
            return false;
        }
        ArrayList<Character> array = new ArrayList<>();
        for (Character c : Password.toCharArray()) {
            array.add(c);
        }
        return array.stream().anyMatch(Character -> java.lang.Character.isDigit(Character)); 
    }

    private boolean validateUserAtRegister(String UserName, String Password) {
        try {
            Map<String,String> UserNameMap = getUserNamesFromFile("Password");
            if(!UserNameMap.containsKey(UserName) && validatePassword(UserName, Password) && !UserName.contains(",") && !UserName.contains(".")){
                registerUserToFile(UserName,Password);       
            } 
            return !UserNameMap.containsKey(UserName) && validatePassword(UserName, Password) && !UserName.contains(",") && !UserName.contains(".");  

        } catch (Exception e) {
            return false;
        }  
    }

    //Metode for å sjekke passord opp mot inntastet brukernavn
    private boolean validateUserAtLogin(String UserName, String Password) {
        try {
            Map<String,String> UserNameMap = getUserNamesFromFile("Password"); //igjen, vi må lage en fil
            return (UserNameMap.containsKey(UserName) && UserNameMap.get(UserName).equals(Password) && !UserName.contains(",") && !UserName.contains("."));
        } catch (Exception e) {
            return false;
        }
    }

    public String getBalance(String Username) {
        try {
            Map<String,String> UserNameMap = getUserNamesFromFile("Balance"); 
            return UserNameMap.get(Username);
        } catch (Exception e) {
            return null;
        }
    }

    public void UpdateBalance(String username, double balance) throws FileNotFoundException {
        /*
        try {
            //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
            //File filename = new File(CardDeck.class.getResource("DataBlackjack").getFile() + "DataBlackjack.txt");
            File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
            List<String> fileList = new ArrayList<>();
            //Scanner scanner = new Scanner(filename);
            /*
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                fileList.add(line);
            }
            scanner.close();  
            String newFileString = "";
            for (String line : fileList) {
                if(line.contains(Username)) {
                    String[] info = line.split(",");
                    newFileString += info[0] + "," + info[1] + "," + Balance + "\n";
                }
                else newFileString += line + "\n";
            }
            FileWriter f = new FileWriter(filename, false);
            
            f.write(newFileString);
            f.flush();
            f.close();  

        } catch (Exception e) {
            return;     
        } */

        List<List<String>> file = readFile("src/main/resources/DataBlackjack.txt", ",");
        writeToFile("src/main/resources/DataBlackjack.txt", "", false);

        for (List<String> line : file) {
            if (line.get(0).equals(username)) {
                line.set(2, "" + balance);
            }

            String newFileString = line.get(0) + "," + line.get(1) + "," + line.get(2);
            if (file.indexOf(line) == 0) {
                writeToFile("src/main/resources/DataBlackjack.txt", newFileString, false);
            }
            else writeToFile("src/main/resources/DataBlackjack.txt", newFileString, true);
        }
    }

    public List<String> updateTopPlayers() {
        List<String> topPlayers = new ArrayList<>(10);

        try {
            Map<String,String> NameBalance = getUserNamesFromFile("Balance");
            for (Map.Entry<String,String> entry : NameBalance.entrySet()) {
                topPlayers.add(entry.getKey() + "," + entry.getValue());  
            }
            topPlayers.sort((o1, o2)-> ((Integer.parseInt(o2.split("[,.]")[1])) - (Integer.parseInt(o1.split("[,.]")[1]))));
            
            return topPlayers;
        } catch (FileNotFoundException e) {
            return topPlayers;
        }
    }
    
    public void removePlayerJustForTestUse(String username) throws FileNotFoundException {
        //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
        List<List<String>> file = readFile("src/main/resources/DataBlackjack.txt", ",");
        writeToFile("src/main/resources/DataBlackjack.txt", "", false);

        for (List<String> line : file) {
            if (! line.get(0).equals(username)) {
                String newFileString = line.get(0) + "," + line.get(1) + "," + line.get(2);

                if (file.indexOf(line) == 0) {
                    writeToFile("src/main/resources/DataBlackjack.txt", newFileString, false);
                }
                else writeToFile("src/main/resources/DataBlackjack.txt", newFileString, true);
            }
        }
        /*
        File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
        List<String> players = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(filename);
            while (scanner.hasNextLine()) {
                String info = scanner.nextLine();
                if (!info.contains(Username)){
                    players.add(info);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return;
        }
            try {
                FileWriter f = new FileWriter(filename, false);
                for (String string : players) {
                    f.write(string + "\n");
                }
                f.flush();
                f.close();
            } catch (IOException e) {
                return;
            }
        */        
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileHandler handler = new FileHandler();
        handler.UpdateBalance("Christian", 402.0);
        //System.out.println(handler.readFile("src/main/resources/DataBlackjack.txt"));
        //handler.registerUserToFile("MoneyMaker", "heipodeg234");
        //handler.removePlayerJustForTestUse("MøneyMaker");
        //System.out.println(FileHandler.class.getResource("DataBlackjack").getFile());
        //System.out.println(Path.of(FileHandler.class.getResource("CardDeck/").getFile() + "DataBlackjack.txt"));
        //File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
    }
}
