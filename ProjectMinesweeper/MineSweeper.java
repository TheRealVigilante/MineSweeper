package ProjectMinesweeper;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper{
    static Random random =new Random();//For getting random numbers
    static int row,col,MineNum, FlagCounter;//position of values, number of mines,counter for flags
    static long start,end;//for the timer
    static int[][] grid;//main game array
    static int RandomI, RandomJ;//random row and random column for mine insertion
    static Scanner input = new Scanner(System.in);//for user input
    static long CurrentTime(){//to save start/end time
        return System.currentTimeMillis();
    }
    static void CalcTime(){//calculation of time to display for user
        System.out.println("Time Taken: "+((end-start)/1000)+" seconds");
    }
    static boolean isMine(int i,int j){//is position inserted a mine?
        return grid[i][j] == 9;
    }
    static boolean isZero(int x){
        return x == 0;
    }
    static boolean isOutOfBounds(int i,int j){
        int rowBounds=row,colBounds=col;
        return i > rowBounds || j > colBounds;
    }
    static boolean isDoubleDigits(int i,int j){
        return i==-1 ||j == -1;
    }
    static boolean isInvalid(int i,int j){
        return isZero(i)||isZero(j)||isOutOfBounds(i,j)||isDoubleDigits(i,j);
    }
    static boolean isOpen(int i,int j){//is position inserted open?
        return grid[i][j] >= 10;
    }
    static boolean isFlagCor(int i,int j){//is flag correct?
        return grid[i][j] == -1;
    }
    static boolean isFlagged(int i,int j){//is position inserted flagged?
        return grid[i][j] < 0;
    }
    static void Welcome(){
        System.out.println("Welcome to Minesweeper Game:\nGame is developed by: Youssef ElNahas\nUnder the supervision of: Dr. Masud Hasan\n");
    }
    static void Tutorial() {
        System.out.println("Here is how to play: \nFirst, for each round pick what you want to do(open,flag or deflag)\nThen enter the row,column you want to open/flag/deflag(The rows and columns start from 1 not 0 for simplicity)\nResult will be shown and you can try again or lose if you pick a mine or win if you flag all the mines\n");
    }
    static int CorFlagsCounter(){//are all the flags you entered correct
        int Counter=0;
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if (isFlagCor(i,j)){
                    Counter++;
                }
            }
        }
        return Counter;
    }
    static void CheatPrint(){//for cheating purposes, prints the grid fully open
        for (int i=0;i<row;i++){
            System.out.println(Arrays.toString(grid[i]));
        }
    }
    static void PrintOpenGrid(){//printing the grid as played by user
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if (isOpen(i,j)){//if it is open, display the actual value-10
                    System.out.print((grid[i][j]-10)+"  ");
                } else if (isFlagged(i,j)) {//if it is flagged, just put an X
                    System.out.print("X  ");
                } else {//if it is neither, just print "unopened" represented by K
                    System.out.print("M  ");
                }
            }
            System.out.println();
        }
    }
    static void CreateGrid(){//Creating an empty grid
        for (int i =0;i<row;i++){
            for (int j=0;j<col;j++){//Just in case
                grid[i][j]=0;
            }
        }
    }
    static void PutMine(){//Inserting mines according to MineNum
        int i,FakeMinNum=MineNum;
        for (i=0;i<FakeMinNum;i++){
            RandomI =random.nextInt(0,row);
            RandomJ =random.nextInt(0,col);
            if (grid[RandomI][RandomJ]==9){//in case of repetition of random integers
                FakeMinNum+=1;
            }
            grid[RandomI][RandomJ]=9;
        }
    }
    static int CheckMineCorner(int i,int j){//Checking how many mines around for the corners(i-1 = above, i+1 below,j-1 left,j+1 right)
        int counter=0;
        if (i == 0 && j == 0) {//top left corner
            if (isMine(i,j+1)) {
                counter += 1;
            }
            if (isMine(i+1,j)) {
                counter += 1;
            }
            if (isMine(i+1,j+1)) {
                counter += 1;
            }
        }
        else if (i==0&&j==col-1) {//top right corner
            if (isMine(i+1,j)) {
                counter += 1;
            }
            if (isMine(i,j-1)) {
                counter += 1;
            }
            if (isMine(i+1,j-1)) {
                counter += 1;
            }
        }
        else if (j==0&&i==row-1) {//bottom left corner
            if (isMine(i,j+1)) {
                counter += 1;
            }
            if (isMine(i-1,j)) {
                counter += 1;
            }
            if (isMine(i-1,j+1)) {
                counter += 1;
            }
        }
        else if (i==row-1&&j==col-1) {//bottom right corner
            if (isMine(i,j-1)) {
                counter += 1;
            }
            if (isMine(i-1,j-1)) {
                counter += 1;
            }
            if (isMine(i-1,j)) {
                counter += 1;
            }
        }
        return counter;
    }
    static int CheckMineEdge(int i,int j){//Checking how many mines are there in each edge of the grid(i-1 = above, i+1 below,j-1 left,j+1 right)
        int counter=0;
        if (i==0) {//top edge
            if (isMine(i,j-1)) {
                counter += 1;
            }
            if (isMine(i,j+1)) {
                counter += 1;
            }
            if (isMine(i+1,j-1)) {
                counter += 1;
            }
            if (isMine(i+1,j)) {
                counter += 1;
            }
            if (isMine(i+1,j+1)) {
                counter += 1;
            }
        }
        else if (j==0) {//left edge
            if (isMine(i,j+1)) {
                counter += 1;
            }
            if (isMine(i+1,j)) {
                counter += 1;
            }
            if (isMine(i+1,j+1)) {
                counter += 1;
            }
            if (isMine(i-1,j)) {
                counter += 1;
            }
            if (isMine(i-1,j+1)) {
                counter += 1;
            }
        }
        else if (i==row-1) {//bottom edge
            if (isMine(i,j-1)) {
                counter += 1;
            }
            if (isMine(i,j+1)) {
                counter += 1;
            }
            if (isMine(i-1,j-1)) {
                counter += 1;
            }
            if (isMine(i-1,j)) {
                counter += 1;
            }
            if (isMine(i-1,j+1)) {
                counter += 1;
            }
        }
        else if (j==col-1) {//right edge
            if (isMine(i,j-1)) {
                counter += 1;
            }
            if (isMine(i+1,j-1)) {
                counter += 1;
            }
            if (isMine(i+1,j)) {
                counter += 1;
            }
            if (isMine(i-1,j-1)) {
                counter += 1;
            }
            if (isMine(i-1,j)) {
                counter += 1;
            }
        }
        return counter;
    }
    static int CheckMineMid(int i,int j){//Checking how many mines are there in the middle of the grid(i-1 = above, i+1 below,j-1 left,j+1 right)
        int counter=0;
        if (isMine(i,j-1)) {
            counter += 1;
        }
        if (isMine(i,j+1)) {
            counter += 1;
        }
        if (isMine(i+1,j-1)) {
            counter += 1;
        }
        if (isMine(i+1,j)) {
            counter += 1;
        }
        if (isMine(i+1,j+1)) {
            counter += 1;
        }
        if (isMine(i-1,j-1)) {
            counter += 1;
        }
        if (isMine(i-1,j)) {
            counter += 1;
        }
        if (isMine(i-1,j+1)) {
            counter += 1;
        }
        return counter;
    }
    static void CheckMine(){
        int counter;
        for (int i =0;i<row;i++){
            for (int j=0;j<col;j++){
                if (grid[i][j]==0) {//we run this as there is only mines on the grid by this point, to assign for each 0 an actual correct value
                    //now checking where we are on the grid
                    if (i==0&&j==0){
                        counter=CheckMineCorner(i,j);
                    }
                    else if (i==0&&j==col-1) {
                        counter=CheckMineCorner(i,j);
                    }
                    else if (j==0&&i==row-1) {
                        counter=CheckMineCorner(i,j);
                    }
                    else if (i==row-1&&j==col-1) {
                        counter=CheckMineCorner(i,j);
                    }
                    else if (i==0) {
                        counter=CheckMineEdge(i,j);
                    }
                    else if (j==0) {
                        counter=CheckMineEdge(i,j);
                    }
                    else if (i==row-1) {
                        counter=CheckMineEdge(i,j);
                    }
                    else if (j==col-1) {
                        counter=CheckMineEdge(i,j);
                    }
                    else {
                        counter=CheckMineMid(i,j);
                    }
                    grid[i][j]= counter;
                }
            }
        }
    }
    static void Open(int i,int j){//if mine then you lost, open all zeroes around, if more than zero then only open that,  if flagged then ignore
        if (isMine(i,j)){//if mine then you lost
            System.out.println("You stepped on a mine, YOU LOST!");
            end= CurrentTime();
            CalcTime();
            System.exit(0);
        }
        if (isOpen(i,j)){//if already opened, ignore
            System.out.println("Invalid opening, value is already opened");
            return;
        }
        if (isFlagged(i,j)){//if flagged, ignore
            System.out.println("Invalid Open, Value is flagged");
        }

        if (grid[i][j]<10&& grid[i][j]>=0) {//Opening value
            grid[i][j] += 10;
        }
        if (grid[i][j]==10){//if value is 0 initially+opening value
            try {
                if (grid[i+1][j]>=0&& grid[i+1][j]<9){//if the one below is between 0 and 8,open it
                   Open(i+1,j);
                }
            }
            catch (Exception e){
                System.out.print("");
            }
            try {
                if (grid[i-1][j]>=0&& grid[i-1][j]<9) {//if the one above is between 0 and 8, open it
                    Open(i-1,j);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i][j+1]>=0&& grid[i][j+1]<9) {//if the one on the right is between 0 and 8, open it
                    Open(i,j+1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i][j-1]>=0&& grid[i][j-1]<9) {//if the one on the left is between 0 and 8, open it
                    Open(i,j-1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i+1][j+1]>=0&& grid[i+1][j+1]<9) {//if the one on the bottom right is between 0 and 8, open it
                    Open(i+1,j+1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i-1][j+1]>=0&& grid[i-1][j+1]<9) {//if the one on the top right is between 0 and 8, open it
                    Open(i-1,j+1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i+1][j-1]>=0&& grid[i+1][j-1]<9) {//if the one on the bottom left is between 0 and 8, open it
                    Open(i+1,j-1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
            try {
                if (grid[i-1][j-1]>=0&& grid[i-1][j-1]<9) {//if the one on the top left is between 0 and 8, open it
                    Open(i-1,j-1);
                }
            }
            catch (Exception e) {
                System.out.print("");
            }
        }
    }
    static void DifficultySet(){
        System.out.println("Please enter the difficulty:\nPick 1 for Easy(5*5 Grid with 7 mines)\nPick 2 for Medium(7*7 Grid with 14 mines)\nPick 3 for Hard(9*9 Grid with 24 mines)");
        int difficulty= input.nextInt();
        switch (difficulty){
            case 1:
                row=5;
                col=5;
                MineNum=7;
                break;
            case 2:
                row=7;
                col=7;
                MineNum=14;
                break;
            case 3:
                row=9;
                col=9;
                MineNum=24;
                break;
            default:
                System.out.println("Invalid Try Again");
                DifficultySet();
        }
        grid =new int[row][col];
        }
    static void PutFlag(int i, int j){//inserting flags
        if (grid[i][j]<10&& grid[i][j]>=0){//if not opened or deflagged
            grid[i][j]-=10;
            FlagCounter++;
        }
        else if (isFlagged(i,j)) {//if already flagged
            System.out.println("Can't flag what is already flagged");
        }
        else{//otherwise it will be open
            System.out.println("Can't flag what is already opened");
        }
    }
    static void DeFlag(){
        int pickR,pickC;
        String temp;
        System.out.println("Let's Remove Flag:");
        System.out.println("Please pick which row and column you'd like to deflag(starting count from 1) i.e 1,2 or 4,3");
        temp= input.next();
        pickR=Character.getNumericValue(temp.charAt(0));
        pickC=Character.getNumericValue(temp.charAt(2));
        if (isInvalid(pickR,pickC)){
            System.out.println("Invalid Entry, " +(pickR)+" or "+(pickC)+ " is out of Bounds");
            System.out.println("Try Again");
            DeFlag();
            return;
        }
        System.out.println("You picked: " + (pickR) + "," + (pickC));
        if (isFlagCor(pickR-1,pickC-1)){//disallows deflagging if the flag is correct
            System.out.println("Removal not allowed, flag is correct");
            PrintOpenGrid();
        }
        else if (isOpen(pickR-1,pickC-1)) {//disallows deflagging if the position is opened
            System.out.println("Removal not allowed, position is already opened");
            PrintOpenGrid();
        }
        else {//deflagging valid so deflags
            System.out.println("Removal of flag successful");
            grid[pickR-1][pickC-1]+=10;//deflagging
            PrintOpenGrid();
        }
    }
    static void Flag(){
        int pickR,pickC;
        String temp;
        System.out.println("Let's Flag:");
        System.out.println("Please pick which row and column you'd like to flag(starting count from 1) i.e 1,2 or 4,3");
        temp= input.next();
        pickR=Character.getNumericValue(temp.charAt(0));
        pickC=Character.getNumericValue(temp.charAt(2));
        if (isInvalid(pickR,pickC)){
            System.out.println("Invalid Entry, " +(pickR)+" or "+(pickC)+ " is out of Bounds");
            System.out.println("Try Again");
            Flag();
            return;
        }
        System.out.println("You are flagging: "+(pickR)+","+(pickC));
        PutFlag(pickR-1,pickC-1);
        PrintOpenGrid();
    }
    static void Play(){
        int pickR,pickC;
        String temp;
        System.out.println("Let's Open:");
        System.out.println("Please pick which row and column you'd like to open(starting count from 1) i.e 1,2 or 4,3");
        temp= input.next();
        pickR=Character.getNumericValue(temp.charAt(0));
        pickC=Character.getNumericValue(temp.charAt(2));
        if (isInvalid(pickR,pickC)){
            System.out.println("Invalid Entry, " +(pickR)+" or "+(pickC)+ " is out of Bounds");
            System.out.println("Try Again");
            Play();
            return;
        }
        System.out.println("You picked: " + (pickR) + "," + (pickC));
        Open(pickR-1, pickC-1);
        PrintOpenGrid();
    }
    static void PlayOrFlag(){
        String pick;
        System.out.println("Do you want to open, or flag, or deflag:");
        pick=input.next();
        if (pick.equalsIgnoreCase("open")){
            Play();
        }
        else if (pick.equalsIgnoreCase("flag")) {
            Flag();
        }
        else if (pick.equalsIgnoreCase("deflag")) {
            DeFlag();
        }
        else{
            System.out.println("Invalid Entry, Try again");
            PlayOrFlag();//try again
        }
    }
    static void Game(){
        Welcome();
        DifficultySet();//to set difficulty
        CreateGrid();//creating the grid
        PutMine();//putting the mines randomly
        CheckMine();//for each value that is not a mine, set a correct value for it by checking mines around it
        start= CurrentTime();//start time
//        CheatPrint();//print original completely opened grid for cheating
        PrintOpenGrid();
        Tutorial();
        while (FlagCounter !=MineNum){//checker for end of game
            PlayOrFlag();
        }
        while (CorFlagsCounter()!=MineNum){
            System.out.println("There is an incorrect flag, Try again");
            PlayOrFlag();
        }
        System.out.println("You have WON!! CONGRATULATIONS");
        end= CurrentTime();
        CalcTime();
    }

    public static void main(String[] args) {
        Game();
    }
}