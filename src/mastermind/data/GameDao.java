package mastermind.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import mastermind.model.Game;
import mastermind.model.Round;

public class GameDao {
	
	public EntityManagerFactory emf; 
	   
	private EntityManager getEntityManager() {
	   if (emf==null){
		   emf = Persistence.createEntityManagerFactory("$objectdb/db/mastermind.odb");
	   }
		return emf.createEntityManager();
	}
	
	public List<Game> getAllGames(){
		
		EntityManager em = getEntityManager();
		
        List<Game> gameList = em.createQuery(
                "SELECT g FROM Game g WHERE g.status = 0", Game.class).getResultList();
        
        return gameList;
	}

	public Game findGame(Long gameId){
		
		EntityManager em = getEntityManager();
		
        Game game = em.find(Game.class, gameId);
        
        return game;
	}

	public Game addGame(Long userId, boolean multi, int level) {
		EntityManager em = getEntityManager();
		
        em.getTransaction().begin();
        Game newGame = em.merge(new Game(userId, false, level));
        em.getTransaction().commit();
        
        return newGame;
	}
	
	public Round addRound(Game game, int index, String guess, String matches){
		EntityManager em = getEntityManager();
		
        em.getTransaction().begin();
        game = em.merge(game);
        Round round = em.merge(new Round(game, index, guess, matches));
        em.getTransaction().commit();

        return round;
	}
}
