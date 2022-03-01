package domain;

import java.io.Serializable;

public class BoardingPass implements Serializable {
    //  Boarding Pass Number, Date, Origin, Destination, Estimated time of arrival (ETA), Departure Time
    //  Total Ticket Price
    private static final long serialVersionUID=1L;
    private int id;
    private double totalPrice;
    private String date, origin, destination, eta, departure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public BoardingPass(){
        super();
    }

    public BoardingPass(int id, double totalPrice, String date, String origin, String destination, String eta, String departure){
        this.id=id;
        this.totalPrice=totalPrice;
        this.date=date;
        this.origin=origin;
        this.destination=destination;
        this.eta=eta;
        this.departure=departure;
    }

    @Override
    public String toString(){
       return "Id: "+ getId()+ " Total Price: "+ getTotalPrice()+ " Date: "+ getDate()+ " Origin: "+ getOrigin()+
               " Destination: "+ getDestination()+ " Estimated Time of Arrival (ETA): "+ getDestination()+ " Departure: "+ getDeparture();
    }

}
