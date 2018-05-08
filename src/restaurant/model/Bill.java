package restaurant.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Implements model of Bill.
 * @author User
 */
public class Bill implements Serializable, Cloneable, Comparable<Bill> {
    public static final int FIELDS_NUMBER = 5;
    
    private long id;
    private long id_order;
    private double amount;
    private boolean billed;
    private boolean requestedForPayment;


    public Bill(long id, long id_order, double amount, boolean billed, boolean requestedForPayment) {
        this.id = id;
        this.id_order = id_order;
        this.amount = amount;
        this.billed = billed;
        this.requestedForPayment = requestedForPayment;
    }
    
    public Bill(String[] fieldsArray) {
        this.id = Long.valueOf(fieldsArray[0]);
        this.id_order = Long.valueOf(fieldsArray[1]);
        this.amount = Double.valueOf(fieldsArray[2]);
        this.billed = Boolean.valueOf(fieldsArray[3]);
        this.requestedForPayment = Boolean.valueOf(fieldsArray[4]);
    }

    public long getId() {
        return id;
    }

    public long getId_order() {
        return id_order;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isBilled() {
        return billed;
    }

    public boolean isRequestedForPayment() {
        return requestedForPayment;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    public void setRequestedForPayment(boolean requestedForPayment) {
        this.requestedForPayment = requestedForPayment;
    }

    @Override
    public Bill clone() throws CloneNotSupportedException {
        return (Bill) super.clone();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 73 * hash + (int) (this.id_order ^ (this.id_order >>> 32));
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
        hash = 73 * hash + (this.billed ? 1 : 0);
        hash = 73 * hash + (this.requestedForPayment ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bill other = (Bill) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_order != other.id_order) {
            return false;
        }
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (this.billed != other.billed) {
            return false;
        }
        if (this.requestedForPayment != other.requestedForPayment) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", id_order=" + id_order + ", amount=" + amount + ", billed=" + billed + ", requestedForPayment=" + requestedForPayment + '}';
    }

    @Override
    public int compareTo(@NotNull Bill o) {
        return Long.valueOf(this.id - o.id).intValue();
    }
}
