package actions;

import bonus.Bonus;
import gameStuff.City;
import gameStuff.ConnectedBuiltCityDiscover;
import gameStuff.Emporium;
import gameStuff.PermitTile;
import model.Game;

/**
 * This action allows the current player to build in one city from those which
 * are indicated by the permit deck. After that, the player catches the bonuses
 * of that city plus all of the linked cities in which he has already built
 * @author Emanuele
 *
 */

public class BuildByPermitTile extends MainAction {

	private PermitTile selectedPermitTile;
	private City selectedCity;

	/**
	 * Constructor of the BuildByPermitTile
	 * @param game is the current game
	 */
	public BuildByPermitTile(Game game, PermitTile selectedPermitTile, City selectedCity) {
		super(game);
		this.selectedPermitTile=selectedPermitTile;
		this.selectedCity=selectedCity;
	}
	
	/**
	 * First of all checks if the parameters the current player gave you are corrected,
	 * then remove the emporium from the hand of the player an adds it on the set of emporiums 
	 * of the selected city, then assigns all the bonuses of liked cities, then decrements player's assistants
	 * @return TRUE if the action goes well, false otherwise
	 */
	public boolean executeAction() {
		
		ConnectedBuiltCityDiscover likedCities=new ConnectedBuiltCityDiscover();
		
		if (!(checkCityNotContainsEmporium() || checkPermitTileContainsCity() || checkEnoughAssistants()))
			return false;
		
		Emporium temporaryEmporium=this.game.getCurrentPlayer().removeEmporium();
		this.selectedCity.addEmporium(temporaryEmporium);
		for (City city : likedCities.getConnectedBuiltCities(this.game.getGameTable().getMap().getGameMap(), this.selectedCity, temporaryEmporium))
			for (Bonus bonusToAssign : city.getRewardToken())
				bonusToAssign.assignBonus(this.game);
		this.game.getCurrentPlayer().decrementAssistants(assistantsToPay());
		
		checkRegionBoardCompletelyBuilt();
		checkCityColourCompletelyBuilt();
		
		return true;
	}
	
	/**
	 * Checks if the selected city already contains the emporium of the current player
	 * @return TRUE if the selected city doesn't contain the emporium, false otherwise
	 */
	private boolean checkCityNotContainsEmporium() {
		for (Emporium emporium : this.selectedCity.getCityEmporiums())
			if (emporium.getEmporiumsPlayer().equals(this.game.getCurrentPlayer()))
				return false;
		return true;
	}
	
	/**
	 * Checks if the selected permit tile contains the city in which you want to build
	 * @return TRUE if the selected permit tile contains the selected city, FALSE otherwise
	 */
	private boolean checkPermitTileContainsCity() {
		return this.selectedPermitTile.getBuildableCities().contains(this.selectedCity);
	}
	
	/**
	 * Checks if player's assistants are enough to build an emporium in the selected city
	 * @return TRUE if the assistants are enough, FALSE otherwise
	 */
	private boolean checkEnoughAssistants() {
		return this.game.getCurrentPlayer().getAssistants() >= 
				assistantsToPay();
	}
	
	/**
	 * @return the amount of assistants to pay
	 */
	private int assistantsToPay() {
		return this.selectedCity.getCityEmporiums().size();
	}
	
	/**
	 * Checks if, after an emporium build, the current player has completed the region.
	 * in case of that, the player picks the region board bonus, and if they are available, 
	 * he also picks one king reward tile
	 */
	private void checkRegionBoardCompletelyBuilt() {
		for (City city : this.selectedCity.getRegion().getRegionCities())
			for (Emporium emporium : city.getCityEmporiums())
				if (emporium.getEmporiumsPlayer().equals(this.game.getCurrentPlayer()))
					if (city.getRegion().isBonusAvailable()) {
						city.getRegion().getRegionBonus().assignBonus(this.game);
						city.getRegion().notBonusAvailable();
						if (!(this.game.getGameTable().getKingRewardTiles().isEmpty()))
							this.game.getGameTable().getKingRewardTiles().remove(0).assignBonus(this.game);
					}
	}
	
	/**
	 * Checks if, after an emporium build, the current player has completed the cities of that colour.
	 * in case of that, the player picks the region board bonus, and if they are available, 
	 * he also picks one king reward tile
	 */
	private void checkCityColourCompletelyBuilt() {
		for (City city : this.selectedCity.getColour().getCitiesOfThisColour())
			for (Emporium emporium : city.getCityEmporiums())
				if (emporium.getEmporiumsPlayer().equals(this.game.getCurrentPlayer()))
					if (city.getColour().isBonusAvailable()) {
						city.getColour().getColorBonus().assignBonus(this.game);
						city.getColour().notBonusAvailable();
						if (!(this.game.getGameTable().getKingRewardTiles().isEmpty()))
							this.game.getGameTable().getKingRewardTiles().remove(0).assignBonus(this.game);
					}
	
	}

}