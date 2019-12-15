package com.company.adminapi.util.feign;

import com.company.adminapi.models.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {



    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    Invoice createInvoice(@RequestBody @Valid Invoice invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    Invoice getInvoice(@PathVariable int id);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    List<Invoice> getAllInvoices();

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.PUT)
    Invoice updateInvoice(@RequestBody @Valid Invoice invoice, @PathVariable int id);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    String deleteInvoice(@PathVariable int id);

    @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
    List<Invoice> getInvoicesByCustomer(@PathVariable int customerId);
}
