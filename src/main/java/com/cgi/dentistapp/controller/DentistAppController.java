package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.DetailedViewDTO;
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
                                   BindingResult bindingResult,
                                   Model model,
                                   Locale locale) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        dentistVisitService.addVisit(dentistVisitDTO);
        FeedbackUtil.setFeedback(model, FeedbackType.SUCCESS, messageSource.getMessage("form.submit.success", null, locale));
        return "form";
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
            model.addAttribute("detailedView", dentistVisitService.getVisitByID(Long.parseLong(visitId)));
            return "visit_details";
        }

        model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

    @PostMapping("visit_details")
    public String showVisitDetails(@ModelAttribute("detailedView") @Valid DetailedViewDTO detailedViewDTO,
                                   BindingResult bindingResult,
                                   @RequestParam(value = "change", defaultValue = "-1") String changeVisit,
                                   @RequestParam(value = "delete", defaultValue = "-1") String deleteVisit,
                                   Model model,
                                   Locale locale) {
        if (bindingResult.hasErrors()) {
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("visits.error", null, locale));
            return "visit_details";
        }
        if (!changeVisit.equals("-1")) {
            model.addAttribute("detailedView", dentistVisitService.setVisitByID(
                    new DetailedViewDTO(
                            Long.parseLong(changeVisit),
                            detailedViewDTO.getDentistName(),
                            detailedViewDTO.getPhysicianName(),
                            detailedViewDTO.getVisitBeginningDateTime(),
                            detailedViewDTO.getVisitEndDateTime()
                    )));
            FeedbackUtil.setFeedback(model, FeedbackType.SUCCESS, messageSource.getMessage("visit_details.update.success", null, locale));
            return "visit_details";
        }

        if (!deleteVisit.equals("-1")){
            dentistVisitService.deleteByID(Long.parseLong(deleteVisit));
            model.addAttribute("dentistVisitDTOs", dentistVisitService.listVisits());
            model.addAttribute("searchQuery", new SearchQueryDTO());
            return "visits";
        }
        return "visit_details";
    }
}
