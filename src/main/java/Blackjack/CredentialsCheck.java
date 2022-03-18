package Blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CredentialsCheck {



    public CredentialsCheck(String string,String UserName, String Password){
        if (string.equals("Register")){
            
        }
        if (string.equals("Login")){

        }
    }

    private Map<String,String> getUserNamesFromFile() throws FileNotFoundException {
        File filename = new File("TDT4100_prosjekt_jensesk\\src\\main\\java\\Blackjack\\DataBlackjack.txt").getAbsoluteFile();
        Scanner scanner = new Scanner(filename);
        //Scanner scanner = new Scanner(new File("C:\\Users\\Jens Eggen Skaug\\.vscode\\Prosjekt\\TDT4100_prosjekt_jensesk\\src\\main\\java\\Blackjack\\DataBlackjack.txt"));
        Map<String, String> UserNameMap = new HashMap<>();
        //Files.lines(Paths.get(getClass().getResource("DataBlackjack.txt.toURI"))).map(l -> l.split("\t")).get(0);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] lineInfo = line.split(",");
            String UserName = lineInfo[0];
            String PassWord = lineInfo[1];

            UserNameMap.put(UserName,PassWord);
        }
        scanner.close();
        return UserNameMap;   
    }

    private void registerUserToFile(String UserName, String Password) {
        File filename = new File("TDT4100_prosjekt_jensesk\\src\\main\\java\\Blackjack\\DataBlackjack.txt").getAbsoluteFile();
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.println(UserName+","+Password+"10");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean validatePassword(String UserName,String Password) {
        if (Password.length()<10){
            return false;
        }
        if (UserName.equals(Password)){
            return false;
        }
        if (Password.toLowerCase().contains("passord")){
            return false;
        }
        ArrayList<Character> array = new ArrayList<>();
        for (Character c : Password.toCharArray()) {
            array.add(c);
        }
        if (!array.stream().anyMatch(Character -> java.lang.Character.isDigit(Character))){
            return false;
        }
        else { return true;}
        
        
    }

    public boolean validateUserAtRegister(String UserName, String Password) {
        try {
            Map<String,String> UserNameMap = getUserNamesFromFile(); //igjen, vi må lage en fil
            if(!UserNameMap.containsKey(UserName) && validatePassword(UserName, Password)){
                registerUserToFile(UserName,Password);
                
            } 
            return !UserNameMap.containsKey(UserName) && validatePassword(UserName, Password);
            
        } catch (Exception e) {
            return false;
        }
        
    }

    //Metode for å sjekke passord opp mot inntastet brukernavn

    public boolean validateUserAtLogin(String UserName, String Password) {
        try {
            Map<String,String> UserNameMap = getUserNamesFromFile(); //igjen, vi må lage en fil
            return (UserNameMap.containsKey(UserName) && UserNameMap.get(UserName).equals(Password));
        } catch (Exception e) {
            return false;
        }
        
    }


    public static void main(String[] args) {
        CredentialsCheck check = new CredentialsCheck();

        try {
            Map<String,String> maplist = check.getUserNamesFromFile();
            System.out.println(maplist.toString());
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("PassORd123".toLowerCase());
        
    }
    
}
