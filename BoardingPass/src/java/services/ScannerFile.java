package services;

import domain.BoardingPass;
import domain.BoardingPassTicket;
import domain.Person;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class ScannerFile {
    public static class ScannerGui {

        //osita//Used for Origin
        private String[] location = new String[]{"New York", "Atlanta", "Dallas", "San francisco"};
        private String name;
        private String email;
        private String gender;
        private String date;
        //private String origin;
        private String origin = location[new Random().nextInt(location.length)];
        private String destination;
        private String departureTime;
        private int age;
        private long phone;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        //public String getCurrentDate(){
        //Date dateNow= new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //String currentTime = dateFormat.format(date);
        //return currentTime;
        //}

        public BoardingPassTicket getUserInput(double totalPrice) throws IOException {
            UseFile useFile=new UseFile();
            if (age <= 12) {
                totalPrice = totalPrice * 0.5;
            } else if (age >= 60) {
                totalPrice = totalPrice * 0.4;
            } else if (gender.equalsIgnoreCase("female") & !(age <= 12 | age >= 60)) {
                totalPrice = totalPrice * .75;
            }

            //id randomly generated
            int id=0;
            if(useFile.getNextBoardingPassid(useFile.getBoardingPassTicket())==0){
                id = (int) (Math.random() * (19999 - 10000) + 10000);
            }
            else{
                id=useFile.getNextBoardingPassid(useFile.getBoardingPassTicket())+1;
            }

            //eta generated through google api divided by flight speed
            String eta = "Temp ETA";

            Person p = new Person(getName(), getEmail(), getPhone(), getGender(), getAge());
            BoardingPass bp = new BoardingPass(id, totalPrice, getDate(), getOrigin(), getDestination(), eta, getDepartureTime());

            return new BoardingPassTicket(bp, p);
        }
    }





    public BoardingPassTicket getScannerUserInput(double totalPrice) {
        String[] location = new String[]{"New York", "Atlanta", "Dallas", "San francisco"};
        String origin = location[new Random().nextInt(location.length)];

        Scanner scan = new Scanner(System.in);
        System.out.println("Please put in your Name, Email, Phone Number, Gender, Age, Date, Destination, and Departure Time");

        boolean notDoneAge = true;
        boolean notDonePhone = true;

        // Name
        System.out.println("Name");
        String name = scan.nextLine();

        // Email
        System.out.println("Email");
        String email = scan.nextLine();

        // Phone Number
        int phone = 0;
        while (notDonePhone) {

            System.out.println("Phone Number");

            try {

                phone = scan.nextInt();
                scan.nextLine();
                notDonePhone = false;

            } catch (Exception ignored) {
            }
        }

        // Gender
        System.out.println("Gender");
        String gender = scan.nextLine();

        // Age
        int age = 0;
        while (notDoneAge) {

            System.out.println("Age");

            try {
                age = scan.nextInt();
                scan.nextLine();
                notDoneAge = false;

            } catch (Exception ignored) {
            }
        }

        //Date
        System.out.println("Date");
        String date = scan.next();
        //Date dateNow= new Date();
        //SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //String currentTime=dateFormat.format(dateNow);

        // Origin/////ON//origin -  date
        //System.out.println("Origin");
        //String origin = scan.nextLine();

        // Destination
        System.out.println("Destination");
        String destination = scan.nextLine();

        // Departure Time
        System.out.println("Departure Time:");
        String departureTime = scan.nextLine();

        //price is determined by age
        //Age < = 12, 50% reduction of ticket price regardless of gender
        //Age > = 60, 60% reduction of ticket price regardless of gender
        //Females, 25% discount on the ticket price
        if (age <= 12) {
            totalPrice = totalPrice * 0.5;
        } else if (age >= 60) {
            totalPrice = totalPrice * 0.4;
        } else if (gender.equalsIgnoreCase("female") & !(age <= 12 | age >= 60)) {
            totalPrice = totalPrice * .75;
        }

        //id randomly generated
        int id = (int) (Math.random() * (19999 - 10000) + 10000);

        //eta generated through google api divided by flight speed
        String eta = "Temp ETA";

        Person p = new Person(name, email, phone, gender, age);
        BoardingPass bp = new BoardingPass(id, totalPrice, date, origin, destination, eta, departureTime);

        return new BoardingPassTicket(bp, p);
    }


}
