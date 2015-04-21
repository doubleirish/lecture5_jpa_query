package edu.uw.data.lecture5.dao;


import edu.uw.data.lecture5.model.Customer;
import edu.uw.data.lecture5.model.Employee;
import edu.uw.data.lecture5.model.Office;
import edu.uw.data.lecture5.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Embedded database is  always initialized cleanly  as its stored in the target sub dir which is cleared out on each run
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/queryapp-spring.xml", "classpath:/datasource-standalone-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)

public class ClassicDaoStandAloneTest extends AbstractTransactionalJUnit4SpringContextTests {

    static final Logger log = LoggerFactory.getLogger(ClassicDaoStandAloneTest.class);

    @Resource
    private ClassicDao classicDao;

    @Override
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }


    @Test
    public void findAllCustomersInCalifornia() {
        List<Customer> customers = classicDao.findAllCustomersInUsState("CA");
        for (Customer customer : customers) {
            log.info("customers in california " + customer);
        }
    }

    @Test
    public void findOrdersForCustomer() {
        String customerName = "CAF Imports";
        Customer customer = classicDao.findCustomerByCustomerName(customerName);
        log.info("found customer " + customer);
        assertThat(customer.getCustomerName(), is(customerName));

        List<Order> orders = classicDao.findRecentOrdersForCustomer(customer);
        for (Order order : orders) {
            log.info("order    :" + order);
            log.info("  detail :" + order.getOrderDetail());
        }
    }

    @Test
    public void findAllOffices() {
        List<Office> offices = classicDao.findAllOffices();
        for (Office office : offices) {
            log.info("office " + office);
        }
    }

    @Test
    public void findSalesOfficeForEachCustomer() {
        List<Object[]> list_OfficeCity_CustomerName = classicDao.findSalesOfficeForEachCustomer();
        for (Object[] offCust : list_OfficeCity_CustomerName) {
            System.out.println("the sales office for customer " + offCust[1] + " is " + offCust[0]);
        }
    }

    @Test
    public void findCustomersByExample() {
        Customer customerExample = new Customer();
        customerExample.setState("CA");
        customerExample.setCity("San Francisco");

        List<Customer> customers = classicDao.findCustomers_QueryByExample(customerExample);
        log.info("found  " + customers.size() + " matching customers for  " + customerExample);
        for (Customer customer : customers) {

            log.info("matching customer " + customer);
        }
    }


    @Test
    public void findCustomersByCriteriaOR() {
        Customer customerExample = new Customer();
        customerExample.setState("CA");
        customerExample.setCity("San Francisco");

        List<Customer> customers = classicDao.findCustomersByCriteriaOR(customerExample);
        log.info("found  " + customers.size() + " matching customers for  " + customerExample);
        for (Customer customer : customers) {
            log.info("matching customer " + customer);
        }
    }

    @Test
    public void findEmployeeByNameCriteria() {
        Employee search = new Employee();//'Thompson','Leslie'
        search.setFirstName("Leslie");
        search.setLastName("Thompson");

        List<Employee> employees = classicDao.findEmployeeByNameCriteriaAND(search);
        log.info("found  " + employees.size() + " matching employees for  " + search);
        for (Employee employee : employees) {
            log.info("matching employee " + employee);
        }
    }

    @Test
    public void findCustomerByCustNum_OK() {
        List<Customer> customers = classicDao.findCustomerByCustNum_STRING_CONCAT_BAD("333");
        for (Customer customer : customers) {
            log.info("customer 333 " + customer);
        }
    }

    @Test
    public void findCustomerByCustNum_SQL_INJECTION() {
        List<Customer> customers = classicDao.findCustomerByCustNum_STRING_CONCAT_BAD("333 OR c.customerNumber is not null");
        for (Customer customer : customers) {
            log.info("customer with injection " + customer);
        }
    }

    @Test
    public void findCustomerInStateWithCreditLimitGreaterThan() {

        String usState = "CA";
        Double minimumCreditLimit =50_000.0;

        List<Customer> customers = classicDao.findCustomerInStateWithCreditLimitGreaterThan(usState, minimumCreditLimit);
        for (Customer customer : customers) {
            log.info("reputable customer " + customer.getCustomerName() + " in  " + usState + " can buy up to " + customer.getCreditLimit());
            assertThat(customer.getCreditLimit(),is(greaterThan(minimumCreditLimit)));
        }
    }

    @Test
    public void findCustomerByFirstAndLast_namedQuery() {

        List<Customer> customers =classicDao.findCustomerByFirstAndLast_namedQuery("Jean", "King");
        for (Customer customer : customers) {
          assertThat(customer.getContactFirstname(),is("Jean"));
        }

    }

}