package com.softserve.edu.jroutes.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Oleg
 */

@Repository("countryDAO")
public class CountryDAO extends ElementDAOImpl<Country> {
    public CountryDAO() {
        super(Country.class);
    }
    
    @SuppressWarnings("unchecked")
	public Country checkForUnique(Country element, Session session) throws NonUniqueException {
		String countryName = element.getName();
		List<Country> cntList = session.createCriteria(Country.class).add(Restrictions.eq("name",countryName)).list();
		if (cntList.size() == 0) {
			return element;
		} else {
			Country cn1 = cntList.get(0);
			if ( cn1.getId() == element.getId()) {
			return cn1;
			} else {
				throw new NonUniqueException("This country already exists");
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Country> getElementsByCriteria(Object... criteria) {
		List<Country> countryList;
		
		if(criteria.length == 1){
		String s = (String) criteria[0];
				countryList = sessionFactory.getCurrentSession()
				.createCriteria(Country.class).add(Restrictions.eq("name", s))
				.list();
		}else{
			String country = (String)criteria[1];
			
			final String getCountry ="from Country where name like '"+ country+"%'";
				countryList = sessionFactory.getCurrentSession().createQuery(getCountry).list();
		}
		
		return countryList;
	}
}