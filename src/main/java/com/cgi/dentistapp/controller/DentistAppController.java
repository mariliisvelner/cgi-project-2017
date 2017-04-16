package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    private final DentistVisitService dentistVisitService;

    @Autowired
    public DentistAppController(DentistVisitService dentistVisitService) {
        this.dentistVisitService = dentistVisitService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO) {
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        dentistVisitService.addVisit(dentistVisitDTO);
        return "redirect:/results";
    }

    @GetMapping("visits")
    public String showVisits(Model model){
        model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
        model.addAttribute("searchQuery", new SearchQueryDTO());
        model.addAttribute("searchResult", new ArrayList());
        return "visits";
    }

    @PostMapping("visits")
    public String showSearchResults(@ModelAttribute("searchQuery") @Valid SearchQueryDTO searchQuery, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("searchResult", new ArrayList());
        }
        else {
            model.addAttribute("searchResult", dentistVisitService.getSearchResults(searchQuery));
        }
        model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

}
