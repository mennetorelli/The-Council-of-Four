package server.model.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import players.Player;
import server.model.Game;

public class Market {
	
	private final List<Offer> offersList;
	private final List<Player> sellingPlayerList;
	private final List<Player> buyingPlayerList;
	
	public Market(){

		this.offersList=new ArrayList<>();
		this.sellingPlayerList=new ArrayList<>();
		this.buyingPlayerList=new ArrayList<>();
	}


	public void addOffer(Offer offer){
		this.offersList.add(offer);
	}
	
	/**
	 * it gives to buying player the object and decrements his coins; removes from the selling player 
	 * the object and increment his coins
	 * @param offer is the object to buy
	 * @param buyingPlayer is the player who accept the offer
	 */
	public void removeOffer(Offer offer){
		for (Offer offerInList : this.offersList)
			if (offer.equals(offerInList)) {
				this.offersList.remove(offer);
				break;
			}
	}
	
	public void addPlayer(Player player){
		this.buyingPlayerList.add(player);
	}
	
	public void shuffleBuyingPlayerList(){
		Collections.shuffle(this.buyingPlayerList);
	}
	
	public void clearMarket(){
		this.offersList.clear();
		this.buyingPlayerList.clear();
		this.sellingPlayerList.clear();
	}
	
	public void sellingNextPlayer(Game game){
		game.setCurrentPlayer(this.sellingPlayerList.remove(0));
	}
	
	public void buyingNextPlayer(Game game){
		game.setCurrentPlayer(this.buyingPlayerList.remove(0));
	}

	public List<Offer> getOffersList() {
		return offersList;
	}

	public List<Player> getSellingPlayerList() {
		return sellingPlayerList;
	}

	public List<Player> getBuyingPlayerList() {
		return buyingPlayerList;
	}
	
	
}
