package account;

import core.models.Model;
import history.History;
import spot.Spot;

public class Account extends Model implements Comparable<Account> {
    private final String name;
    private String description;
    private final Spot spot;

    private final History history;

    public Account(String name) {
        this.name = name;
        this.spot = new Spot();
        this.history = new History();
    }

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
        this.spot = new Spot();
        this.history = new History();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Spot getSpot() {
        return spot;
    }

    public History getHistory() {
        return history;
    }

    @Override
    public int compareTo(Account other) {
        return this.getId().compareTo(other.getId());
    }
}
