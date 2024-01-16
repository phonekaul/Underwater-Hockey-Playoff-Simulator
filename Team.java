import java.util.*;

public class Team { //the Team class is a blueprint for the Team object that it creates.
  String name;
  int wins;
  int losses;
  int goalsFor;
  int goalsAgainst;
  int ranking;
  int lungCapacity;
  int swimming;
  int puckHandling;
  int totalStats;
  
  static int randNum(int max){ //returns random number from 0 to (max-1)
    Random rand = new Random();
    return rand.nextInt(max);
  }

  public String getName(String cn, String an){ //returns the concatenation of the two names set as parameters
    return cn + " " + an;
  }

  static int getLungCapacity(){ //returns random number from  0 to 5
    return randNum(5 + 1);
  }

  static int getSwimming(){ //returns random number from 0 to 5
    return randNum(5 + 1);
  }

  static int getPuckHandling(){ //returns random number from 0 to 5
    return randNum(5 + 1);
  }
  
  public Team(String cityName, String animalName) { //constructor creates contestant Team object and defines some of its attibutes (name, lungCapacity, swimming, puckHandling, and totalStats)
    name = getName(cityName, animalName);
    lungCapacity = getLungCapacity();
    swimming = getSwimming();
    puckHandling = getPuckHandling();
    totalStats = lungCapacity + swimming + puckHandling;
  }

}