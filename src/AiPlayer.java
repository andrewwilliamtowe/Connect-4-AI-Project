import java.util.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * The constructor essentially does nothing. 
 * 
 * @author james spargo
 *
 */

public class AiPlayer 
{
    private static int depth; //this says what the maximum depth we are allowed to go
    private static int idiotcounter; //for all of those people who intentionaly try to break code.
    private Scanner reader;
    private int AI;
    private int PC;
    private static int Alpha=Integer.MIN_VALUE;
    private static int Beta=Integer.MAX_VALUE;
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    public AiPlayer(int d)
    {
        reader=new Scanner(System.in);
        depth=d;
	// nothing to do here
    }

    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like
     * to play in.
     */
    public int findBestPlay( GameBoard currentGame ,int whoseturn)
    {
        if(whoseturn==1){
            AI=1;
            PC=2;

        }else{
            AI=2;
            PC=1;

        }
        Alpha=Integer.MIN_VALUE;
        Beta=Integer.MAX_VALUE;
	int playChoice=max(currentGame,depth,Alpha, Beta)[0];
	return playChoice;
    }
    public int AIMove(GameBoard currentGame,int whoseturn){
        return findBestPlay(currentGame,whoseturn);
    }
    public int PCMove(GameBoard currentGame){
        currentGame.printGameBoard();
        System.out.println("Players Turn");
        int move=0;
        System.out.print("move: ");
        move=reader.nextInt();
        if(!currentGame.isValidPlay(move) || move<0 ||move>6){
            if(idiotcounter<7) {
                idiotcounter++;
                System.out.println("That is an invalid move. Try again");
                move = PCMove(currentGame);
            }else{
                System.out.println("You are obviously not taking this game seriously.\n The game shall shut down now");
                System.exit(0);
            }
        }
        return move;
    }

    private int[] max(GameBoard gm,int d, int Alpha, int Beta){

    int[] answer=new int[2];
    int move=0;
    int value=Integer.MIN_VALUE;
    if(d==0||gm.getPieceCount()==42){
        answer[0]=0;
        answer[1]= gm.getScore(AI)-gm.getScore(PC);
        return answer;
    }
        int store;
    for(int i=0; i<7;i++){
        if(gm.playPiece(i)) {
            store=min(gm,d-1,Alpha,Beta)[1];
            if (value < store) {
                value = store;
                move = i;
            }
            gm.removePiece(i);
        }
        if(value>=Beta){
            answer[0]=move;
            answer[1]=value;
            return answer;
        }
        if(value>Alpha) {
            Alpha = value;
        }

    }

    answer[0]=move;
    answer[1]=value;
    return answer;
}
    private int[] min(GameBoard gm,int d, int Alpha, int Beta){
        int[] answer=new int[2];
        int move=0;
        int value=Integer.MAX_VALUE;
        if(d==0||gm.getPieceCount()==42){
            answer[0]=0;
            answer[1]= gm.getScore(AI)-gm.getScore(PC);
            return answer;
        }
        int store;
        for(int i=0; i<7;i++){
            if(gm.playPiece(i)) {
                store=max(gm,d-1,Alpha, Beta)[1];
                if (value > store) {
                    value = store;
                    move = i;
                }
                gm.removePiece(i);
            }
            if(value<=Alpha){
                answer[0]=move;
                answer[1]=value;
                return answer;
            }
            if(value<Beta) {
                Beta = value;
            }
        }
        answer[0]=move;
        answer[1]=value;
        return answer;
    }
}
