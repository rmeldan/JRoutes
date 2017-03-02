package com.softserve.edu.jroutes.service;

import java.util.List;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * Main interface of service level
 * 
 * @param <E>
 */

public interface ElementService<E> {
	void addElement(E element) throws NonUniqueException;

	void updateElement(E element) throws NonUniqueException;

	List<E> getAllElements();

	void deleteElement(E element);

	List<E> getElementsByCriteria(Object... criteria);

	E getElementByID(Long elementId);
}