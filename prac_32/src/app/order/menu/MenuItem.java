package app.order.menu;

public abstract class MenuItem {
    private final double cost;
    private final String name;
    private final String description;

    public MenuItem(String name, String description) {
        if (name.equals("")) throw new IllegalArgumentException("name is empty");
        if (description.equals("")) throw new IllegalArgumentException("description is empty");
        this.cost = 0;
        this.name = name;
        this.description = description;
    }

    public MenuItem(double cost, String name, String description) {
        if (cost < 0) throw new IllegalArgumentException("cost less zero");
        if (name.equals("")) throw new IllegalArgumentException("name is empty");
        if (description.equals("")) throw new IllegalArgumentException("description is empty");
        this.cost = cost;
        this.name = name;
        this.description = description;
    }
    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
