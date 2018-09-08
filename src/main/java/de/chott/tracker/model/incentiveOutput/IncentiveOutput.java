package de.chott.tracker.model.incentiveOutput;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.IncentiveValue;
import java.util.ArrayList;
import java.util.List;

public class IncentiveOutput {

	private String game;
	private String incentiveName;
	private String description;
	private float targetAmount;

	@JsonProperty
	private List<IncentiveValueOutput> values;

	public IncentiveOutput(Incentive incentive, IncentiveService incentiveService) {
		game = incentive.getGame().getName();
		incentiveName = incentive.getIncentiveName();
		description = incentive.getDescription();
		targetAmount = incentive.getTargetAmount();

		this.values = loadValues(incentive, incentiveService);

	}

	public String getGame() {
		return game;
	}

	public String getIncentiveName() {
		return incentiveName;
	}

	public String getDescription() {
		return description;
	}

	public float getTargetAmount() {
		return targetAmount;
	}

	public List<IncentiveValueOutput> getValues() {
		return values;
	}

	private List<IncentiveValueOutput> loadValues(Incentive incentive, IncentiveService incentiveService) {
		List<IncentiveValueOutput> values = new ArrayList<>();

		for (IncentiveValue iv : incentiveService.loadBidwarValues(incentive)) {
			values.add(new IncentiveValueOutput(iv.getValue(), incentiveService.loadCurrentIncentiveAmount(iv)));
		}

		return values;
	}

	public class IncentiveValueOutput {

		private String name;
		private double value;

		private IncentiveValueOutput(String name, double incentiveAmount) {
			this.name = name;
			this.value = incentiveAmount;
		}

		public String getName() {
			return name;
		}

		public double getValue() {
			return value;
		}
	}
}
