package app.order;

import app.order.customer.Customer;
import app.order.menu.MenuItem;

import java.util.HashMap;

public class TableOrder implements Order{
    private int size = 0;
    private MenuItem[] items = new MenuItem[0];
    private Customer customer;

    public boolean add(MenuItem item){
        try {
            if (items.length == size) {
                MenuItem[] newItems = new MenuItem[(int)(size*1.5+1)];
                for (int i = 0; i < size; i++) newItems[i] = items[i];
                items = newItems;
            }
            items[size++] = item;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(String itemName) {
        int f = -1;
        for (int i = 0; i < size; i++) if (items[i].getName().equals(itemName)) f = i;
        if (f == -1) return false;
        items[f] = null;
        for (int i = f; i < --size; i++) items[i] = items[i+1];
        return true;
    }

    public int removeAll(String itemName) {
        int r = 0;
        for (int i = 0; i < size; i++) {
            if (items[i+r].getName().equals(itemName)) {
                r++;
                continue;
            }
            if (i + r < items.length) {
                items[i] = items[i+r];
            } else {
                items[i] = null;
            }
        }
        size-=r;
        return r;
    }

    public int itemQuantity() {
        return size;
    }

    public int itemQuantity(String itemName) {
        int r = 0;
        for (int i = 0; i < size; i++) if (items[i].getName().equals(itemName)) r++;
        return r;
    }

    public MenuItem[] getItems() {
        MenuItem[] r = new MenuItem[size];
        for (int i = 0; i < size; i++) r[i] = items[i];
        return r;
    }

    public double costTotal() {
        double r = 0;
        for (int i = 0; i < size; i++) r+=items[i].getCost();
        return r;
    }

    public String[] itemsNames() {
        HashMap<String, Boolean> r = new HashMap<>();
        for (int i = 0; i < size; i++) r.put(items[i].getName(), true);
        return r.keySet().toArray(new String[r.size()]);
    }

    public MenuItem[] sortedItemByCostDesc(){
        MenuItem[] r = new MenuItem[size];
        for (int i = 0; i < size; i++) r[i] = items[i];
        new QuickSort(r, 0, size);
        return r;
    }

    @Override
    public int itemQuantity(MenuItem itemName) {
        int r = 0;
        for (int i = 0; i < size; i++) if (items[i].equals(itemName)) r++;
        return r;
    }

    @Override
    public boolean remove(MenuItem itemName) {
        int f = -1;
        for (int i = 0; i < size; i++) if (items[i].equals(itemName)) f = i;
        if (f == -1) return false;
        items[f] = null;
        for (int i = f; i < --size; i++) items[i] = items[i+1];
        return true;
    }

    @Override
    public int removeAll(MenuItem itemName) {
        int r = 0;
        for (int i = 0; i < size; i++) {
            if (items[i+r].equals(itemName)) {
                r++;
                continue;
            }
            if (i + r < items.length) {
                items[i] = items[i+r];
            } else {
                items[i] = null;
            }
        }
        size-=r;
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