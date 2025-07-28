import com.codetest.api.model.DeckResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CardGameAPITest {
    String deckId;

    @Test(priority = 1)
    public void createDeck() {
        DeckResponse deckResponse = RestAssured.get("https://deckofcardsapi.com/api/deck/new/")
                .as(DeckResponse.class);
        assertTrue(deckResponse.success);
        deckId = deckResponse.deck_id;
        assertNotNull(deckId);
    }

    @Test(priority = 2, dependsOnMethods = "createDeck")
    public void shuffleDeck() {
        DeckResponse shuffleResponse = RestAssured.get("https://deckofcardsapi.com/api/deck/" + deckId + "/shuffle/")
                .as(DeckResponse.class);
        assertTrue(shuffleResponse.success);
    }

    @Test(priority = 3, dependsOnMethods = "shuffleDeck")
    public void drawCardsAndCheckBlackjack() {
        Response response = RestAssured.get("https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=6");
        assertEquals(response.statusCode(), 200);
        @SuppressWarnings("unchecked")
        var cards = (java.util.List<String>) (java.util.List<?>) response.jsonPath().getList("cards.value");

        var player1 = cards.subList(0, 3);
        var player2 = cards.subList(3, 6);

        System.out.println("Player 1 cards: " + player1);
        System.out.println("Player 2 cards: " + player2);

        if (isBlackjack(player1)) System.out.println("Player 1 has blackjack!");
        else if (isBlackjack(player2)) System.out.println("Player 2 has blackjack!");
        else System.out.println("No blackjack.");
    }

    private boolean isBlackjack(java.util.List<String> hand) {
        return hand.contains("ACE") && (hand.contains("10") || hand.contains("JACK") || hand.contains("QUEEN") || hand.contains("KING"));
    }
}
