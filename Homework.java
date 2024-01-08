/*
 * File: Homework.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */
import stanford.karel.*;

public class Homework extends SuperKarel {
	int count=0,x=1 , y=1;
	public void run()
	{
	    // scan the map to get the Dimensions
		getDim();
		// invoke the divideMap then print the number of steps.
		divideMap(x,y);
		System.out.println("number of steps : " + count);
	}
	private void getDim(){
		// scan the map to get the Dimensions
		while(true){
			//scan the x-axis
			if(frontIsClear()){
				move();
				x++;
			}
			else break;
		}
		turnLeft();
		//scan the y-axis
		while(true){
			if(frontIsClear()){
				move();
				y++;}
			else break;
		}
		// back to the origin:
		for(int i =0 ; i<2 ; i++) {
			turnLeft();
			while (true) {
				if (frontIsClear()) move();
				else break;
			}
		}
		// face front
		turnLeft();
	}

	private void divideMap( int x, int y) {
		if (x >= 3 && y >= 3) {
			if (x % 2 == 0 && y % 2 == 0) {
				if (x == y) squareEven(x, y);
			    else Evenmaps(x,y);}
			else if (x % 2 != 0 && y % 2 != 0) oddMaps(x, y);
			else oddEven(x, y);
		}

		else if( (x==2 && y>=3) || (y==2 && x>=3))hasDimTwo(x,y);

		else if ((x==1 && y>=3) || (y==1 && x>=3)) hasDimOne(x,y);
		// 2*2 is a corner case,so I handled it here
		else if(x==2 && y==2){
			putBeeper();
			move();
			turnLeft();
			move();
			putBeeper();
		}
		else {
			System.out.println("Can't be divided");
		}

	}

	private void hasDimOne(int x,int y){
		// deciding which is bigger because if y is bigger i have to turn left first.
		int bigger = (x>y)?x:y;
		if(bigger==y) turnLeft();
		int step=0;
		// odd dim - I have two corner cases where I have the bigger or = 3 or 5.
		if(bigger == 3){
			countMove();
			putBeeper();
			return;
		} else if (bigger == 5){
			countMove();
			putBeeper();
			countMove();
			countMove();
			putBeeper();
			return;
		}
		else if(bigger ==6){
			for(int i=0; i <3; i++){
				if(i!=0) countMove();
				putBeeper();
				if(i!=2) countMove();

			}
			return;}
		else if(bigger==4){
			putBeeper();
			countMove();
			countMove();
			putBeeper();
			return;
		}
		// if bigger is an odd Number
		if(bigger%2 != 0 ){
			if(bigger == x)
			{  step = (x-5)/2; 	move();}
			else {
				step = (y-5)/2;
			    countMove();}}
		else{
			if (bigger == x) {
				step = ((x-6)/2) ;
			} else {
				step = ((y-6)/2);

			}}

			for(int i =0 ; i<step ; i++){
				putBeeper();
				countMove();
			}
			countMove();
			putBeeper();
			countMove();
			if(bigger%2==0) move();
			for(int i =0 ; i<step ; i++){
				if(bigger%2!=0)countMove();
				putBeeper();
				if(bigger%2==0)countMove();
			}
			if(bigger%2==0){
			countMove();
			putBeeper();}
	}

