package app.order;

import app.order.menu.MenuItem;

public class QuickSort {
    public QuickSort (MenuItem[] r, int bottomBorder, int topBorder) {
        if (r.length == 0 || bottomBorder>=topBorder) return;
        double pivot = r[(bottomBorder + topBorder)/2].getCost();
        int i = bottomBorder;
        int j = topBorder;
        while (i < j) {
            while (r[i].getCost() > pivot)i++;
            while (r[i].getCost() < pivot) j--;
            if (i >= j) break;
            MenuItem temp = r[i];
            r[i] = r[j];
            r[j] = temp;
            i++;
            j--;
        }
        new QuickSort(r, bottomBorder, j);
        new QuickSort(r, j + 1, topBorder);
    }
}
