package com.example.citronix.web.rest;

import com.example.citronix.domain.Sale;
import com.example.citronix.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleRest {

    private final SaleService saleService;

    public SaleRest(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/save")
    public ResponseEntity<Sale> save(@RequestBody Sale sale) {
        Sale savedSale = saleService.save(sale);
        return ResponseEntity.ok(savedSale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.ok(sale);
    }

    @GetMapping
    public ResponseEntity<Page<Sale>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Sale> sales = saleService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(sales);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
