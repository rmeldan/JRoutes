package com.softserve.edu.jroutes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("transportService")
public class TransportService implements ElementService<Transport>{
	@Autowired
	private ElementDAO<Transport> transportDAO;

	@Override
	public void addElement(Transport element) throws NonUniqueException {
		transportDAO.addElement(element);
	}

	@Override
	public void updateElement(Transport element)
			throws NonUniqueException {
		transportDAO.updateElement(element);
	}

	@Override
	public List<Transport> getAllElements() {
		return transportDAO.getAllElements();
	}

	@Override
	public void deleteElement(Transport element) {
		transportDAO.deleteElement(element);
	}

	@Override
	public List<Transport> getElementsByCriteria(Object... criteria) {
		return transportDAO.getElementsByCriteria(criteria);
	}

	@Override
	public Transport getElementByID(Long elementId) {
		return transportDAO.getElementByID(elementId);
	}
}