package com.hallio.sms;

import com.hallio.dms.IObject;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Sales extends IObject {
    private int customerId;
    RelatedSalesBooking relatedBookings;
    private SalesStatus status;
    private int saleAmount;
    private Date date;

    public Sales(int id, int customerId, RelatedSalesBooking relatedBookings, SalesStatus status, int saleAmount, Date date) {
        this.id = id;
        this.customerId = customerId;
        this.relatedBookings = relatedBookings;
        this.status = status;
        this.saleAmount = saleAmount;
        if(date == null) {
            this.date = new Date();
            return;
        }
        this.date = date;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public RelatedSalesBooking getRelatedBookings() {
        return this.relatedBookings;
    }

    public void setRelatedBookings(RelatedSalesBooking relatedBookings) {
        this.relatedBookings = relatedBookings;
    }

    public SalesStatus getStatus() {
        return this.status;
    }

    public void setStatus(SalesStatus status) {
        this.status = status;
    }

    public int getSaleAmount() {
        return this.saleAmount;
    }

    public void setSaleAmount(int saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        return new LinkedList<String>(
                Arrays.asList(
                        String.valueOf(this.customerId),
                        this.status.toString(),
                        String.valueOf(this.saleAmount),
                        this.date.getTime() + ""
                )
        );
    }

    @Override
    protected String getFilePath() {
        return "sales.txt";
    }

    @Override
    protected void loadFromString(List<String> list) {
        this.id = Integer.parseInt(list.get(0));
        this.customerId = Integer.parseInt(list.get(1));
        this.status = SalesStatus.valueOf(list.get(2));
        this.saleAmount = Integer.parseInt(list.get(3));
        this.date = new Date(Long.parseLong(list.get(4)));
    }
}
