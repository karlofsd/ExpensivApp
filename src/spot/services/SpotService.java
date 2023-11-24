package spot.services;

import spot.Spot;

public class SpotService {
    public SpotService() {
    }

    public void deposit(Spot spot ,double amount){
        double newAmount = spot.getAmount() + amount;
        spot.setAmount(newAmount);
    }

    public void draw(Spot spot ,double amount){
        double newAmount = spot.getAmount() - amount;
        spot.setAmount(newAmount);
    }
}
