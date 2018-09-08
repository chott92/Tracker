package de.chott.tracker.rest;

import de.chott.tracker.boundary.EventService;
import de.chott.tracker.boundary.IncentiveService;
import de.chott.tracker.model.Event;
import de.chott.tracker.model.Incentive;
import de.chott.tracker.model.incentiveOutput.IncentiveOutput;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/incentives")
public class IncentiveInformationRestService {

	@Inject
	private IncentiveService incentiveService;
	@Inject
	private EventService eventService;

	@GET
	@Path("/event/{id}/upcoming")
	@Produces(MediaType.APPLICATION_JSON)
	public List<IncentiveOutput> getUpcomingIncentivesForEvent(@PathParam("id") String eventIdString) {
		Event event = eventService.loadEvent(Long.valueOf(eventIdString));
		List<Incentive> upcomingIncentives = incentiveService.loadUpcomingIncentivesForEvent(event);

		return getIncentiveOutputForIncentives(upcomingIncentives);
	}

	private List<IncentiveOutput> getIncentiveOutputForIncentives(List<Incentive> incentives) {
		List<IncentiveOutput> outputList = new ArrayList<>();

		for (Incentive incentive : incentives) {
			outputList.add(new IncentiveOutput(incentive, incentiveService));
		}

		return outputList;
	}

}
