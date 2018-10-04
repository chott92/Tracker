/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.model;

import de.chott.tracker.enums.IncentiveType;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.Type;

/**
 *
 * @author cot
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Incentive.findByEvent", query = "SELECT i FROM Incentive i WHERE i.event = :paramEvent")
	,
    @NamedQuery(name = "Incentive.findUpcomingBidwarsForEvent",
			query = "SELECT i FROM Incentive i WHERE i.game.startTime>=CURRENT_TIMESTAMP AND i.incentiveType = 'BID_WAR' AND i.event = :paramEvent")
	,
	@NamedQuery(name = "Incentive.findAllUpcomingForEvent",
			query = "SELECT i FROM Incentive i WHERE i.game.startTime>=CURRENT_TIMESTAMP AND i.event = :paramEvent ORDER BY i.game.startTime ASC")
})
public class Incentive extends BaseEntity {

	@ManyToOne
	private Game game;

	private String incentiveName;

	@Type(type = "text")
	private String description;

	private float targetAmount;

	@Enumerated(EnumType.STRING)
	private IncentiveType incentiveType;

	@ManyToOne
	private Event event;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getIncentiveName() {
		return incentiveName;
	}

	public void setIncentiveName(String incentiveName) {
		this.incentiveName = incentiveName;
	}

	public float getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(float targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IncentiveType getIncentiveType() {
		return incentiveType;
	}

	public void setIncentiveType(IncentiveType incentiveType) {
		this.incentiveType = incentiveType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 67 * hash + Objects.hashCode(this.game);
		hash = 67 * hash + Objects.hashCode(this.incentiveName);
		hash = 67 * hash + Objects.hashCode(this.description);
		hash = 67 * hash + Float.floatToIntBits(this.targetAmount);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Incentive other = (Incentive) obj;
		if (!Objects.equals(this.game, other.game)) {
			return false;
		}
		if (!Objects.equals(this.incentiveName, other.incentiveName)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (Float.floatToIntBits(this.targetAmount) != Float.floatToIntBits(other.targetAmount)) {
			return false;
		}
		return true;
	}

}
