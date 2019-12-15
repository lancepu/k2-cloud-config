package com.company.adminapi.service;

import com.company.adminapi.util.feign.*;
import com.company.adminapi.models.Customer;
import com.company.adminapi.models.Product;
import com.company.adminapi.models.Inventory;
import com.company.adminapi.models.Invoice;
import com.company.adminapi.models.InvoiceItem;
import com.company.adminapi.models.LevelUp;
import com.company.adminapi.viewmodel.InventoryViewModel;
import com.company.adminapi.viewmodel.InvoiceItemViewModel;
import com.company.adminapi.viewmodel.InvoiceViewModel;
import com.company.adminapi.viewmodel.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    private ServiceLayer serviceLayer;

    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;
    private InventoryClient inventoryClient;
    private CustomerClient customerClient;

    @Before
    public void setUp() {
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
        setUpCustomerClientMock();
        setUpProductClientMock();
        setUpInventoryClientMock();

        serviceLayer = new ServiceLayer(customerClient, productClient, inventoryClient, levelUpClient, invoiceClient);
    }


    @Test
    public void testLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setMemberDate(LocalDate.of(2020,12,12));
        levelUp.setPoints(1000);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setPhone("123-456-7890");
        customer.setEmail("test@test.com");

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(5);
        levelUpViewModel.setCustomer(customer);
        levelUpViewModel.setMemberDate(LocalDate.of(2020,12,12));
        levelUpViewModel.setPoints(1000);

        List<LevelUpViewModel> levelUpViewModelList = new ArrayList<>();
        levelUpViewModelList.add(levelUpViewModel);

        LevelUpViewModel serviceLevelUpVM = serviceLayer.addLevelUp(levelUp);
        assertEquals(levelUpViewModel, serviceLevelUpVM);

        serviceLevelUpVM = serviceLayer.getLevelUp(5);
        assertEquals(levelUpViewModel, serviceLevelUpVM);

        serviceLevelUpVM = serviceLayer.getLevelUpByCustomerId(1);
        assertEquals(levelUpViewModel, serviceLevelUpVM);

        List<LevelUpViewModel> fromServiceList = serviceLayer.getAllLevelUps();
        assertEquals(levelUpViewModelList, fromServiceList);

        levelUpViewModel.setPoints(1050);
        levelUp.setPoints(1050);
        levelUp.setLevelUpId(5);
        serviceLevelUpVM = serviceLayer.updateLevelUp(levelUp);
        assertEquals(levelUpViewModel, serviceLevelUpVM);

    }

    @Test
    public void testInventory() {

        Inventory inventory = new Inventory();
        inventory.setProductId(11);
        inventory.setQuantity(2);

        Product product = new Product();
        product.setProductId(11);
        product.setProductName("Sony Series X");
        product.setProductDescription("Console");
        product.setListPrice(new BigDecimal(250.00));
        product.setUnitCost(new BigDecimal(200.00));

        InventoryViewModel inventoryVm = new InventoryViewModel();
        inventoryVm.setInventoryId(21);
        inventoryVm.setQuantity(2);
        inventoryVm.setProduct(product);

        List<InventoryViewModel> expectedList = new ArrayList<>();
        expectedList.add(inventoryVm);

        InventoryViewModel fromService = serviceLayer.addInventory(inventory);
        assertEquals(inventoryVm, fromService);

        fromService = serviceLayer.getInventory(21);
        assertEquals(inventoryVm, fromService);

        List<InventoryViewModel> fromServiceList = serviceLayer.getAllInventories();
        assertEquals(expectedList, fromServiceList);

        inventoryVm.setQuantity(1);
        inventory.setQuantity(1);
        inventory.setInventoryId(21);
        fromService = serviceLayer.updateInventory(inventory);
        assertEquals(inventoryVm, fromService);

    }

    @Test
    public void findInvoiceAndFindAll() {

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setPhone("123-456-7890");
        customer.setEmail("test@test.com");

        Product product = new Product();
        product.setProductId(11);
        product.setProductName("Sony Series X");
        product.setProductDescription("Console");
        product.setListPrice(new BigDecimal(250.00));
        product.setUnitCost(new BigDecimal(200.00));

        InventoryViewModel inventoryVm = new InventoryViewModel();
        inventoryVm.setInventoryId(21);
        inventoryVm.setQuantity(2);
        inventoryVm.setProduct(product);

        InvoiceItemViewModel invoiceItemVm = new InvoiceItemViewModel();
        invoiceItemVm.setInvoiceId(31);
        invoiceItemVm.setInvoiceItemId(41);
        invoiceItemVm.setInventory(inventoryVm);
        invoiceItemVm.setListPrice(new BigDecimal(250.00));
        invoiceItemVm.setQuantity(1);
        List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItemVm);

        InvoiceViewModel invoiceVm = new InvoiceViewModel();
        invoiceVm.setInvoiceId(31);
        invoiceVm.setPurchaseDate(LocalDate.of(2019,9,4));
        invoiceVm.setMemberPoints(1050);
        invoiceVm.setCustomer(customer);
        invoiceVm.setInvoiceItems(invoiceItems);

        invoiceVm.setMemberPoints(1000);
        InvoiceViewModel fromFind = serviceLayer.getInvoice(31);
        assertEquals(invoiceVm, fromFind);

        List<InvoiceViewModel> allInvoices = serviceLayer.getAllInvoices();
        assertEquals(allInvoices.size(),1);
        assertEquals(allInvoices.get(0), invoiceVm);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveInvoice() {

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInventoryId(21);
        invoiceItem.setListPrice(new BigDecimal(250.00));
        invoiceItem.setQuantity(1);
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,9,4));
        invoice.setInvoiceItems(invoiceItems);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setPhone("123-456-7890");
        customer.setEmail("test@test.com");

        Product product = new Product();
        product.setProductId(11);
        product.setProductName("Sony Series X");
        product.setProductDescription("Console");
        product.setListPrice(new BigDecimal(250.00));
        product.setUnitCost(new BigDecimal(200.00));

        InventoryViewModel inventoryVm = new InventoryViewModel();
        inventoryVm.setInventoryId(21);
        inventoryVm.setQuantity(1);
        inventoryVm.setProduct(product);

        InvoiceItemViewModel invoiceItemVm = new InvoiceItemViewModel();
        invoiceItemVm.setInvoiceId(31);
        invoiceItemVm.setInvoiceItemId(41);
        invoiceItemVm.setInventory(inventoryVm);
        invoiceItemVm.setListPrice(new BigDecimal(250.00));
        invoiceItemVm.setQuantity(1);
        List<InvoiceItemViewModel> invoiceItemVms = new ArrayList<>();
        invoiceItemVms.add(invoiceItemVm);

        InvoiceViewModel invoiceVm = new InvoiceViewModel();
        invoiceVm.setInvoiceId(31);
        invoiceVm.setPurchaseDate(LocalDate.of(2019,9,4));
        invoiceVm.setMemberPoints(1050);
        invoiceVm.setCustomer(customer);
        invoiceVm.setInvoiceItems(invoiceItemVms);

        InvoiceViewModel fromSave = serviceLayer.addInvoice(invoice);
        assertEquals(invoiceVm, fromSave);

        invoiceItem.setQuantity(100);
        serviceLayer.addInvoice(invoice);
    }

    @Test
    public void updateAndRemoveInvoice(){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(31);
        invoiceItem.setInvoiceItemId(41);
        invoiceItem.setInventoryId(21);
        invoiceItem.setListPrice(new BigDecimal(250.00));
        invoiceItem.setQuantity(1);
        List<InvoiceItem> invoiceItemsIn = new ArrayList<>();
        invoiceItemsIn.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(31);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));
        invoice.setInvoiceItems(invoiceItemsIn);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setPhone("123-456-7890");
        customer.setEmail("test@test.com");

        Product product = new Product();
        product.setProductId(11);
        product.setProductName("Sony Series X");
        product.setProductDescription("Console");
        product.setListPrice(new BigDecimal(250.00));
        product.setUnitCost(new BigDecimal(200.00));

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(21);
        inventory.setQuantity(2);
        inventory.setProduct(product);

        InvoiceItemViewModel invoiceItemVm = new InvoiceItemViewModel();
        invoiceItemVm.setInvoiceId(31);
        invoiceItemVm.setInvoiceItemId(41);
        invoiceItemVm.setInventory(inventory);
        invoiceItemVm.setListPrice(new BigDecimal(250.00));
        invoiceItemVm.setQuantity(1);
        List<InvoiceItemViewModel> invoiceItemVms = new ArrayList<>();
        invoiceItemVms.add(invoiceItemVm);

        InvoiceViewModel fromDbVM = new InvoiceViewModel();
        fromDbVM.setInvoiceId(31);
        fromDbVM.setPurchaseDate(LocalDate.of(2020,12,12));
        fromDbVM.setMemberPoints(1000);
        fromDbVM.setCustomer(customer);
        fromDbVM.setInvoiceItems(invoiceItemVms);

        InvoiceViewModel fromUpdate = serviceLayer.updateInvoice(invoice);
        assertEquals(fromDbVM, fromUpdate);
    }

    private void setUpCustomerClientMock(){
        customerClient = mock(CustomerClient.class);

        Customer fromDb = new Customer();
        fromDb.setCustomerId(1);
        fromDb.setFirstName("Chris");
        fromDb.setLastName("Zevallos");
        fromDb.setStreet("101 Times Square");
        fromDb.setCity("New York");
        fromDb.setZip("10001");
        fromDb.setPhone("123-456-7890");
        fromDb.setEmail("test@test.com");

        doReturn(fromDb).when(customerClient).getCustomer(1);
    }

    private void setUpProductClientMock(){
        productClient = mock(ProductClient.class);

        Product fromDb = new Product();
        fromDb.setProductId(11);
        fromDb.setProductName("Sony Series X");
        fromDb.setProductDescription("Console");
        fromDb.setListPrice(new BigDecimal(250.00));
        fromDb.setUnitCost(new BigDecimal(200.00));

        doReturn(fromDb).when(productClient).getProduct(11);
    }

    private void setUpInventoryClientMock(){
        inventoryClient = mock(InventoryClient.class);

        Inventory toDb = new Inventory();
        toDb.setProductId(11);
        toDb.setQuantity(2);

        Inventory fromDb = new Inventory();
        fromDb.setInventoryId(21);
        fromDb.setProductId(11);
        fromDb.setQuantity(2);

        Inventory updated = new Inventory();
        updated.setInventoryId(21);
        updated.setProductId(11);
        updated.setQuantity(1);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(fromDb);

        doReturn(fromDb).when(inventoryClient).createInventory(toDb);
        doReturn(fromDb).when(inventoryClient).getInventory(21);
        doReturn(inventoryList).when(inventoryClient).getAllInventories();
        doReturn(updated).when(inventoryClient).updateInventory(updated,updated.getInventoryId());
    }

    private void setUpInvoiceClientMock(){
        invoiceClient = mock(InvoiceClient.class);

        InvoiceItem invoiceItemIn = new InvoiceItem();
        invoiceItemIn.setInventoryId(21);
        invoiceItemIn.setListPrice(new BigDecimal(250.00));
        invoiceItemIn.setQuantity(1);
        List<InvoiceItem> invoiceItemsIn = new ArrayList<>();
        invoiceItemsIn.add(invoiceItemIn);

        Invoice toDb = new Invoice();
        toDb.setCustomerId(1);
        toDb.setPurchaseDate(LocalDate.of(2019,9,4));
        toDb.setInvoiceItems(invoiceItemsIn);


        InvoiceItem invoiceItemOut = new InvoiceItem();
        invoiceItemOut.setInvoiceItemId(41);
        invoiceItemOut.setInvoiceId(31);
        invoiceItemOut.setInventoryId(21);
        invoiceItemOut.setListPrice(new BigDecimal(250.00));
        invoiceItemOut.setQuantity(1);
        List<InvoiceItem> invoiceItemsOut = new ArrayList<>();
        invoiceItemsOut.add(invoiceItemOut);

        Invoice fromDb = new Invoice();
        fromDb.setInvoiceId(31);
        fromDb.setCustomerId(1);
        fromDb.setPurchaseDate(LocalDate.of(2019,9,4));
        fromDb.setInvoiceItems(invoiceItemsOut);

        List<Invoice> fromDbList = new ArrayList<>();
        fromDbList.add(fromDb);

        Invoice updated = new Invoice();
        updated.setInvoiceId(31);
        updated.setCustomerId(1);
        updated.setPurchaseDate(LocalDate.of(2020,12,12));
        updated.setInvoiceItems(invoiceItemsOut);

        doReturn(fromDb).when(invoiceClient).createInvoice(toDb);
        doReturn(fromDb).when(invoiceClient).getInvoice(31);
        doReturn(fromDbList).when(invoiceClient).getInvoicesByCustomer(1);
        doReturn(fromDbList).when(invoiceClient).getAllInvoices();
        doReturn(updated).when(invoiceClient).updateInvoice(updated,updated.getInvoiceId());
    }

    private void setUpLevelUpClientMock(){
        levelUpClient = mock(LevelUpClient.class);

        LevelUp toDb = new LevelUp();
        toDb.setCustomerId(1);
        toDb.setMemberDate(LocalDate.of(2020,12,12));
        toDb.setPoints(1000);

        LevelUp fromDb = new LevelUp();
        fromDb.setLevelUpId(5);
        fromDb.setCustomerId(1);
        fromDb.setMemberDate(LocalDate.of(2020,12,12));
        fromDb.setPoints(1000);

        List<LevelUp> levelUps = new ArrayList<>();
        levelUps.add(fromDb);

        LevelUp updated = new LevelUp();
        updated.setLevelUpId(5);
        updated.setCustomerId(1);
        updated.setMemberDate(LocalDate.of(2020,12,12));
        updated.setPoints(1050);

        doReturn(fromDb).when(levelUpClient).createLevelUp(toDb);
        doReturn(fromDb).when(levelUpClient).getLevelUp(5);
        doReturn(fromDb).when(levelUpClient).getLevelUpByCustomerId(1);
        doReturn(levelUps).when(levelUpClient).getAllLevelUps();
        doReturn(updated).when(levelUpClient).updateLevelUp(updated,updated.getLevelUpId());

    }
}