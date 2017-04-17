package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.RegistrationFormDTO;
import com.cgi.dentistapp.visits.FamilyPhysician;
import com.cgi.dentistapp.dto.DetailedViewDTO;
import com.cgi.dentistapp.dto.SearchQueryDTO;
import com.cgi.dentistapp.feedback.FeedbackType;
import com.cgi.dentistapp.service.DentistVisitService;
import com.cgi.dentistapp.util.FeedbackUtil;
import lombok.RequiredArgsConstructor;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DentistAppController extends WebMvcConfigurerAdapter {
    private final MessageSource messageSource;
    private final DentistVisitService dentistVisitService;

    // This list is for populating the family physician name dropdown lists in the front-end
    private final List<FamilyPhysician> familyPhysicians = Arrays.asList(
            new FamilyPhysician("John", "Doe"),
            new FamilyPhysician("Jane", "Doe")
    );


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(RegistrationFormDTO registrationFormDTO, Model model) {
        model.addAttribute("familyPhysicians", familyPhysicians);
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid RegistrationFormDTO registrationFormDTO,
                                   BindingResult bindingResult,
                                   Model model,
                                   Locale locale) {
        model.addAttribute("familyPhysicians", familyPhysicians);

        if (bindingResult.hasErrors()) {
            return "form";
        }
        // If the entered beginning datetime is after the entered end datetime, display an error message
        if (registrationFormDTO.getVisitBeginningDateTime().isAfter(registrationFormDTO.getVisitEndDateTime())) {
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("form.submit.faulty.dates", null, locale));
            return "form";
        }
        // If there are more than zero overlapping registrations with the entered registration, display an error message
        if (dentistVisitService.getOverlapCount(registrationFormDTO) > 0) {
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("form.submit.overlap.fail", null, locale));
            return "form";
        }

        // In case of no errors, add a new registration to the database and display success message
        dentistVisitService.addVisit(registrationFormDTO);
        FeedbackUtil.setFeedback(model, FeedbackType.SUCCESS, messageSource.getMessage("form.submit.success", null, locale));
        return "form";
    }

    @GetMapping("visits")
    public String showVisits(Model model,
                             @ModelAttribute("searchQuery") SearchQueryDTO dto) {
        model.addAttribute("familyPhysicians", familyPhysicians);

        // All registrations are shown on the visits page
        model.addAttribute("displayVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

    @PostMapping("visits")
    public String showSearchResults(@ModelAttribute("searchQuery") @Valid SearchQueryDTO searchQuery,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "visit-id", defaultValue = "-1") String visitId,
                                    Model model,
                                    Locale locale) {
        model.addAttribute("familyPhysicians", familyPhysicians);

        if (bindingResult.hasErrors()) {
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("visits.error", null, locale));
        } else if (visitId.equals("-1")) {
            // If the visitId is -1, then search results are returned
            model.addAttribute("searchResult", dentistVisitService.getSearchResults(searchQuery));
        } else {
            // Otherwise, visitId marks a registration's ID and the detailed view is displayed
            model.addAttribute("detailedView", dentistVisitService.getVisitByID(Long.parseLong(visitId)));
            return "visit_details";
        }

        model.addAttribute("displayVisitDTOs", dentistVisitService.listVisits());
        return "visits";
    }

    @PostMapping("visit_details")
    public String showVisitDetails(@ModelAttribute("detailedView") @Valid DetailedViewDTO detailedViewDTO,
                                   BindingResult bindingResult,
                                   @RequestParam(value = "change", defaultValue = "-1") String changeVisit,
                                   @RequestParam(value = "delete", defaultValue = "-1") String deleteVisit,
                                   Model model,
                                   Locale locale) {
        model.addAttribute("familyPhysicians", familyPhysicians);

        if (bindingResult.hasErrors()) {
            detailedViewDTO.setId(Long.parseLong(changeVisit));
            model.addAttribute("detailedView", detailedViewDTO);
            FeedbackUtil.setFeedback(model, FeedbackType.ERROR, messageSource.getMessage("visits.error", null, locale));
            return "visit_details";
        }
        // changeVisit is not -1 if the "Muuda" button was clicked and therefore has the value of the registration's ID
        if (!changeVisit.equals("-1")) {
            model.addAttribute("detailedView", dentistVisitService.update(
                    new DetailedViewDTO(
                            Long.parseLong(changeVisit),
                            detailedViewDTO.getNid(),
                            detailedViewDTO.getDentistName(),
                            detailedViewDTO.getPhysicianName(),
                            detailedViewDTO.getVisitBeginningDateTime(),
                            detailedViewDTO.getVisitEndDateTime()
                    ))
            );
            FeedbackUtil.setFeedback(model, FeedbackType.SUCCESS, messageSource.getMessage("visit_details.update.success", null, locale));
            return "visit_details";
        }

        // deleteVisit is not -1 if the "Kustuta" button was clicked and therefore has the value of the registration's ID
        if (!deleteVisit.equals("-1")) {
            dentistVisitService.deleteByID(Long.parseLong(deleteVisit));
            model.addAttribute("displayVisitDTOs", dentistVisitService.listVisits());
            model.addAttribute("searchQuery", new SearchQueryDTO());
            return "visits";
        }
        return "visit_details";
    }
}
