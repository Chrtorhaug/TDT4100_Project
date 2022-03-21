package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    private HandComparator handComp;
    private FileHandler fileHandler = new FileHandler();
    private List<ImageView> firstHandImageViews= new ArrayList<>();
    private List<ImageView> dealerHandImageViews = new ArrayList<>();
    private List<ImageView> secondHandImageViews= new ArrayList<>();
    private List<ImageView> thirdHandImageViews = new ArrayList<>();

    @FXML
    private Label PlayerScore1, PlayerScore2, PlayerScore3, DealerScore, WelcomeSign, ShowBalance;

    @FXML
    private Text PlayerScoreText1, PlayerScoreText2, PlayerScoreText3, DealerScoreText;
    
    @FXML
    private Button LoginButton, RegisterButton, NewGameButton, BetButton, HitButton, HoldButton, SplitButton;

    @FXML
    private TextField NameField, PasswordField, BetField;

    @FXML
    private ImageView DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18,
                    CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18,
                    CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28, 
                    CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38;

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        handComp = new HandComparator();

        PlayerScore1.setText(" ");
        PlayerScore2.setVisible(false);
        PlayerScore3.setVisible(false);
        DealerScore.setText(" ");
        ShowBalance.setText(" ");
        PlayerScoreText2.setVisible(false);
        PlayerScoreText3.setVisible(false);
        WelcomeSign.setVisible(false);;

        NewGameButton.setDisable(true);
        BetButton.setDisable(true);
        HitButton.setDisable(true);
        HoldButton.setDisable(true);
        SplitButton.setDisable(true);
        BetField.setDisable(true);
    }

    private void updateCardHandPictures(List<ImageView> view, ActionEvent event) {
        List<List<ImageView>> hands = Arrays.asList(firstHandImageViews, secondHandImageViews, thirdHandImageViews, dealerHandImageViews);
        if (view.equals(dealerHandImageViews)) {
            if (event.getSource().equals(NewGameButton)) {
                for (int i = 0; i < dealer.getHand(0).size(); i++) {
                    view.get(i).imageProperty().set(null);  
                }
                dealer.newHand(deck);
                DealerPicture11.setImage(dealer.getHand(0).get(0).getCardPicture());
            }
            else {
                for (int i = 0; i < dealer.getHand(0).size(); i++) {
                    view.get(i).setImage(dealer.getHand(0).get(i).getCardPicture());
                }
            }
        }
        else {
            if (event.getSource().equals(NewGameButton)) {
                for (int i = 0; i < player.getHand(hands.indexOf(view)).size(); i++) {
                    view.get(i).imageProperty().set(null);  
                }
                if (view.equals(firstHandImageViews)) {
                    player.newHand(deck);
                }
                else return;
            } 
            if (event.getSource().equals(HitButton)) {
                player.addCard(deck);
            } 
            if (event.getSource().equals(SplitButton)) {
                view.get(1).imageProperty().set(null);
                hands.get(hands.indexOf(view)).get(0).imageProperty().set(player.getCard(hands.indexOf(view), 0).getCardPicture());
                hands.get(hands.indexOf(view) + 1).get(0).imageProperty().set(player.getHand(1).get(0).getCardPicture());
            }
            for (int i = 0; i < player.getHand(hands.indexOf(view)).size(); i++) {
                view.get(i).setImage(player.getCard(hands.indexOf(view), i).getCardPicture());
            }
        }
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getClass().equals(BlackJackDealer.class) && player.checkPlaying()) { // Hvis runden ikke er ferdig skal Dealer bare vise scoren til første kort 
            lb.setText(String.valueOf(pl.getHand(0).get(0).getValue()));
        }
        else {
            if (lb.equals(PlayerScore2)) {
                lb.setText(String.valueOf(pl.getScore(1)));
                PlayerScoreText2.setVisible(true);
                PlayerScore2.setVisible(true);
            }
            else lb.setText(String.valueOf(pl.getScore(0)));
        }
    }

    @FXML
    public void handleNewGame(ActionEvent newGameEvent) {
        if (deck.getSize() < 50) {
            this.deck = new CardDeck(5);
        }
        updateCardHandPictures(secondHandImageViews, newGameEvent);
        updateCardHandPictures(thirdHandImageViews, newGameEvent);
        updateCardHandPictures(firstHandImageViews, newGameEvent);
        updateCardHandPictures(dealerHandImageViews, newGameEvent);
        updateLabel(PlayerScore1, player);
        updateLabel(DealerScore, dealer);

        NewGameButton.setDisable(true);
        HitButton.setDisable(false);
        HoldButton.setDisable(false);
        PlayerScoreText2.setVisible(false);
        PlayerScore2.setVisible(false);

        if (player.canSplit(player.getHand(0))) {
            SplitButton.setDisable(false);
        }

        if (player.getScore(0) == 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHit(ActionEvent hitEvent) {
        updateCardHandPictures(firstHandImageViews, hitEvent);
        updateLabel(PlayerScore1, player);

        if (player.getScore(0) >= 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHold(ActionEvent holdEvent) {
        player.hold();
        updateCardHandPictures(dealerHandImageViews, holdEvent);
        updateLabel(DealerScore, dealer);
        player.findWinner(handComp, dealer);
        ShowBalance.setText(player.getBalance() + "$");

        HoldButton.setDisable(true);
        HitButton.setDisable(true);
        SplitButton.setDisable(true);
        BetButton.setDisable(false);
        BetField.setDisable(false);
    }

    @FXML
    public void handleSplit(ActionEvent splitEvent) {
        player.split(player.getHand(0));
        updateCardHandPictures(firstHandImageViews, splitEvent);
        updateLabel(PlayerScore1, player);
        updateLabel(PlayerScore2, player);
        SplitButton.setDisable(true);
    }

    @FXML
    public void handleBet() {
        NewGameButton.setDisable(false);
        BetButton.setDisable(true);
        BetField.setDisable(true);

        if (BetField.getText().isBlank()) {
            player.setBet(String.valueOf(player.getStandardBet()));
        }
        else player.setBet(BetField.getText());
    }

    @FXML
    public void handleRegister() {
        dealerHandImageViews = Arrays.asList(DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18);
        firstHandImageViews = Arrays.asList(CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18);
        secondHandImageViews = Arrays.asList(CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28); 
        thirdHandImageViews = Arrays.asList(CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38);
        //Forslag til kodeendring - lage klasse som enten validerer: sjekker at brukernavn ikke er tatt,
        // at passord følger visse regler, og skriver personens informasjon og balanse til fil. dette oppdateres enten
        // etter hvert spill eller når programmet avsluttes
        // Den andre metoden til klassen kalles ved loginButton, sammenkobler brukernavn til passord og henter ut balanse
        // fra fil, samt tidligere spillhistorikk?
        // CredentialsCheck check = new CredentialsCheck();
        //  if (check.validate(NameField.getText(),PasswordField.getText())) 
        //          utføre kode nedenfor
        // else {NameField.setPromptText("Invalid");
        //       PasswordField.setPromptText("Invalid");}
        
        if (fileHandler.CheckRegisterOrLogin("Register", NameField.getText(), PasswordField.getText())){
            this.player = new BlackjackPlayer(100, NameField.getText(), deck);
            NameField.clear();
            PasswordField.clear();
            WelcomeSign.setText("Welcome " + player.getName());
            ShowBalance.setText(player.getBalance() + "$");
            
            WelcomeSign.setVisible(true);;
            LoginButton.setVisible(false);
            RegisterButton.setVisible(false);
            NameField.setDisable(true);
            BetButton.setDisable(false);
            BetField.setDisable(false);
        }
        else {
            NameField.clear();
            PasswordField.clear();
            NameField.setPromptText("Invalid Username");
            PasswordField.setPromptText("Invalid password");
        }    
    }

    @FXML
    public void handleLogin() {
        dealerHandImageViews = Arrays.asList(DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18);
        firstHandImageViews = Arrays.asList(CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18);
        secondHandImageViews = Arrays.asList(CardPicture21, CardPicture22, CardPicture23, CardPicture24, CardPicture25, CardPicture26, CardPicture27, CardPicture28); 
        thirdHandImageViews = Arrays.asList(CardPicture31, CardPicture32, CardPicture33, CardPicture34, CardPicture35, CardPicture36, CardPicture37, CardPicture38);
        
        if (fileHandler.CheckRegisterOrLogin("Login",NameField.getText(), PasswordField.getText())){
            this.player = new BlackjackPlayer(Double.parseDouble(fileHandler.getBalance(NameField.getText())), NameField.getText(), deck);
            NameField.clear();
            PasswordField.clear();
            WelcomeSign.setText("Welcome " + player.getName());
            ShowBalance.setText(player.getBalance() + "$");
            
            WelcomeSign.setVisible(true);;
            LoginButton.setVisible(false);
            RegisterButton.setVisible(false);
            NameField.setDisable(true);
            BetButton.setDisable(false);
            BetField.setDisable(false);
        }
        else {
            NameField.clear();
            PasswordField.clear();
            NameField.setPromptText("Invalid Username");
            PasswordField.setPromptText("Invalid password");
        }
    }
}