package com.softserve.edu.jroutes.component;

import com.softserve.edu.jroutes.dto.*;
import com.softserve.edu.jroutes.entity.*;
import com.softserve.edu.jroutes.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.*;

/**
 * @author Roman
 */

@Component
public class AlgorithmComponent {
	@Autowired
	private SavedRouteService savedRouteService;
	@Autowired
	private ElementService<Transport> transportService;	
	@Autowired
	private RegUserInterface registeredUserObject;
	private static final Logger LOGGER = Logger.getLogger(AlgorithmComponent.class);
	public void showFoundRoutes(RoutePoint depart, RoutePoint arrive,
			RoutePoint transfer, RouteConnectionExDTO dto, ModelMap model) {
		long timeStart = System.currentTimeMillis();
		User user = registeredUserObject.getRegisteredUserObject();
		Transport[] trs = new Transport[4];
		boolean isTransport = false;
		if (dto.getTransport0() != null) {
			trs[0] = transportService.getElementsByCriteria(dto.getTransport0()).get(0);
			isTransport = true;
		}
		if (dto.getTransport1() != null) {
			trs[1] = transportService.getElementsByCriteria(dto.getTransport1()).get(0);
			isTransport = true;
		}
		if (dto.getTransport2() != null) {
			trs[2] = transportService.getElementsByCriteria(dto.getTransport2()).get(0);
			isTransport = true;
		}
		if (dto.getTransport3() != null) {
			trs[3] = transportService.getElementsByCriteria(dto.getTransport3()).get(0);
			isTransport = true;
		}
		if (!isTransport) {
			trs = null;
		}
		user.setSavedRoutesAlgorithm(savedRouteService.getBuiltRoutes(depart,
				arrive, trs, (long) dto.getPrice(), (long) dto.getTime()));
		List<SavedRouteDto> sRDtoList = new LinkedList<SavedRouteDto>();
		long i = -1;
		for (SavedRouteAlgorithm savedRouteAlgorithm : user.getSavedRoutesAlgorithm()) {
			++i;
			if (savedRouteAlgorithm.getRoutesAlgorithm().size() == 1) {
				continue;
			}
			Long price = 0L;
			Long time = 0L;
			boolean isTransfer = false;
			if (transfer == null) {
				isTransfer = true;
			}
			for (RouteAlgorithm routeAlgorithm : savedRouteAlgorithm.getRoutesAlgorithm()) {
				price += routeAlgorithm.getRouteConnectionId().getPrice();
				time += routeAlgorithm.getRouteConnectionId().getTime();
				if (!isTransfer	&& (transfer.getId() == routeAlgorithm.getRouteConnectionId().getRoutePointAId().getId() 
						|| transfer.getId() == routeAlgorithm.getRouteConnectionId().getRoutePointBId().getId())) {
					isTransfer = true;
				}
			}
			if (!isTransfer) {
				continue;
			}
			SavedRouteDto sRDto = new SavedRouteDto();
			sRDto.setId(i);
			sRDto.setPrice(price);
			sRDto.setTime(time / 60 + ":" + String.format("%02d", time % 60));
			sRDto.setStartPoint(depart);
			sRDto.setFinishPoint(arrive);
			sRDtoList.add(sRDto);
		}
		model.addAttribute("sRDtoList", sRDtoList);
		long timeFinish = System.currentTimeMillis();
		LOGGER.info("The algorithm worked " + (timeFinish - timeStart) + " milliseconds");
	}	
}
