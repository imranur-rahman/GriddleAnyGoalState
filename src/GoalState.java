public class GoalState extends State{

        int[] mapAr;
        boolean orientation; // false->horizontal, true->vertical

        public GoalState(int arraySize) {
                super(arraySize);
                mapAr = new int[arraySize + 1];
        }

        public GoalState(State parent) {
                super(parent);
                mapAr = new int[parent.arraySize + 1];
        }

        public void checkOrientation() {
                boolean isHorizontal = true;
                for(int i = 1; i <= arraySize; ++i) {
                        boolean thisIsHorizontal = true;
                        for(int j = 2; j <= arraySize; ++j) {
                                if(ar[i][j] != ar[i][j -1]) {
                                        thisIsHorizontal = false;
                                }
                        }
                        isHorizontal &= thisIsHorizontal;
                }
                if(isHorizontal)
                        orientation = false;
                
                boolean isVertical = true;
                for(int j = 1; j <= arraySize; ++j) {
                        boolean thisIsVertical = true;
                        for(int i = 2; i <= arraySize; ++i) {
                                if(ar[i][j] != ar[i - 1][j]) {
                                        thisIsVertical = false;
                                }
                        }
                        isVertical &= thisIsVertical;
                }
                if(isVertical)
                        orientation = true;
        }

        public void mapGoalState() {
                if(orientation == true) { //vertical
                        for(int j = 1; j <= arraySize; ++j) {
                                mapAr[ ar[1][j] ] = j;
                        }
                }
                else {                  //horizontal
                        for(int i = 1; i <= arraySize; ++i) {
                                mapAr[ ar[i][1] ] = i;
                        }
                }
        }
}
