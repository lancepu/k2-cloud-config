package com.company.adminapi.service;


import com.company.adminapi.models.*;
import com.company.adminapi.util.feign.*;
import com.company.adminapi.viewmodel.InventoryViewModel;
import com.company.adminapi.viewmodel.InvoiceItemViewModel;
import com.company.adminapi.viewmodel.InvoiceViewModel;
import com.company.adminapi.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private CustomerClient customerClient;
    private ProductClient productClient;
    private InventoryClient inventoryClient;
    private LevelUpClient levelUpClient;
    private InvoiceClient invoiceClient;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, ProductClient productClient, InventoryClient inventoryClient, LevelUpClient levelUpClient, InvoiceClient invoiceClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
        this.levelUpClient = levelUpClient;
        this.invoiceClient = invoiceClient;
    }

//Customer
    public Customer addCustomer(Customer customer){

        Customer createdCustomer = customerClient.createCustomer(customer);

        LevelUp newAccount = new LevelUp();
        newAccount.setCustomerId(createdCustomer.getCustomerId());
        newAccount.setMemberDate(LocalDate.now());
        newAccount.setPoints(0);
        addLevelUp(newAccount);

        return createdCustomer;
    }

    public Customer getCustomerById(int customerId){
        return customerClient.getCustomer(customerId);
    }

    public List<Customer> getAllCustomers(){
        return customerClient.getAllCustomers();
    }

    public Customer updateCustomer(Customer customer){
        return customerClient.updateCustomer(customer, customer.getCustomerId());
    }

    public String deleteCustomer(int customerId){
        return customerClient.deleteCustomer(customerId);
    }

    //Product
    public Product addProduct(Product product){
        return productClient.createProduct(product);
    }

    public Product getProductById(int productId){
        return productClient.getProduct(productId);
    }

    public List<Product> getAllProducts(){
        return productClient.getAllProducts();
    }

    public Product updateProduct(Product product){
        return productClient.updateProduct(product, product.getProductId());
    }

    public String deleteProduct(int productId){
        return productClient.deleteProduct(productId);
    }

    //LevelUp
    public LevelUpViewModel addLevelUp(LevelUp levelUp){
        try{
            getCustomerById(levelUp.getCustomerId());
        } catch (Exception e){
            throw new IllegalArgumentException("Customer with that ID was not found...");
        }
        return buildLevelUpViewModel(levelUpClient.createLevelUp(levelUp));
    }

    public LevelUpViewModel getLevelUp(int levelUpId){
        return buildLevelUpViewModel(levelUpClient.getLevelUp(levelUpId));
    }

    public LevelUpViewModel getLevelUpByCustomerId(int customerId){
        return buildLevelUpViewModel(levelUpClient.getLevelUpByCustomerId(customerId));
    }

    public List<LevelUpViewModel> getAllLevelUps(){
        List<LevelUp> fromLevelUpService = levelUpClient.getAllLevelUps();
        List<LevelUpViewModel> levelUpViewModels = new ArrayList<>();
        fromLevelUpService.forEach(levelUp -> levelUpViewModels.add(buildLevelUpViewModel(levelUp)));
        return levelUpViewModels;
    }

    public LevelUpViewModel updateLevelUp(LevelUp levelUp){
        try{
            getCustomerById(levelUp.getCustomerId());
        } catch (Exception e){
            throw new IllegalArgumentException("Customer with that ID was not found...");
        }
        return buildLevelUpViewModel(levelUpClient.updateLevelUp(levelUp, levelUp.getLevelUpId()));
    }

    public String deleteLevelUp(int levelUpId){
        return levelUpClient.deleteLevelUp(levelUpId);
    }

    //Inventory
    public InventoryViewModel addInventory(Inventory inventory){
        try{
            getProductById(inventory.getProductId());
        } catch (Exception e){
            throw new IllegalArgumentException("Product with that ID was not found...");
        }
        return buildInventoryViewModel(inventoryClient.createInventory(inventory));
    }

    public InventoryViewModel getInventory(int inventoryId){
        return buildInventoryViewModel(inventoryClient.getInventory(inventoryId));
    }

    public List<InventoryViewModel> getAllInventories(){
        List<Inventory> fromInventoryService = inventoryClient.getAllInventories();
        List<InventoryViewModel> inventoryViewModels = new ArrayList<>();
        fromInventoryService.forEach(inventory -> inventoryViewModels.add(buildInventoryViewModel(inventory)));
        return inventoryViewModels;
    }

    public InventoryViewModel updateInventory(Inventory inventory){
        try{
            getProductById(inventory.getProductId());
        } catch (Exception e){
            throw new IllegalArgumentException("Product with that ID was not found...");
        }
        return buildInventoryViewModel(inventoryClient.updateInventory(inventory, inventory.getInventoryId()));
    }

    public String deleteInventory(int inventoryId){
        return inventoryClient.deleteInventory(inventoryId);
    }

    //Invoice
    public InvoiceViewModel addInvoice(Invoice invoice){

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();
        int points;
        List<BigDecimal> totalPrice = new ArrayList<>();

        invoiceViewModel.setCustomer(getCustomerById(invoice.getCustomerId()));

        invoice.getInvoiceItems().forEach(invoiceItem -> {
            InventoryViewModel inventory = getInventory(invoiceItem.getInventoryId());

            if(invoiceItem.getQuantity()>inventory.getQuantity()){
                throw new IllegalArgumentException("This Inventory ID only has "+inventory.getQuantity()+" in stock");
            }

            invoiceItem.setListPrice(inventory.getProduct().getListPrice());
            BigDecimal itemPrice = BigDecimal.valueOf( invoiceItem.getQuantity() ).multiply( inventory.getProduct().getListPrice() ).setScale(2, RoundingMode.HALF_UP);
            totalPrice.add(itemPrice);
        });

        invoice = invoiceClient.createInvoice(invoice);

        int result = totalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
        points = (result/50)*10;
        LevelUpViewModel currentPoints = getLevelUpByCustomerId(invoiceViewModel.getCustomer().getCustomerId());
        currentPoints.setPoints(currentPoints.getPoints()+points);
        currentPoints = updateLevelUp(buildLevelUpToVm(currentPoints));

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setMemberPoints(currentPoints.getPoints());
        invoice.getInvoiceItems().forEach(invoiceItem -> {

        InventoryViewModel inventory = getInventory(invoiceItem.getInventoryId());

        inventory.setQuantity(inventory.getQuantity()-invoiceItem.getQuantity());
        inventory = updateInventory(buildInventoryToVm(inventory));

        InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
        invoiceItemViewModel.setInvoiceId(invoiceItem.getInvoiceId());
        invoiceItemViewModel.setInvoiceItemId(invoiceItem.getInvoiceItemId());
        invoiceItemViewModel.setListPrice(invoiceItem.getListPrice());
        invoiceItemViewModel.setQuantity(invoiceItem.getQuantity());
        invoiceItemViewModel.setInventory(inventory);
        invoiceItems.add(invoiceItemViewModel); });
        invoiceViewModel.setInvoiceItems(invoiceItems);

        return invoiceViewModel;
    }

    public InvoiceViewModel getInvoice(int invoiceId){
        return buildInvoiceViewModel(invoiceClient.getInvoice(invoiceId));
    }

    public List<InvoiceViewModel> getAllInvoices(){
        List<Invoice> fromInvoiceService = invoiceClient.getAllInvoices();
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        fromInvoiceService.forEach(invoice -> invoiceViewModels.add(buildInvoiceViewModel(invoice)));
        return invoiceViewModels;
    }

    @Transactional
    public List<InvoiceViewModel> getInvoicesByCustomerId(int customerId){
        List<Invoice> fromInvoiceService = invoiceClient.getInvoicesByCustomer(customerId);
        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        fromInvoiceService.forEach(invoice -> invoiceViewModels.add(buildInvoiceViewModel(invoice)));
        return invoiceViewModels;
    }

    public InvoiceViewModel updateInvoice(Invoice invoice){
        try{
            getCustomerById(invoice.getCustomerId());
        } catch (Exception e){
            throw new IllegalArgumentException("Customer with that ID was not found...");
        }

        invoice.getInvoiceItems().forEach(invoiceItem -> {
            InventoryViewModel inventory = getInventory(invoiceItem.getInventoryId());

            if(invoiceItem.getQuantity()>inventory.getQuantity()){
                throw new IllegalArgumentException("This Inventory ID only has "+inventory.getQuantity()+" in stock");
            }
        });
        return buildInvoiceViewModel(invoiceClient.updateInvoice(invoice, invoice.getInvoiceId()));
    }

    public String deleteInvoice(int invoiceId){
        return invoiceClient.deleteInvoice(invoiceId);
    }


    private InventoryViewModel buildInventoryViewModel(Inventory inventory){
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(inventory.getInventoryId());
        inventoryViewModel.setQuantity(inventory.getQuantity());
        inventoryViewModel.setProduct(getProductById(inventory.getProductId()));
        return inventoryViewModel;
    }

    private LevelUpViewModel buildLevelUpViewModel(LevelUp levelUp){
        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());
        levelUpViewModel.setMemberDate(levelUp.getMemberDate());
        levelUpViewModel.setPoints(levelUp.getPoints());
        levelUpViewModel.setCustomer(getCustomerById(levelUp.getCustomerId()));
        return levelUpViewModel;
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice){
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        List<InvoiceItemViewModel> invoiceItemsList = new ArrayList<>();

        invoice.getInvoiceItems().forEach(invoiceItem -> {
            InvoiceItemViewModel invoiceItemViewModel = new InvoiceItemViewModel();
            invoiceItemViewModel.setInvoiceId(invoiceItem.getInvoiceId());
            invoiceItemViewModel.setInvoiceItemId(invoiceItem.getInvoiceItemId());
            invoiceItemViewModel.setQuantity(invoiceItem.getQuantity());
            invoiceItemViewModel.setListPrice(invoiceItem.getListPrice());
            invoiceItemViewModel.setInventory(getInventory(invoiceItem.getInventoryId()));
            invoiceItemsList.add(invoiceItemViewModel);
        });
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setCustomer(getCustomerById(invoice.getCustomerId()));
        invoiceViewModel.setMemberPoints(getLevelUpByCustomerId(invoice.getCustomerId()).getPoints());
        invoiceViewModel.setInvoiceItems(invoiceItemsList);

        return invoiceViewModel;
    }

    private LevelUp buildLevelUpToVm(LevelUpViewModel levelUpViewModel){
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(levelUpViewModel.getLevelUpId());
        levelUp.setCustomerId(levelUpViewModel.getCustomer().getCustomerId());
        levelUp.setMemberDate(levelUpViewModel.getMemberDate());
        levelUp.setPoints(levelUpViewModel.getPoints());
        return levelUp;
    }

    private Inventory buildInventoryToVm(InventoryViewModel inventoryViewModel){
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryViewModel.getInventoryId());
        inventory.setProductId(inventoryViewModel.getProduct().getProductId());
        inventory.setQuantity(inventoryViewModel.getQuantity());
        return inventory;
    }
}
