package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import com.cgi.dentistapp.feedback.FeedbackType;
import com.cgi.dentistapp.service.DentistVisitService;
import com.cgi.dentistapp.util.FeedbackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {
    private final MessageSource messageSource;
    private final DentistVisitService dentistVisitService;

    @Autowired
    public DentistAppController(MessageSource messageSource,
                                DentistVisitService dentistVisitService) {
        this.messageSource = messageSource;
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
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        dentistVisitService.addVisit(dentistVisitDTO);
        return "redirect:/results";
    }

    @GetMapping("visits")
    public String showVisits(Model model,
                             @ModelAttribute("searchQuery") SearchQueryDTO dto) {
        model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

    @PostMapping("visits")
    public String showSearchResults(@ModelAttribute("searchQuery") @Valid SearchQueryDTO searchQuery,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "visit-id", defaultValue = "-1") String visitId,
                                    Model model,
                                    Locale locale) {

        if (bindingResult.hasErrors()) {
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("visits.error", null, locale));
        } else if (visitId.equals("-1")) {
            model.addAttribute("searchResult", dentistVisitService.getSearchResults(searchQuery));
        } else {
            return "visit_details";
        }

        model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

}
