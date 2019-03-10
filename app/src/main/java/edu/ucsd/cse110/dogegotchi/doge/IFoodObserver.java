package edu.ucsd.cse110.dogegotchi.doge;

import edu.ucsd.cse110.dogegotchi.accessories.AccessoryFactory;

public interface IFoodObserver {

    public void onFoodAvailable(AccessoryFactory.Accessory food);
}
