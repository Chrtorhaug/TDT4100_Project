package Blackjack;

import java.io.FileNotFoundException;
import java.util.Map;

public interface FileHandlerInterface {
    
    public boolean CheckRegisterOrLogin(String string, String UserName, String Password);
    public Map<String,String> getUserNamesFromFile(String value) throws FileNotFoundException;
    public String getBalance(String Username);
    public void registerUserToFile(String UserName, String Password);
    public void UpdateBalance(String Username, double Balance);
}
