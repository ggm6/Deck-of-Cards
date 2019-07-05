import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

enum Suit {
	CLUBS, SPADES, HEARTS, DIAMONDS;
}

enum Rank {
	TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
}

class Card {
	private Suit Suit;
	private Rank Rank;
	private int position;

	Card(Suit suit, Rank rank) {
		Suit = suit;
		Rank = rank;
	}
	public Suit getSuit() {
		return Suit;
	}
	public Rank getRank() {
		return Rank;
	}
	public int getAbsolutePosition() {
		return position;
	}
	public void setAbsolutePosition(int pos) {
		position = pos;
	}
}

public class Deck {
	private List<Card> deck = new ArrayList<Card>();
	private int size;

	public int size() {
		return size;
	}
	Deck(int deckSize) {
		size = deckSize;
		Suit suitArr[] = Suit.values();
		Rank rankArr[] = Rank.values();
		int suitArrPos = 0;
		int rankArrPos = 0;
		for (int deckPos=0; deckPos<deckSize; ++deckPos) {
			Suit suit = suitArr[suitArrPos];
			Rank rank = rankArr[rankArrPos];
			Card card = new Card(suit,rank);
			deck.add(card);
			++rankArrPos;
			if ((deckPos+1)%rankArr.length==0) {
				++suitArrPos;
				rankArrPos = 0;
			}
			if (suitArrPos == suitArr.length)
				suitArrPos = 0;
		}
		sortOnce();
	}
	public void shuffle() {
		Random rand = new Random();
		for (int i=0; i<1000; ++i) {
			int n1 = rand.nextInt(deck.size());
			int n2 = rand.nextInt(deck.size());
			Collections.swap(deck, n1, n2);
		}
	}
	public void display() {
		for (int deckPos=0; deckPos < deck.size(); ++deckPos)
			System.out.println(deck.get(deckPos).getRank() + " of " + deck.get(deckPos).getSuit());
	}
	public void sortOnce() {
		List<Card> temp = new ArrayList<Card>();
		Rank rankArr[] = Rank.values();
		Suit suitArr[] = Suit.values();
		int rankArrPos = 0;
		int suitArrPos = 0;
		while (suitArrPos < suitArr.length) {
			while (rankArrPos < rankArr.length) {
				for (int deckPos=0; deckPos<deck.size(); ++deckPos) {
					Rank rank = deck.get(deckPos).getRank();
					Suit suit = deck.get(deckPos).getSuit();
					if (rank == rankArr[rankArrPos] && suit == suitArr[suitArrPos]) {
						temp.add(deck.get(deckPos));
						deck.remove(deckPos);
					}
				}
				++rankArrPos;
			}
			++suitArrPos;
			rankArrPos = 0;
		}

		deck.addAll(temp);
		for (int deckPos=0; deckPos<deck.size(); ++deckPos) {
			deck.get(deckPos).setAbsolutePosition(deckPos);
		}
	}
	public void sortByRank() {
		Rank rankArr[] = Rank.values();
		int rankArrPos = 0;
		List<Card> temp = new ArrayList<Card>();
		while (!deck.isEmpty()) {
			for (int deckPos=0; deckPos<deck.size(); ++deckPos) {
				Rank rank = deck.get(deckPos).getRank();
				if (rank == rankArr[rankArrPos]) {
					temp.add(deck.get(deckPos));
					deck.remove(deckPos);
					--deckPos;
				}
			}
			++rankArrPos;
		}
		deck.addAll(temp);
	}
	public void sortBySuitAndRank() {
		List<Card> temp = new ArrayList<Card>();
		for (int deckPos = 0; deckPos<deck.size(); ++deckPos)
			temp.add(null);

		int pos;
		for (int deckPos = 0; deckPos<deck.size(); ++deckPos) {
			pos = deck.get(deckPos).getAbsolutePosition();
			temp.set(pos, deck.get(deckPos));
		}
		deck = temp;
	}

	public static void main(String[] args) {
		Deck deck = new Deck(52);
		System.out.println(deck.size() + " cards in the deck.");
		deck.shuffle();
		System.out.println("\n\nShuffled Deck:");
		deck.display();
		deck.sortByRank();
		System.out.println("\n\nSorted by Rank:");
		deck.display();
		deck.sortBySuitAndRank();
		System.out.println("\n\nSorted by Suit and Rank:");
		deck.display();
	}
}
