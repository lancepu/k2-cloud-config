package com.company.adminapi.controller;

import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.models.Invoice;
import com.company.adminapi.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RefreshScope
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(){
        return serviceLayer.getAllInvoices();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid Invoice invoice){
        return serviceLayer.addInvoice(invoice);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable int id) {
        return serviceLayer.getInvoice(id);
    }

    @GetMapping(value = "/customer/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return serviceLayer.getInvoicesByCustomerId(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel updateInvoice(@RequestBody @Valid Invoice invoice, @PathVariable int id) {
        if(id!= invoice.getInvoiceId()){
            throw new IllegalArgumentException("This invoice ID was not found...");
        }
        return serviceLayer.updateInvoice(invoice);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteInvoice(@PathVariable int id) {
        return serviceLayer.deleteInvoice(id);
    }
}