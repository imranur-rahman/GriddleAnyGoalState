import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
        static int arraySize;
        static GoalState goalState[] = new GoalState[4];

        static boolean checkIfArraysAreEqual(int[][] ar1, int[][] ar2) {
                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j){
                                if(ar1[i][j] != ar2[i][j])
                                        return false;
                        }
                }
                return true;
        }

        static void generateGoalStates() {
                for(int i = 0; i < 4; ++i)
                        goalState[i] = new GoalState(arraySize);

                //horizontal
                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j) {
                                //System.out.println(arraySize + " " + i + " " + j);
                                goalState[0].ar[i][j] = i;
                                goalState[1].ar[i][j] = arraySize - i + 1;
                        }
                }
                //goalState[0].printAr();
                //goalState[1].printAr();


                //vertical
                for(int j = 1; j <= arraySize; ++j) {
                        for(int i = 1; i <= arraySize; ++i) {
                                goalState[2].ar[i][j] = j;
                                goalState[3].ar[i][j] = arraySize - j + 1;
                        }
                }
                //goalState[2].printAr();
                //goalState[3].printAr();

                for(int i = 0; i < 4; ++i) {
                        goalState[i].checkOrientation();
                        goalState[i].mapGoalState();
                }
        }

        static void addInPriorityQueue(State state, PriorityQueue<State> priorityQueue) {

                State temp1 = new State(state);
                temp1.setParentNull();
                temp1.setGoalState(goalState[0]);
                priorityQueue.add(temp1);

                State temp2 = new State(state);
                temp2.setParentNull();
                temp2.setGoalState(goalState[1]);
                priorityQueue.add(temp2);

                State temp3 = new State(state);
                temp3.setParentNull();
                temp3.setGoalState(goalState[2]);
                priorityQueue.add(temp3);

                State temp4 = new State(state);
                temp4.setParentNull();
                temp4.setGoalState(goalState[3]);
                priorityQueue.add(temp4);

        }

        public static void main(String[] args) throws FileNotFoundException, NullPointerException, InterruptedException {


                //time count start
                long tStart = System.currentTimeMillis();



                //open list
                PriorityQueue<State>priorityQueue = new PriorityQueue<State>();
                //closed list
                HashSet<State>hashSet = new HashSet<State>();

                Scanner scanner = new Scanner(new File("src/input.txt"));

                arraySize = scanner.nextInt();

                State initialState = new State(arraySize);
                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j){
                                initialState.ar[i][j] = scanner.nextInt();
                        }
                }

                /*State goalState = new State(arraySize);
                for(int i = 1; i <= arraySize; ++i) {
                        for(int j = 1; j <= arraySize; ++j){
                                goalState.ar[i][j] = scanner.nextInt();
                        }
                }*/
                generateGoalStates();



                //priorityQueue.add(initialState);
                addInPriorityQueue(initialState, priorityQueue);



                State now = null;

                System.out.println("ok");
                //initialState.printAr();
                //goalState.printAr();

                while(priorityQueue.isEmpty() == false) {

                        now = priorityQueue.poll();
                        /*if(now.equals(goalState))
                                break;*/
                        /*if(now.ar == goalState.ar)
                                break;*/
                        if(checkIfArraysAreEqual(now.ar, now.goalState.ar))
                                break;

                        //generate all childs
                        for(int i = 1; i <= arraySize; ++i) {
                                State temp1 = new State(now);
                                temp1.rotateRowLeft(i);
                                temp1.calculateHeuristic();
                                //temp1.printMove();
                                //temp1.printAr();

                                State temp2 = new State(now);
                                temp2.rotateRowRight(i);
                                temp2.calculateHeuristic();
                                //temp2.printMove();
                                //temp2.printAr();

                                if(hashSet.contains(temp1) == false)
                                        priorityQueue.add(temp1);
                                if(hashSet.contains(temp2) == false)
                                        priorityQueue.add(temp2);
                        }

                        for(int i = 1; i <= arraySize; ++i) {
                                State temp1 = new State(now);
                                temp1.rotateColumnUp(i);
                                temp1.calculateHeuristic();
                                //temp1.printMove();
                                //temp1.printAr();

                                State temp2 = new State(now);
                                temp2.rotateColumnDown(i);
                                temp2.calculateHeuristic();
                                //temp2.printMove();
                                //temp2.printAr();

                                if(hashSet.contains(temp1) == false)
                                        priorityQueue.add(temp1);
                                if(hashSet.contains(temp2) == false)
                                        priorityQueue.add(temp2);
                        }

                        hashSet.add(now);

                        //Thread.sleep(1000);
                }

                if(now == null) {
                        return;
                }

                Stack<State> stack = new Stack<State>();

                while(now != null  &&  now.Parent != null) {
                        stack.push(now);
                        now = now.Parent;
                }

                //now = stack.pop();
                System.out.println("Number Of Moves Needed : " + stack.size());
                while(stack.isEmpty() == false) {
                        now = stack.pop();
                        now.printMove();
                        now.printAr();
                }


                //time count end
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                System.out.println(elapsedSeconds + " seconds");
        }
}
