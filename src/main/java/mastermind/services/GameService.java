package mastermind.services;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import mastermind.data.GameDao;
import mastermind.model.Game;
import mastermind.model.Round;
/**
 * Implements services for the MasterMind Game
 * 
 * @author Marcelo
 *
 */
@Path("/GameService")
public class GameService {

   private static final GameDao gameDao = new GameDao();
   
   private static final String SUCCESS_RESULT="<result>success</result>";
   private static final String FAILURE_RESULT="<result>failure</result>";

   /**
    * Returns all active games 
    * @return List games
    */
   @GET
   @Path("/games")
   @Produces(MediaType.APPLICATION_XML)
   public String getGames(){
	   List<Game> list = gameDao.getAllGames();
	   StringBuilder msg = new StringBuilder("\n");
	   for (Game game : list){
		   msg.append(game.toString()+"\n");
	   }
      return fmtMessage(msg.toString());
   }	
   
   /**
    * Returns game session detail
    * @param gameId
    * @return game session object
    */
   @GET
   @Path("/games/{gameid}")
   @Produces(MediaType.APPLICATION_XML)
   public String getGame(@PathParam("userid") Long gameId){
	   return fmtMessage(gameDao.findGame(gameId).toString());
   }
   
   /**
    * Starts a new Mastermind game session
    * @param userId	user´s id
    * @param multiplayer indicates multiplayer
    * @param level number of colors
    * @param servletResponse 
    * @return Game the game session object
    * @throws IOException
    */
   @PUT
   @Path("/start")
   @Produces(MediaType.APPLICATION_XML)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String startGame(@FormParam("userid") Long userId, 
		   @FormParam("multiplayer") boolean multiplayer,
		   @FormParam("level") int level,
		   @Context HttpServletResponse servletResponse) {
	   
	   Game game = gameDao.addGame(userId, multiplayer, level);
	   if (game!=null){
		   return fmtMessage("game started: "+game);
	   }else{
		   return FAILURE_RESULT;
	   }
   }
   
   /**
    * Process a guess from user  
    * @param gameId
    * @param guess
    * @param servletResponse
    * @return processing result
    */
   @PUT
   @Path("/round")
   @Produces(MediaType.APPLICATION_XML)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String addRound(@FormParam("gameid")Long gameId, 
		   @FormParam("guess") String guess,
		   @Context HttpServletResponse servletResponse) {
	   
       Game game = gameDao.findGame(gameId);
	   if (game==null || game.getStatus()>0){
		   return fmtMessage("round rejected: invalid game");
	   }
	   int qty;
	   if (game.getRounds()==null){
		   qty = 0;
	   } else {
	       qty = game.getRounds().size();
	   }
	   
       if (qty == 10) {
		   return fmtMessage("round rejected: limit reached");
       }
   	   
       StringBuilder matches = new StringBuilder("");
       StringBuilder matchO = new StringBuilder("");
       int pos = -1;
       for (int i=0;i<guess.length();i++){
    	   char cha = guess.charAt(i);
    	   pos = game.getSecret().indexOf(cha);
    	   if (pos>=0){
    		   if (pos==i){
    			   matches.append("x");
    		   } else {
    			   matchO.append("o");
    		   }
    	   }
       }
       matches.append(matchO);
       
       if (qty == 9 || guess.equals(game.getSecret())){
    	   game.setStatus(1);
       }
       Round round = gameDao.addRound(game, qty+1, guess, matches.toString());

	   if (round!=null){
	       if (guess.equals(game.getSecret())){
			   return fmtMessage("[------ YOU WIN -------] " + game.getSecret());
	       }
	       if (qty == 9) {
			   return fmtMessage("[----- YOU LOSE -------] " + game.getSecret());
	       }
		   return fmtMessage("round accepted: "+round.getGame());
	   }else{
		   return FAILURE_RESULT;
	   }
   }
   
   private String fmtMessage(String mess){
	   String message = "<result>message</result>";
	   message = message.replace("message", mess);
	   return message;
	   
   }
}