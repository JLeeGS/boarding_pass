package driver;

import display.MainGui;
import domain.*;
import services.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class driver {
    public static void main (String args[]) throws IOException, InterruptedException, ParseException {
//        //Get Objects
//        UseFile useFile=new UseFile();
//        LocationAPI locationAPI=new LocationAPI();
//        ScannerFile sf=new ScannerFile();
//
//        //GUI
//        //MainGui gui=new MainGui();
//
//        //Standard Price Set to 500;
//        BoardingPassTicket bpt= sf.getScannerUserInput(500);
//        //ETA based on Google API distance and 500mph flight speed
//        locationAPI.generateETA(bpt);
//        //Prints Ticket
//        useFile.printBoardingPassTicket(bpt);

        new MainGui();
    }
}
