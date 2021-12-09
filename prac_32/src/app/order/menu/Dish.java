package app.order.menu;

public final class Dish extends MenuItem {

    public Dish(String name, String description) {
        super(name, description);
    }

    public Dish(double cost, String name, String description) {
        super(cost, name, description);
    }
}