	private void hasDimTwo(int x,int y){
		int bigger = (x>y)?x:y;
		int rem = bigger/2;
		// for odd*2 maps i will convert it to 2*(even) maps
		if(bigger%2 != 0 ) cornerCases2Dim(x,y);
		// dealing with maps with 2x(even dims) is not the same always , it depends on the remaining
		// if the remaining is odd ill put 2 more Beepers so now all the cases like even with even remaining
		if(rem%2!=0) cornerCases2Dim(x,y);
		// now for even
		int split=0;
		if (y>x) {split = y / 4;
			turnLeft();
		}
		else split = x/4;
        // the basic
		for (int i = 1; i <= 4; i++) {
			for (int j = 0; j < split; j++) {
				putBeeper();
				// in the last iteration in the outer loop and when i have odd remaining i'll put a 2 beepers parallel to the edge
				if(i==4 && j==(split-1)){
					if(rem%2!=0 && i==4){
						countMove();
						putBeeper();
						if(x>y) {
							if (rem % 2 == 0) turnLeft();
							else turnRight();
						}
						else{
							if (rem % 2 == 0) turnRight();
							else turnLeft();
						}
						countMove();
						putBeeper();
						return;
					}
					else return;
				}
				countMove();
			}
			// to control to movement.
			if(x>y){
				if (i % 2 != 0) {
					// odd iteration , moving from bottom to top
					turnLeft();
					countMove();
					turnRight();
				} else { // else top to bottom
					turnRight();
					countMove();
					turnLeft();
				}
			}
			else{
				if (i % 2 != 0) {
					turnRight();
					countMove();
					turnLeft();}
				else{
					turnLeft();
					countMove();
					turnRight();
				}
			}
		}
	}
    private void countMove(){
		move();
		count++;
	}
	private void moveHalfmap(int x){
		if(x%2 !=0){
			x+=1;
		}
		for(int i = 1 ; i < (x/2) ; i++){
			countMove();
		}
	}
	private void putAllBeepers(int x){
		for(int i=1 ; i<x ; i++){
			if(frontIsBlocked()){
				return;
			}
			countMove();
			if(noBeepersPresent())
			putBeeper();
		}
	}
	private void  oddMaps(int x,int y){
		turnLeft();
		moveHalfmap(y);
		putBeeper();
		turnRight();
		putAllBeepers(x);
		turnLeft();
		moveHalfmap(y);
		turnLeft();
		moveHalfmap(x);
		putBeeper();
		turnLeft();
		putAllBeepers(y);

	}
	private void squareEven(int x,int y){
		putBeeper();
		moveDiagonal(x);
		moveHalfmap(x);
		moveHalfmap(x);
		countMove();
		putBeeper();
		turnLeft();
		moveDiagonal(x);
	}
	private void moveDiagonal(int x){
		for(int i = 0 ; i<x ; i++){

			if(frontIsBlocked()){
				turnLeft();
				turnLeft();

				break;
			}
			countMove();
			turnLeft();
			countMove();
			putBeeper();
			turnRight();
		}
	}
	private void oddEven(int x,int y){
		// in the two cases i'll got to (0,(y/2))
		turnLeft();
		moveHalfmap(y);
		putBeeper();
		turnRight();
		putAllBeepers(x);
		turnLeft();
//		if y is even i am going to put second wall then i'll go the (x/2,y) in the line under the else
		if(y%2 ==0 ){
			countMove();
			putBeeper();
			turnLeft();
			putAllBeepers(x);
			turnRight();
    		moveHalfmap(y);
		    turnRight();}
// if y is odd i am going to turn left (back) to the x/2
		else turnLeft();
     	moveHalfmap(x);
		 // if y is even , i'll put beepers along mid x with length = y
	   	if(y%2 ==0 ) {
	    putBeeper();
		turnRight();
		putAllBeepers(y);}
		   // y even is done
		else turnLeft();
		// i did this for optimization , where if i walked back to the middle it'll be less steps than if i went like the y is even status
		if(y%2!=0){
			putAllBeepers(y);
    		turnRight();
			countMove();
			putBeeper();
    		turnRight();
//			// put beepers along the second wall
	    	putAllBeepers(y);
  		    turnRight();
			countMove();
			putBeeper();
			turnRight();
			// put beepers along the last half / (y/2)/2 becuase there is no wall to block and i dont want it to put along all y
			putAllBeepers((y/2));
		}
	}
	private void Evenmaps(int x , int y){
		// going to (0,y/2) to start the spliting there
		turnLeft();
		moveHalfmap(y);
		putBeeper();
		turnRight();
		// put beepers along x-axis
		putAllBeepers(x);
		turnLeft();
		countMove();
		putBeeper();
		// put bepeers in double walls
		turnLeft();
		putAllBeepers(x);
		turnRight();
		turnRight();
		// going back to the middle to start the double walls from the middle of the map & put
        // beepers along y/2 there is a if block condition so when it reaches y/2 it will return
		moveHalfmap(x);
		turnLeft();
		putAllBeepers(y);
		turnRight();
		countMove();
		putBeeper();
		turnRight();
		// put beepers along the second wall
		putAllBeepers(y);
		turnRight();
		countMove();
		putBeeper();
		turnRight();
		// put beepers along the last half / (y/2)/2 becuase there is no wall to block and i dont want it to put along all y
		putAllBeepers((y/2)-1);
	}

	private void cornerCases2Dim(int x,int y){
		if(x>y){
			turnLeft();
			countMove();
			putBeeper();
			turnRight();
			turnRight();
			countMove();
			turnLeft();
			putBeeper();
			countMove();}
		else{
			countMove();
			putBeeper();
			turnLeft();
			turnLeft();
			countMove();
			putBeeper();
			turnRight();
			countMove();
			turnRight();
		}
	}
}


