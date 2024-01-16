import java.util.*;

class Main {
  
  public static int randNum(int max){ //returns random number from 0 to (max-1)
    Random rand = new Random();
    return rand.nextInt(max);
  }

  public static void rankingSelectionSort(ArrayList<Team> elements){ //rankingSelectionSort sorts an ArrayList of Team objects from lowest ranking number to highest ranking number. It uses selection sort, which divides the ArrayList into subarrays and sorts each section individually.
    for (int j = 0; j < elements.size() - 1; j++){
      int minIndex = j;
      for (int k = j+1; k < elements.size(); k++){
        if (elements.get(k).ranking < elements.get(minIndex).ranking){
          minIndex = k;
        }
      }
      int temp = elements.get(j).ranking;
      elements.get(j).ranking = elements.get(minIndex).ranking;
      elements.get(minIndex).ranking = temp;
    }
  }
  
  public static Team game(Team team1, Team team2){ //game generates two random numbers of goals for each competing Team object, where the random number is between zero and the team's total stat points. If one team has a higher number of goals, it wins the game. If the number of goals is tied, the team with more total stat points wins. If their stat points are equal, the second team wins. game returns the winner.
    int goals1 = randNum((team1.totalStats)+1);
    int goals2 = randNum((team2.totalStats)+1);
    if (goals1 == goals2){
      if ((team1.totalStats) > (team2.totalStats)){
        goals1 += 1;
      }
      else{
        goals2 += 1;
      }
    }
    team1.goalsFor += goals1;
    team2.goalsFor += goals2;
    team1.goalsAgainst += goals2;
    team2.goalsAgainst += goals1;
    if (goals1 > goals2){
      System.out.println(team1.name+" "+goals1+", "+team2.name+" "+goals2);
      team1.wins += 1;
      team2.losses += 1;
      return team1;
    }
    else{
      System.out.println(team2.name+" "+goals2+", "+team1.name+" "+goals1);
      team2.wins += 1;
      team1.losses += 1;
      return team2;
    }
  }

  public static Team playoffs(ArrayList<Team> teams){ //playoffs calls the game method to make the top 8 ranked teams play against each other in a single elimination tournament. It keeps track of the winning teams from each stage in a new ArrayList, and then returns the final winner of the tournament.
    ArrayList<Team> teamsCopied = new ArrayList<Team>();
    teamsCopied.addAll(teams);
    rankingSelectionSort(teamsCopied);
    ArrayList<Team> winningTeams = new ArrayList<Team>();
    System.out.println("\nThe Underwater Hockey Playoffs have started!");
    for (int i = 3; i > 0; i--){
      System.out.println("\nRound "+(4-i));
      for (int j = 0; j < i; j++){  
        Team winner = game(teamsCopied.get(j), teamsCopied.get(teamsCopied.size()-j-1));
        if (j % 2 == 0){
          winningTeams.add(j, winner);
        }
        else{
          winningTeams.add((winningTeams.size()-j+1), winner);
        }
      }
      teamsCopied.clear();
      teamsCopied.addAll(winningTeams);
      winningTeams.clear();
    }
    return teamsCopied.get(0);
  }

  public static void mergeSort(ArrayList<Team> elements){ //mergeSort initiates the recursive merge sorting of an ArrayList of Team objects. It ranks them from best performing to worst performing based on their number of wins, goal differential, and total stat points.
    int n = elements.size();
    Team[] temp = new Team[n];
    mergeSortHelper(elements, 0, n - 1, temp);
  }

  private static void mergeSortHelper(ArrayList<Team> elements,int from, int to, Team[] temp){ //mergeSortHelper breaks the full ArrayList into smaller sections recursively until all the elements are in their own ArrayList, then combines them again while sorting them using the merge method.
    if (from < to){
      int middle = (from + to) / 2;
      mergeSortHelper(elements, from, middle, temp);
      mergeSortHelper(elements, middle + 1, to, temp);
      merge(elements, from, middle, to, temp);
    }
  }

