package mastermind.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "round")
public class Round {
	 private static final long serialVersionUID = 2L;

	 @ManyToOne(optional=false)
	 private Game game;
	 
	 private int index;
	 private Date created;
	 private String guess;
	 private String matches;
	
	 public Round(){
	 }

	 public Round(Game game, int index, String guess, String matches){
		 this.game = game;
		 this.index = index;
		 this.guess = guess;
		 this.created = new Date();
		 this.matches = matches;
	 }
	 
	public Game getGame() {
		return game;
	}
	@XmlElement
	public void setGame(Game game) {
		this.game = game;
	}
	public Date getCreated() {
		return created;
	}
	@XmlElement
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getGuess() {
		return guess;
	}
	@XmlElement
	public void setGuess(String guess) {
		this.guess = guess;
	}
	public String getMatches() {
		return matches;
	}
	@XmlElement
	public void setMatches(String matches) {
		this.matches = matches;
	}
	public int getIndex() {
		return index;
	}
	@XmlElement
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "   Round [index=" + index + ", created=" + created + ", guess="
				+ guess + ", matches=" + matches + "]\n";
	}

	 
}
