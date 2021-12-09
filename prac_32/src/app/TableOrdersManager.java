package app;

import app.exception.IllegalTableNumber;
import app.exception.OrderAlreadyAddedException;
import app.order.Order;
import app.order.menu.MenuItem;

public class TableOrdersManager implements OrdersManager {
    private Order[] orders = new Order[25];

    public void add(Order order, int tableNumber) throws OrderAlreadyAddedException {
        if (tableNumber < 0 || tableNumber > orders.length-1) throw new IllegalTableNumber("Invalid number of table");
        if (orders[tableNumber] != null) throw new OrderAlreadyAddedException("This table already has an order");
        orders[tableNumber] = order;
    }

    public void addItem(MenuItem item, int tableNumber) {
        if (tableNumber < 0 || tableNumber > orders.length-1) throw new IllegalTableNumber("Invalid number of table");
        orders[tableNumber].add(item);
    }

    public int freeTableNumber() {
        for (int i = 0; i < orders.length; i++) if (orders[i] == null) return i;
        return -1;
    }

    public int[] freeTableNumbers() {
        int l = 0;
        for (int i = 0; i < orders.length; i++) if (orders[i] == null) l++;
        int[] r = new int[l];
        l=0;
        for (int i = 0; i < orders.length; i++) if (orders[i] == null) r[l++]=i;
        return r;
    }

    public Order getOrder(int tableNumber) {
        if (tableNumber < 0 || tableNumber > orders.length-1) throw new IllegalTableNumber("Invalid number of table");
        return orders[tableNumber];
    }

    public void remove(int tableNumber) {
        if (tableNumber < 0 || tableNumber > orders.length-1) throw new IllegalTableNumber("Invalid number of table");
        orders[tableNumber]=null;
    }

    public int remove(Order order) {
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] != null) if (orders[i].equals(order)) {
                orders[i] = null;
                return i;
            }
        }
        return -1;
    }

    public int removeAll(Order order) {
        int r = 0;
        for (Order o: orders) {
            if (o != null) if (o.equals(order)) {
                o = null;
                r++;
            }
        }
        return r;
    }


    public int itemsQuantity(String itemName) {
        int r = 0;
        for (Order o: orders) if (o != null) r+=o.itemQuantity(itemName);
        return r;
    }

    public int itemsQuantity(MenuItem itemName) {
        int r = 0;
        for (Order o: orders) if (o != null) r+=o.itemQuantity(itemName);
        return r;
    }

    public Order[] getOrders() {
        return orders;
    }

    public double ordersCostSummary() {
        double r = 0;
        for (Order o: orders) if (o != null) r+=o.costTotal();
        return r;
    }

    public int ordersQuantity() {
        int r = 0;
        for (Order o: orders) if (o != null) r++;
        return r;
    }

}
