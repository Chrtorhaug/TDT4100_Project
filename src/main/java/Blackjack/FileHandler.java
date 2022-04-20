package Blackjack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class FileHandler implements FileHandlerInterface {

    public boolean CheckRegisterOrLogin(String string, String UserName, String Password) {
        if (string.equals("Register")) {
            return validateUserAtRegister(UserName, Password);
        }
        if (string.equals("Login")) {
            return validateUserAtLogin(UserName, Password);
        }
        else return false;
    }

    public Map<String,String> getUserNamesFromFile(String value) throws FileNotFoundException {
        //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
        File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
        Scanner scanner = new Scanner(filename);
        Map<String, String> UserNameMap = new HashMap<>();

        if (value.equals("Password")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                String UserName = lineInfo[0];
                String PassWord = lineInfo[1];

                UserNameMap.put(UserName,PassWord);
            }
        }
        if (value.equals("Balance")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                String UserName = lineInfo[0];
                String Balance = lineInfo[2];

                UserNameMap.put(UserName,Balance);
            }
        }
        scanner.close();
        return UserNameMap;   
    }

    public void registerUserToFile(String UserName, String Password) {
        //File filename1 = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
        //File filename = new File(FileHandler.class.getResource("DataBlackjack").getFile() + "DataBlackjack.txt");
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
        }
    }

    private boolean validatePassword(String UserName, String Password) {
        if (Password.length() < 10 || UserName.equals(Password) || Password.toLowerCase().contains("password") || Password.contains(",") || Password.contains(".")) {
            return false;
        }
        ArrayList<Character> array = new ArrayList<>();
        for (Character c : Password.toCharArray()) {
            array.add(c);
        }
        if (!array.stream().anyMatch(Character -> java.lang.Character.isDigit(Character))) {
            return false;
        }
        else return true;  
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

    public void UpdateBalance(String Username, double Balance) {
        try {
            //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
            //File filename = new File(CardDeck.class.getResource("DataBlackjack").getFile() + "DataBlackjack.txt");
            File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
            List<String> fileList = new ArrayList<>();
            Scanner scanner = new Scanner(filename);

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
        }
    }

    public List<String> updateTopPlayers() {
        List<String> topPlayers = new ArrayList<>(10);

        try {
            Map<String,String> NameBalance = getUserNamesFromFile("Balance");
            for (Map.Entry<String,String> entry : NameBalance.entrySet()) {
                topPlayers.add(entry.getKey()+","+entry.getValue());  
            }
            topPlayers.sort((o1, o2)-> ((Integer.parseInt(o2.split("[,.]")[1])) - (Integer.parseInt(o1.split("[,.]")[1]))));
            
            return topPlayers;
        } catch (FileNotFoundException e) {
            return topPlayers;
        }
    }
    
    public void removePlayerJustForTestUse(String Username) {
        //File filename = new File("src\\main\\resources\\DataBlackjack.txt").getAbsoluteFile();
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
            
    }

    public static void main(String[] args) {
        FileHandler handler = new FileHandler();
        handler.removePlayerJustForTestUse("MoneyMaker");
        //System.out.println(FileHandler.class.getResource("DataBlackjack").getFile());
        //System.out.println(Path.of(FileHandler.class.getResource("CardDeck/").getFile() + "DataBlackjack.txt"));
        File filename = Paths.get("src/main/resources/DataBlackjack.txt").toFile();
        System.out.println(filename.exists());
    }
}
