package edu.ucsd.cse110.dogegotchi.food;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.accessories.AccessoryFactory;
import edu.ucsd.cse110.dogegotchi.doge.IFoodObserver;
import edu.ucsd.cse110.dogegotchi.observer.ISubject;

public class FoodView implements ISubject<IFoodObserver> {

    private ArrayList<IFoodObserver> observers;

    View foodMenu;
    ImageButton hamButton, steakButton, turkeyLegButton;

    public FoodView(Activity activity) {
        observers = new ArrayList<>();
        foodMenu = activity.findViewById(R.id.FoodMenuView);
        hamButton       = foodMenu.findViewById(R.id.HamButton);
        steakButton     = foodMenu.findViewById(R.id.SteakButton);
        turkeyLegButton = foodMenu.findViewById(R.id.TurkeyLegButton);

        hamButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                present(AccessoryFactory.Accessory.HAM);
            }
        });
        steakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                present(AccessoryFactory.Accessory.STEAK);
            }
        });
        turkeyLegButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                present(AccessoryFactory.Accessory.TURKEY);
            }
        });

    }

    public void setVisible(boolean value) {
        if (value) {
            foodMenu.setVisibility(View.VISIBLE);
            foodMenu.bringToFront();
        } else {
            foodMenu.setVisibility(View.INVISIBLE);
        }
    }

    public void present(AccessoryFactory.Accessory food) {
        for (IFoodObserver o: observers) {
            o.onFoodAvailable(food);
        }
    }

    @Override
    public void register(IFoodObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(IFoodObserver observer) {
        observers.remove(observer);
    }
}
