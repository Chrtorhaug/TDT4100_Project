package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class BlackjackController { 

    private BlackjackPlayer player;
    private CardDeck deck;
    private BlackJackDealer dealer;
    private HandComparator handComp;
    private List<ImageView> firstHandImageViews= new ArrayList<>();
    private List<ImageView> dealerHandImageViews = new ArrayList<>();
    
    @FXML
    private ListView<Card> PlayerCards, DealerCards, SplitCards;

    @FXML
    private Label PlayerScore, DealerScore, WelcomeSign, ShowBalance;
    
    @FXML
    private Button LoginButton, RegisterButton, NewGameButton, BetButton, HitButton, HoldButton, SplitButton;

    @FXML
    private TextField NameField, PasswordField, BetField;

    @FXML
    private ImageView DealerPicture11, DealerPicture12, DealerPicture13, DealerPicture14, DealerPicture15, DealerPicture16, DealerPicture17, DealerPicture18,
                    CardPicture11, CardPicture12, CardPicture13, CardPicture14, CardPicture15, CardPicture16, CardPicture17, CardPicture18;

    @FXML
    public void initialize() {
        this.deck = new CardDeck(5);
        this.dealer = new BlackJackDealer(deck);
        handComp = new HandComparator();

        PlayerScore.setText(" ");
        DealerScore.setText(" ");
        ShowBalance.setText(" ");
        WelcomeSign.setVisible(false);
        SplitCards.setVisible(false);

        NewGameButton.setDisable(true);
        BetButton.setDisable(true);
        HitButton.setDisable(true);
        HoldButton.setDisable(true);
        SplitButton.setDisable(true);
        BetField.setDisable(true);
    }
    /*
    private void updateListView(ListView<Card> lv, PlayerInterface pl, ActionEvent event) {
        lv.getItems().clear();

        if (pl.getClass().equals(BlackJackDealer.class)) {
            if (event.getSource().equals(HoldButton)) { //Hvis runden er ferdig skal Dealer vise alle kortene sine
                lv.getItems().addAll(pl.getHand(0));
            }
            else {
                pl.newHand(deck);
                lv.getItems().add(pl.getHand(0).get(0)); // Hvis runden ikke er ferdig skal Dealer bare vise første kort
            }
        }
        else if (event.getSource().equals(NewGameButton)) {
            pl.newHand(deck);
            lv.getItems().addAll(pl.getHand(0));
        }
        else if (event.getSource().equals(HitButton)) {
            player.addCard(deck);
            PlayerCards.getItems().clear();
            PlayerCards.getItems().addAll(player.getHand(0));
        }
        else if (event.getSource().equals(SplitButton)) {
            PlayerCards.getItems().clear();
            PlayerCards.getItems().addAll(player.getHand(0));
        }
    } */

    private void updateCardHandPictures(List<ImageView> view, ActionEvent event) {
        List<List<ImageView>> hands = Arrays.asList(firstHandImageViews, dealerHandImageViews);
        if (hands.indexOf(view) == 1) {
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
                player.newHand(deck);
            } 
            if (event.getSource().equals(HitButton)) {
                player.addCard(deck);
            } 
            for (int i = 0; i < player.getHand(hands.indexOf(view)).size(); i++) {
                view.get(i).setImage(player.getCard(i).getCardPicture());
            }
        }
    }

    private void updateLabel(Label lb, PlayerInterface pl) {
        if (pl.getClass().equals(BlackJackDealer.class) && player.checkPlaying()) { // Hvis runden ikke er ferdig skal Dealer bare vise scoren til første kort 
            lb.setText(String.valueOf(pl.getHand(0).get(0).getValue()));
        }
        else lb.setText(String.valueOf(pl.getScore(0)));
    }

    @FXML
    public void handleNewGame(ActionEvent newGameEvent) {
        if (deck.getSize() < 50) {
            this.deck = new CardDeck(5);
        }
        //updateListView(PlayerCards, player, newGameEvent);
        //updateListView(DealerCards, dealer, newGameEvent);
        updateCardHandPictures(firstHandImageViews, newGameEvent);
        updateCardHandPictures(dealerHandImageViews, newGameEvent);
        updateLabel(PlayerScore, player);
        updateLabel(DealerScore, dealer);

        NewGameButton.setDisable(true);
        HitButton.setDisable(false);
        HoldButton.setDisable(false);
        SplitCards.setVisible(false);

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
        //updateListView(PlayerCards, player, hitEvent);
        updateCardHandPictures(firstHandImageViews, hitEvent);
        updateLabel(PlayerScore, player);

        if (player.getScore(0) >= 21) {
            HitButton.setDisable(true);
            SplitButton.setDisable(true);
        }
    }

    @FXML
    public void handleHold(ActionEvent holdEvent) {
        player.hold();
        //updateListView(DealerCards, dealer, holdEvent);
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
        SplitCards.setVisible(true);
        //updateListView(PlayerCards, player, splitEvent);
        SplitCards.getItems().addAll(player.getHand(1));
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
        this.player = new BlackjackPlayer(100, NameField.getText(), deck);
        NameField.clear();
        WelcomeSign.setText("Welcome " + player.getName());
        ShowBalance.setText(player.getBalance() + "$");
        
        WelcomeSign.setVisible(true);;
        LoginButton.setVisible(false);
        RegisterButton.setVisible(false);
        NameField.setDisable(true);
        BetButton.setDisable(false);
        BetField.setDisable(false);
    }
}