package client.modelDTO.actionsDTO.actionsParametersSetters;

import java.util.ArrayList;
import java.util.List;

import client.modelDTO.GameDTO;
import client.modelDTO.actionsDTO.ActionDTO;
import client.modelDTO.actionsDTO.standardActions.BuildByPermitTileDTO;
import client.modelDTO.gameTableDTO.CityDTO;
import client.modelDTO.gameTableDTO.PermitTileDTO;
import client.view.ClientView;

public class BuildByPermitTileParser implements ActionParserVisitor {

	private BuildByPermitTileDTO selectedAction;
	private ClientView view;
	private GameDTO game;

	public BuildByPermitTileParser(BuildByPermitTileDTO selectedAction, ClientView view, GameDTO game) {
		this.selectedAction=selectedAction;
		this.view=view;
		this.game=game;
	}

	
	@Override
	public ActionDTO setParameters() {
		this.view.displayMessage("Ok! you have chosen to build an emporium with a permit tile.");
			
		if (!this.game.getClientPlayer().getAvailablePermitTiles().isEmpty()) {
			
			this.view.displayMessage("Now I need some other infos, like:");
		
			this.view.displayMessage("the permit tile you want to use");
			PermitTileDTO permitTileTranslated=this.view.askForPermitTile
					(this.game.getClientPlayer().getAvailablePermitTiles());
			this.selectedAction.setSelectedPermitTile(permitTileTranslated);
			
			this.view.displayMessage("the name of the city in which you want to build");
			List<CityDTO> acceptableCities=new ArrayList<>();
			for (CityDTO acceptableCity : permitTileTranslated.getBuildablecities())
				acceptableCities.add(acceptableCity);
			this.selectedAction.setSelectedCity(this.view.askForCity(acceptableCities));
			
			this.selectedAction.parametersSetted();
		
		}
		else 
			this.view.displayMessage("but it seems that you haven't any permit tile turned up! Select another action please");
		
		return this.selectedAction;
	}

}