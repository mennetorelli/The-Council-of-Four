package client.view.notifies;

import client.view.ClientView;

public class ClientPermitTileBonusNotify implements ClientViewNotify {

	@Override
	public void updateView(ClientView view) {
		view.PurchasedPermitTileBonus();
	}

}
