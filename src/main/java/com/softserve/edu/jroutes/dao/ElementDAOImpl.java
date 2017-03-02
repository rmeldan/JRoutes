package com.softserve.edu.jroutes.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.softserve.edu.jroutes.exception.NonUniqueException;

abstract class ElementDAOImpl<E> implements ElementDAO<E> {
	private Class<E> elementClass;
	@Autowired
	protected SessionFactory sessionFactory;

	public ElementDAOImpl(Class<E> elementClass) {
		this.elementClass = elementClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getAllElements() {
		return sessionFactory.getCurrentSession().createCriteria(elementClass)
				.list();
	}

	@Override
	public void addElement(E element) throws NonUniqueException {
		Session session = sessionFactory.getCurrentSession();
		checkForUnique(element, session);
		session.save(element);
	}

	@Override
	public void deleteElement(E element) {
		sessionFactory.getCurrentSession().delete(element);
	}

	@Override
	public void updateElement(E element) throws NonUniqueException {
		Session session = sessionFactory.getCurrentSession();
		E e = checkForUnique(element, session);
		session.update(e);
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getElementByID(Long elementId) {
		return (E) sessionFactory.getCurrentSession().get(elementClass,
				elementId);
	}
}