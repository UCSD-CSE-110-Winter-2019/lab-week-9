package edu.ucsd.cse110.dogegotchi.food;

import edu.ucsd.cse110.dogegotchi.accessories.AccessoryFactory;
import edu.ucsd.cse110.dogegotchi.doge.Doge;
import edu.ucsd.cse110.dogegotchi.doge.DogeView;
import edu.ucsd.cse110.dogegotchi.doge.IDogeObserver;
import edu.ucsd.cse110.dogegotchi.doge.IFoodObserver;

public class FoodMediator implements IDogeObserver, IFoodObserver {

    FoodView foodView;
    Doge doge;
    DogeView dogeView;
    AccessoryFactory factory;

    public FoodMediator(AccessoryFactory factory, FoodView foodView, Doge doge, DogeView dogeView) {
        this.foodView = foodView;
        this.doge = doge;
        this.dogeView = dogeView;
        this.factory = factory;

        foodView.register(this);
        doge.register(this);
    }

    @Override
    public void onStateChange(Doge.State newState) {
        foodView.setVisible(newState == Doge.State.SAD);

        if (newState != Doge.State.EATING) {
            dogeView.setAccessoryView(factory.make(newState));
        }
    }

    @Override
    public void onFoodAvailable(AccessoryFactory.Accessory food) {
        doge.onFoodPresented();
        dogeView.setAccessoryView(factory.make(food));
    }
}
