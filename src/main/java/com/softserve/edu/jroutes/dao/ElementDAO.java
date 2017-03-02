package com.softserve.edu.jroutes.dao;

import java.util.List;

import org.hibernate.Session;

import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;

public interface ElementDAO<E> {
	void addElement(E element) throws NonUniqueException;

	void updateElement(E element) throws NonUniqueException;

	List<E> getAllElements();

	void deleteElement(E element);

	List<E> getElementsByCriteria(Object... criteria);

	E getElementByID(Long elementId);

	E checkForUnique(E element, Session session) throws NonUniqueException;

	
}