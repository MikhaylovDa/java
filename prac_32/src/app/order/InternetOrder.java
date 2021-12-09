package app.order;

import app.order.customer.Customer;
import app.order.menu.MenuItem;

import java.util.HashMap;

public class InternetOrder implements Order {
    private int size = 0;
    private ListNode head;
    private Customer customer;

    public static class ListNode {
        ListNode next;
        ListNode prev;
        MenuItem value;
    }

    public InternetOrder(Customer customer) {
        this.customer = customer;
        head = new ListNode();
        head.prev = head.next = head;
        head.value = null;
    }

    public InternetOrder(MenuItem[] items, Customer customer) {
        this(customer);
        for(MenuItem item: items) add(item);
    }

    public boolean add(MenuItem item) {
        if (head.value == null) {
            head.value = item;
            return true;
        }
        ListNode element = new ListNode();
        element.value = item;
        element.next = head;
        element.prev = head.prev;
        head.prev.next = element;
        head.prev = element;
        size++;
        return true;
    }

    public boolean remove(String itemName) {
        ListNode node = head.prev;
        while (!node.value.getName().equals(itemName)) {
            if (node == head) return false;
            node = node.prev;
        }
        if (node == head) head = head.next;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node=null;
        size--;
        if (size == 0){
            head = new ListNode();
            head.prev = head.next = head;
        }
        return true;
    }

    public int removeAll(String itemName) {
        int r = 0;
        ListNode node = head.prev;
        while (node != head) {
            if(node.value.getName().equals(itemName)){
                size--;
                r++;
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            node = node.prev;
        }
        if(node.value.getName().equals(itemName)) {
            size--;
            r++;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            head = head.next;
        }
        if (size == 0){
            head = new ListNode();
            head.prev = head.next = head;
        }
        return r;
    }

    public int itemQuantity() {
        return size;
    }

    public MenuItem[] getItems() {
        MenuItem[] r = new MenuItem[size];
        ListNode node = head;
        for (int i = 0; i < size; i++){
            r[i] = node.value;
            node = node.next;
        }
        return r;
    }

    public double costTotal() {
        double r = head.value.getCost();
        ListNode node = head.next;
        while (node != head) {
            r += head.value.getCost();
            node = node.next;
        }
        return r;
    }

    public int itemQuantity(String itemName) {
        int r = 0;
        if (head.value.getName().equals(itemName)) r++;
        ListNode node = head.next;
        while (node != head) {
            if (head.value.getName().equals(itemName)) r++;
            node = node.next;
        }
        return r;
    }

    public String[] itemsNames() {
        HashMap<String, Boolean> r = new HashMap<>();
        r.put(head.value.getName(), true);
        ListNode node = head.next;
        while (node != head) {
            r.put(node.value.getName(), true);
            node = node.next;
        }
        return r.keySet().toArray(new String[r.size()]);
    }

    public MenuItem[] sortedItemByCostDesc(){
        MenuItem[] r = new MenuItem[size];
        ListNode node = head;
        for (int i = 0; i < size; i++) {
            r[i] = node.value;
            node = node.next;
        }
        new QuickSort(r, 0, size);
        return r;
    }

    @Override
    public int itemQuantity(MenuItem itemName) {
        int r = 0;
        if (head.value.equals(itemName)) r++;
        ListNode node = head.next;
        while (node != head) {
            if (head.value.equals(itemName)) r++;
            node = node.next;
        }
        return r;
    }

    @Override
    public boolean remove(MenuItem itemName) {
        ListNode node = head.prev;
        while (!node.value.equals(itemName)) {
            if (node == head) return false;
            node = node.prev;
        }
        if (node == head) head = head.next;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node=null;
        size--;
        return true;
    }

    @Override
    public int removeAll(MenuItem itemName) {
        int r = 0;
        ListNode node = head.prev;
        while (node != head) {
            if(node.value.equals(itemName)){
                size--;
                r++;
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            node = node.prev;
        }
        if(node.value.equals(itemName)) {
            size--;
            r++;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            head = head.next;
        }
        return r;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}