package edu.uw.data.lecture5.dao;


import edu.uw.data.lecture5.model.Customer;
import edu.uw.data.lecture5.model.Employee;
import edu.uw.data.lecture5.model.Office;
import edu.uw.data.lecture5.model.Order;

import java.util.List;

/**
 * Created by credmond on 03/03/15.
 */
 interface ClassicDao {

   List<Customer> findAllCustomersInUsState(String usState);
   Customer findCustomerByCustomerName(String customerName);
   List<Order> findRecentOrdersForCustomer(Customer customer);
      List<Customer> findCustomerByCustNum_STRING_CONCAT_BAD(String customerNum) ;

  // office employees
   List<Office> findAllOffices();
   List<Object[]> findSalesOfficeForEachCustomer();

    // QBE Example
     List<Customer> findCustomers_QueryByExample(Customer cust) ;


    //Criteria untyped api
     List<Customer> findCustomersByCriteriaOR(Customer customerSearch) ;
     List<Employee> findEmployeeByNameCriteriaAND(Employee search) ;


    //Criteria typed api
     List<Customer> findCustomerInStateWithCreditLimitGreaterThan(String usState, Double minimumCreditLimit);
    
    //named Query
     List<Customer> findCustomerByFirstAndLast_namedQuery(String first, String last) ;


    }
