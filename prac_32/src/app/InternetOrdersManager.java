package app;

import app.exception.OrderAlreadyAddedException;
import app.order.Order;
import app.order.menu.MenuItem;

public class InternetOrdersManager implements app.OrdersManager {
    private int size;
    private QueueNode head;

    public static class QueueNode {
        QueueNode next;
        QueueNode prev;
        Order value;
    }

    public void add(Order order) throws OrderAlreadyAddedException {
        for (Order o: getOrders()) if (o.getCustomer().equals(order.getCustomer())) throw new OrderAlreadyAddedException("This customer already has an order");
        if (size == 0) {
            head = new QueueNode();
            head.prev = head.next = head;
            head.value = order;
        } else {
            QueueNode node = new QueueNode();
            node.next = head;
            node.prev = head.prev;
            node.value = order;
            node.prev.next = node;
            head.prev = node;
        }
        size++;
    }

    public void remove() {
        if (head != null) {
            QueueNode node = head;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            head = head.next;
            size--;
        }
        if (size == 0) {
            head = null;
        }
    }

    public Order order() {
        return head.value;
    }

    public int itemsQuantity(String itemName) {
        int r = 0;
        for (Order o: getOrders()) r += o.itemQuantity(itemName);
        return r;
    }

    public int itemsQuantity(MenuItem itemName) {
        int r = 0;
        for (Order o: getOrders()) r += o.itemQuantity(itemName);
        return r;
    }

    public Order[] getOrders() {
        Order[] r = new Order[size];
        QueueNode node = head;
        for (int i = 0; i < size; i++) {
            r[i] = node.value;
            node = node.next;
        }
        return r;
    }

    public double ordersCostSummary() {
        double r = 0;
        for (Order o: getOrders()) r += o.costTotal();
        return r;
    }

    public int ordersQuantity() {
        return size;
    }
}