  private static void merge(ArrayList<Team> elements, int from, int mid, int to, Team[] temp){ //merge compares Team objects based on their number of wins, goal differential, and total stat points and sorts them from best performing to wort performing. If all traits are equal, it randomly determines the higher ranking one of the two elements.
    int i = from;
    int j = mid + 1;
    int k = from;

    while (i <= mid && j <= to){
      if (elements.get(i).wins == elements.get(j).wins){
        if ((elements.get(i).goalsFor - elements.get(i).goalsAgainst) == (elements.get(j).goalsFor - elements.get(j).goalsAgainst)){
          if (elements.get(i).totalStats == elements.get(j).totalStats){
            if (randNum(1+1) == 0){
              temp[k] = elements.get(i);
              i++;
            }
            else{
              temp[k] = elements.get(j);
              j++;
            }
          }
          else if (elements.get(i).totalStats > elements.get(j).totalStats){
            temp[k] = elements.get(i);
            i++;
          }
          else{
            temp[k] = elements.get(j);
            j++;
          }
        }
        else if ((elements.get(i).goalsFor - elements.get(i).goalsAgainst) > (elements.get(j).goalsFor - elements.get(j).goalsAgainst)){
            temp[k] = elements.get(i);
            i++;
        }
        else{
            temp[k] = elements.get(j);
            j++;
        }
      }
      else if (elements.get(i).wins > elements.get(j).wins){
          temp[k] = elements.get(i);
          i++;
      }
      else{
          temp[k] = elements.get(j);
          j++;
      }
      k++;
    }

    while (i <= mid){
      temp[k] = elements.get(i);
      i++;
      k++;
    }

    while (j <= to){
      temp[k] = elements.get(j);
      j++;
      k++;
    }

    for (k = from; k <= to; k++){
      elements.set(k,temp[k]);
    }
  }
  
  public static void roundRobinScheduler(ArrayList<Team> teams, ArrayList<Team> teamsC, int dayNum){ //roundRobinScheduler calls the game method to make all the competing teams play against each other in a round robin tournament, pitting certain teams against each other based on the progression of the tournament. It works by simulating the rotation of the list of teams while keeping the first team in place, and making each team play against the team one half of the list away from them.
    int rotation = dayNum % (teams.size()-1);
    int size = teamsC.size();
    int teamIndex = dayNum % size;
    System.out.println("\nWeek "+(dayNum+1));
    game(teamsC.get(teamIndex), teams.get(0));
    for (int index = 1; index < (teams.size())/2; index++){
      int firstTeam = (rotation+index)%size;
      int secondTeam = (rotation+size-index)%size;
      game(teamsC.get(firstTeam), teamsC.get(secondTeam));
    } 
  }
  
  public static void main(String[] args) {
    int numTeams = 8;
    ArrayList<String> cityNames = new ArrayList<>(Arrays.asList("Toronto", "Edmonton", "Boston", "Calgary", "Pittsburgh", "Montreal", "Tampa Bay", "Los Angeles", "Minnesota", "Florida", "Colorado", "Vancouver", "Washington", "Carolina", "Dallas", "St. Louis", "Vegas", "Detroit", "Chicago", "Atlanta"));
  ArrayList<String> animalNames = new ArrayList<>(Arrays.asList("Bluejays", "Penguins", "Panthers", "Coyotes", "Sharks", "Ducks", "Hornets", "Raptors", "Pelicans", "Orioles", "Cardinals", "Bruins", "Bulls", "Dolphins", "Seahawks", "Timberwolves", "Ravens", "Cubs", "Bucks", "Tigers"));
    String[] rankingStrings = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"}; 
    ArrayList<Team> teams = new ArrayList<Team>();
    
    for (int i = 0; i < numTeams; i++){
      int randIndex = randNum(cityNames.size());
      Team team = new Team(cityNames.get(randIndex), animalNames.get(randNum(animalNames.size())));
      teams.add(team);
      cityNames.remove(randIndex);
    }
    ArrayList<Team> teamsCopied = new ArrayList<Team>();
    teamsCopied.addAll(teams);
    teamsCopied.remove(0);
    ArrayList<Team> teamsRanked = new ArrayList<Team>();
    teamsRanked.addAll(teams);
    for (int i = 0; i < 2*(numTeams-1); i++){
      roundRobinScheduler(teams, teamsCopied, i);
      mergeSort(teamsRanked);
      System.out.println("Week "+(i+1)+" Standings\nplace - team - win - loss - for - against");
      for (int j = 0; j < numTeams; j++){
        System.out.println(rankingStrings[j]+" - "+teamsRanked.get(j).name+" - "+teamsRanked.get(j).wins+" - "+teamsRanked.get(j).losses+" - "+teamsRanked.get(j).goalsFor+" - "+teamsRanked.get(j).goalsAgainst);
      } 
    }
    Team finalWinner = playoffs(teams);
    System.out.println("The final winner is: "+finalWinner.name+"!");
  }
}
