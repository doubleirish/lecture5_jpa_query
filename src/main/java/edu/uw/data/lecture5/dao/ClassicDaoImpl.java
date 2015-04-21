package edu.uw.data.lecture5.dao;

import edu.uw.data.lecture5.model.*;
import edu.uw.data.lecture5.model.Order;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;

import java.util.List;

/**
 * simple single-table Jdbc example with try-resources and datasource
 */
@Transactional
@Repository("classicDao")


public class ClassicDaoImpl implements ClassicDao {

    static final Logger log = LoggerFactory.getLogger(ClassicDaoImpl.class);


    @PersistenceContext
    private EntityManager em;

    public List<Customer> findAllCustomersInUsState(String usState) {

        return em.createQuery(
                "SELECT c FROM Customer c WHERE c.state = :state", Customer.class) // TODO Case of entity "Customer" and fields are important
                .setParameter("state", usState)
                        //.setMaxResults(10)
                .getResultList();
    }


    public Customer findCustomerByCustomerName(String customerName) {

        return (Customer) em.createQuery(
                "SELECT c FROM Customer c WHERE c.customerName = :customerName")
                .setParameter("customerName", customerName)
                .getSingleResult();
    }

    public  List<Customer> findCustomerByCustNum_STRING_CONCAT_BAD(String customerNum) {

        return   em.createQuery(
                "SELECT c FROM Customer c WHERE c.customerNumber = "+customerNum, Customer.class)
                .getResultList();
    }


    public List<Order> findRecentOrdersForCustomer(Customer cust) {

        return em.createQuery(
                "SELECT o from Order o   " +
                        " WHERE o.customer = :cust " +
                        " order by orderDate ", Order.class)
                .setParameter("cust", cust)
                .setMaxResults(10)
                .getResultList();
    }


    public List<Office> findAllOffices() {
        return em.createQuery("FROM Office", Office.class).getResultList();
    }


    public List<Object[]> findSalesOfficeForEachCustomer() {
        return em.createQuery("select o.city, c.customerName FROM Office o, Customer c where c.salesRep.office =o order by c.customerName "
        ).getResultList();
    }

    public List<Customer> findCustomers_QueryByExample(Customer customer) {
        Session session = (Session) em.getDelegate();

        Example customerExample = Example.create(customer);
        Criteria criteria = session.createCriteria(Customer.class).add(customerExample);

        return (List<Customer>) criteria.list();

    }

    public List<Customer> findCustomersByCriteriaOR(Customer search) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Customer> q = cb.createQuery(Customer.class);

        Root<Customer> c = q.from(Customer.class);
        q.select(c);
        String cityVal = search.getCity();
        if(StringUtils.isNotBlank(cityVal)) {
            q.where(cb.equal(c.get("city")  , cityVal));
        }
        // defaults to "or" relationship E.G city or state
        String stateVal = search.getState();
        if(StringUtils.isNotBlank(stateVal)) {
            q.where(cb.equal(c.get("state")  , stateVal));
        }
        q.orderBy(cb.asc(c.get("state")),cb.asc(c.get("city")),cb.asc(c.get("addressLine1")));
        return em.createQuery(q).getResultList();

    }


    public List<Employee> findEmployeeByNameCriteriaAND(Employee search) {
        String firstName = search.getFirstName();
        String lastName = search.getLastName();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> emp = cq.from(Employee.class);
        cq.select(emp);

        Predicate where = cb.conjunction();

        if (StringUtils.isNotBlank(firstName)) {
            where = cb.and(where, cb.equal(emp.get("firstName"), firstName));  //TODO can break if Employeefield name changes
        }
        //optional AND
        if (StringUtils.isNotBlank(lastName)) {
            where = cb.and(where, cb.equal(emp.get("lastName"), lastName));
        }
        cq.where(where);

        TypedQuery<Employee> query = em.createQuery(cq);
        return query.getResultList();

    }



//    public List<Customer> findCustomerInStateWithCreditLimitGreaterThan(String usState, Double minimumCreditLimit) {
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//
//        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
//
//        Root<Customer> emp = cq.from(Customer.class);
//        cq.select(emp);
//
//        Predicate andWhere = cb.conjunction();
//
//            //  renaming the customer state field now will result in a compile time error
//
//
//            andWhere = cb.and(andWhere, cb.equal(emp.get(Customer_.state), usState));
//            andWhere = cb.and(andWhere, cb.greaterThan(emp.get(Customer_.creditLimit), minimumCreditLimit));
//
//        cq.where(andWhere);
//
//        TypedQuery<Customer> query = em.createQuery(cq);
//        return query.getResultList();
//
//    }

    public List<Customer> findCustomerByFirstAndLast_namedQuery(String first, String last) {

        return em.createNamedQuery("Customer.findByCustomerFirstAndLast", Customer.class)
                .setParameter("firstName", first)
                .setParameter("lastName", last)
                .getResultList();
    }

    public List<Order> findAllOrders_namedQuery(String first, String last) {

        return em.createNamedQuery("Order.findAll", Order.class)
                .getResultList();
    }


	@Override
	public List<Customer> findCustomerInStateWithCreditLimitGreaterThan(
			String usState, Double minimumCreditLimit) {
		// TODO Auto-generated method stub
		return null;
	}
}
