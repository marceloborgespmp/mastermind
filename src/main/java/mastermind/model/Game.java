package mastermind.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mastermind.util.GameHelper;

@Entity
@XmlRootElement(name = "game")
public class Game {
	 private static final long serialVersionUID = 1L;

	// Persistent Fields:
	@Id @GeneratedValue
	private Long id;
	
	private Date created;
	private Long userId;
	private boolean multiplayer;
	private String secret;
	private Long userIdMatched;
	private int status;
	
	@OneToMany(mappedBy="game")
	private List<Round> rounds;
	
	public Game(){	
	}
	
	public Game (Long userId, boolean multi, int size){
		this.userId = userId;
		this.multiplayer = multi;
		this.secret = GameHelper.generateSecret(size);
		this.created = new Date();
		this.status = 0;
	}

	public Long getId() {
		return id;
	}
	
	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	@XmlElement
	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getUserId() {
		return userId;
	}

	@XmlElement
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	@XmlElement
	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	public String getSecret() {
		return secret;
	}

	@XmlElement
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getUserIdMatched() {
		return userIdMatched;
	}

	@XmlElement
	public void setUserIdMatched(Long userIdMatched) {
		this.userIdMatched = userIdMatched;
	}

	public int getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(int status) {
		this.status = status;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	@XmlElement
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", created=" + created + ", userId=" + userId
				+ ", multiplayer=" + multiplayer + ", status=" + status +", secret=" + secret + "]\n"
				+ "  rounds: " + rounds;
	}
	
}
