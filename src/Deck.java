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
		int suitPos = 0;
		int rankPos = 0;
		for (int i=0; i<deckSize; ++i) {
			Suit suit = suitArr[suitPos];
			Rank rank = rankArr[rankPos];
			Card card = new Card(suit,rank);
			deck.add(card);
			++rankPos;
			if ((i+1)%rankArr.length==0) {
				++suitPos;
				rankPos = 0;
			}
			if (suitPos == Suit.values().length)
				suitPos = 0;
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
		for (int i=0; i < deck.size(); ++i)
			System.out.println(deck.get(i).getRank() + " of " + deck.get(i).getSuit());
	}
	public void sortOnce() {
		List<Card> temp = new ArrayList<Card>();
		Rank rankArr[] = Rank.values();
		Suit suitArr[] = Suit.values();
		int rankPos = 0;
		int suitPos = 0;
		while (suitPos < suitArr.length) {
			while (rankPos < rankArr.length) {
				for (int i=0; i<deck.size(); ++i) {
					if (deck.get(i).getRank() == rankArr[rankPos] && deck.get(i).getSuit() == suitArr[suitPos]) {
						temp.add(deck.get(i));
						deck.remove(i);
					}
				}
				++rankPos;
			}
			++suitPos;
			rankPos = 0;
		}
		
		deck.addAll(temp);
		for (int i=0; i<deck.size(); ++i) {
			deck.get(i).setAbsolutePosition(i);
		}
	}
	public void sortByRank() {
		Rank rankArr[] = Rank.values();
		int rankPos = 0;
		List<Card> temp = new ArrayList<Card>();
		while (!deck.isEmpty()) {
			for (int i=0; i<deck.size(); ++i) {
				if (deck.get(i).getRank() == rankArr[rankPos]) {
					temp.add(deck.get(i));
					deck.remove(i);
					--i;
				}
			}
			++rankPos;
		}
		deck.addAll(temp);
	}
	public void sortBySuitAndRank() {
		List<Card> temp = new ArrayList<Card>();
		for (int i = 0; i<deck.size(); ++i)
			temp.add(null);
		
		int pos;
		for (int i = 0; i<deck.size(); ++i) {
			pos = deck.get(i).getAbsolutePosition();
			temp.set(pos, deck.get(i));
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