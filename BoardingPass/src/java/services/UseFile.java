package services;

import domain.BoardingPass;
import domain.BoardingPassTicket;
import domain.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UseFile {

    public ArrayList<BoardingPassTicket> getObject() throws IOException {
        ArrayList<BoardingPassTicket> deSerialize=new ArrayList<>();
        ObjectInputStream ois=null;
        FileInputStream fis=null;
        try {
            fis = new FileInputStream("src/resources/boardingPass.txt"); ois = new ObjectInputStream(fis);
            BoardingPassTicket bpt= (BoardingPassTicket) ois.readObject();
            if(bpt!=null){
                deSerialize.add(bpt);
            }
            //deSerialize=(ArrayList<Object>) ois.readObject();
            fis.close();ois.close();
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return deSerialize;
    }

    public ArrayList<BoardingPassTicket> getBoardingPassTicket() throws IOException{
        ArrayList<BoardingPassTicket> bptArrs=new ArrayList<>();
        try{
            File file=new File("src/resources/ticket.txt");
            Scanner scan= new Scanner(file);
            ArrayList<String> tempStrings=new ArrayList<>();
            while(scan.hasNextLine()){
                String line= scan.nextLine().replaceAll(" ","");
                String[] attr= line.split(":");
                tempStrings.add(attr[1]);
            }
            int size=tempStrings.size();
            for(int i=0; i<size/12; i++) {
                int id = Integer.parseInt(tempStrings.get(0));tempStrings.remove(0);
                String date = tempStrings.get(0);tempStrings.remove(0);
                String name = tempStrings.get(0);tempStrings.remove(0);
                String email = tempStrings.get(0);tempStrings.remove(0);
                String gender = tempStrings.get(0);tempStrings.remove(0);
                long phoneNum = Long.parseLong(tempStrings.get(0));tempStrings.remove(0);
                int age = Integer.parseInt(tempStrings.get(0));tempStrings.remove(0);
                String origin = tempStrings.get(0);tempStrings.remove(0);
                String departure = tempStrings.get(0);tempStrings.remove(0);
                String destination = tempStrings.get(0);tempStrings.remove(0);
                String eta = tempStrings.get(0);tempStrings.remove(0);
                double totalPrice = Double.parseDouble(tempStrings.get(0));tempStrings.remove(0);

                BoardingPass bp = new BoardingPass(id, totalPrice, date, origin, destination, eta, departure);
                Person person = new Person(name, email, phoneNum, gender, age);
                BoardingPassTicket bpt = new BoardingPassTicket(bp, person);
                bptArrs.add(bpt);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return bptArrs;
    }


    public int getNextBoardingPassid(ArrayList<BoardingPassTicket> bptArrs){
        if(bptArrs.size()==0){
            return 0;
        }
        return bptArrs.stream().mapToInt(x->x.getBp().getId()).max().getAsInt();
    }

    public void makeObject(BoardingPassTicket boardingPassTicket) throws IOException {
        ArrayList<BoardingPassTicket> serialize=new ArrayList<>();
        ObjectOutputStream oos=null;
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream("src/resources/boardingPass.txt"); oos = new ObjectOutputStream(fos);
            oos.writeObject(boardingPassTicket);

            //serialize=(ArrayList<Call>) ois.readObject();
            fos.close();oos.close();
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
    }

    public void makeTicket(BoardingPassTicket bpt) throws IOException {
        System.out.println(bpt.getPerson()+" "+bpt.getBp());
        String personString = String.format("Boarding ID: %d%nDate: %s%nName: %s%nEmail: %s%nGender: %s%nPhone: %d%n" +
                        "Age: %d%n",
                bpt.getBp().getId(), bpt.getBp().getDate(), bpt.getPerson().getName(),
                bpt.getPerson().getEmail(), bpt.getPerson().getGender(), bpt.getPerson().getPhone(),
                bpt.getPerson().getAge());
        String flightString = String.format("Origin: %s%nDeparture Time: %s%nDestination: %s%nETA: %s%nTotal Price: %.2f",
                bpt.getBp().getOrigin(), bpt.getBp().getDeparture(), bpt.getBp().getDestination(), bpt.getBp().getEta(),
                bpt.getBp().getTotalPrice());
        FileWriter fw = new FileWriter("src/resources/ticket.txt",true);
        fw.write("\n");
        fw.write(personString);
        fw.write(flightString);
        fw.close();
        System.out.println("Ticket successfully created");
    }

    public void printBoardingPassTicket(BoardingPassTicket bpt) throws IOException {
        makeObject(bpt);
        ArrayList<BoardingPassTicket> allTickets=getObject();
        for(BoardingPassTicket bpticket: allTickets){
            System.out.println(bpticket.getPerson()+"\n"+bpticket.getBp());
            makeTicket(bpticket);
        }
    }
}
