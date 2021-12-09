package app.order.menu;

public final class Drink extends MenuItem implements Alcoholable {
    private final double alcoholVol;
    private final DrinkTypeEnum type;

    public Drink(String name, String description, DrinkTypeEnum type, double alcoholVol) {
        super(name, description);
        this.type = type;
        this.alcoholVol = alcoholVol;
    }

    public Drink(double cost, String name, String description, DrinkTypeEnum type, double alcoholVol) {
        super(cost, name, description);
        this.type = type;
        this.alcoholVol = alcoholVol;
    }

    @Override
    public boolean isAlcoholicDrink() {
        return (alcoholVol > 0);
    }

    @Override
    public double getAlcoholVol() {
        return alcoholVol;
    }

    public DrinkTypeEnum getType() {
        return type;
    }
}