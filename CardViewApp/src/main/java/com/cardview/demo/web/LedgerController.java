package com.cardview.demo.web;

import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.outputModels.LedgerOutput;
import com.cardview.demo.service.LedgerService;
import com.cardview.demo.service.PFAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @GetMapping("/getall")
    public List<LedgerOutput> getLedger() {
        try {
        return  ledgerService.getLedger();
        } catch (Exception ex) {
            return null;
        }

    }

}
