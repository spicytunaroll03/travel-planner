package src;

import sol.TravelController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TravelController tester = new TravelController();
        tester.load("sol/KRCity.csv", "sol/KRTransport.csv");
        System.out.println(tester.mostDirectRoute("Ulsan", "Jeudo"));
//        tester.load("data/cities1.csv", "data/transport1.csv");
//////        //System.out.println(tester);
//////
//        System.out.println(tester.mostDirectRoute("Boston", "New York City") );
//        System.out.println(tester.mostDirectRoute("Boston", "Providence"));
////
//        System.out.println(tester.mostDirectRoute("Providence", "New York City"));
////
//        System.out.println(tester.fastestRoute("Providence", "asdf"));
//        System.out.println(tester.fastestRoute("Boston", "New York City"));
//        System.out.println(tester.fastestRoute("Providence", "New York City"));


        REPL<City, Transport> repl = new REPL<>(new TravelController());
        repl.run();
    }
}
