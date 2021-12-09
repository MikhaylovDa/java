package app;

import app.order.Order;
import app.order.menu.MenuItem;

public interface OrdersManager {
    int itemsQuantity(String itemName);
    int itemsQuantity(MenuItem itemName);
    Order[] getOrders();
    double ordersCostSummary();
    int ordersQuantity();
}