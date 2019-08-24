package com.microservice.weighttrackerapp.tracker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
//@SessionAttributes("name")
public class MeasureController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Autowired
    private MeasureRepository repository;

    // LIST OF MEASURES
    @GetMapping("/get-all-measures")
    public String getAllMeasures(ModelMap model) {
        List<Measure> measures = repository.findByName(getLoggedInUserName());
        Collections.sort(measures);
        logger.info("measure list: {}", measures.toString());

        model.put("measures", measures);
        return "measures-list";// jsp
    }

    // ADD MEASURE
    @GetMapping("/add-measure")
    public String showAddMeasureForm(ModelMap model) {
        model.addAttribute("measure",
                new Measure(getLoggedInUserName(), 0.0f, "", new Date())); //mapped to  CommandName in add-or-update-measure.jsp
        return "add-or-update-measure"; // jsp
    }

    @PostMapping("/add-measure")
    public String addMeasure(ModelMap model, @Valid Measure measure, BindingResult result) { //@RequestParam float weight
        if (result.hasErrors()) { // if errors user will be redirected to same page
            return "add-or-update-measure";
        }
        repository.save(new Measure(getLoggedInUserName(), measure.getWeight(), measure.getNotes(), measure.getDate()));
        return "redirect:/get-all-measures"; // jsp
    }


    // UPDATE MEASURE
    @GetMapping("/update-measure")
    public String showUpdateMeasureByIdPage(@RequestParam long id, ModelMap model) {
        Measure measure = repository.findById(id).get();
        model.put("measure", measure);
        return "add-or-update-measure"; // jsp
    }

    @PostMapping("/update-measure")
    public String updatedMeasureById(ModelMap model, @Valid Measure measure, BindingResult result) {//@RequestParam long id,
        if (result.hasErrors()) { // if errors user will be redirected to same page
            return "add-or-update-measure";
        }
        //repository.deleteById(id); // так работает
        logger.info("Measure bean: {}", measure.toString());
        measure.setName(getLoggedInUserName());
        repository.save(measure);
        return "redirect:/get-all-measures"; // jsp
    }


    //DELETE MEASURE
    @GetMapping("/delete-measure")
    public String deleteMeasureById(@RequestParam long id) {
        repository.deleteById(id);
        return "redirect:/get-all-measures"; // jsp
    }

    @GetMapping("/show-chart")
    public String showChart(ModelMap model) {
        List<Measure> measures = repository.findByName(getLoggedInUserName());

        if(measures.size()==0){
            return "blankChartPage";
        }
        Collections.sort(measures);
        Collections.reverse(measures);
        StringBuffer sbWeights = new StringBuffer();
        StringBuffer sbLabels = new StringBuffer();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < measures.size(); i++) {
            sbWeights.append(measures.get(i).getWeight()).append(",");
            String date = dateFormat.format(measures.get(i).getDate());
            sbLabels.append("'").append(date).append("'").append(",");
        }
        String weights = sbWeights.toString().substring(0, sbWeights.toString().length() - 1);
        String labels = sbLabels.toString().substring(0, sbLabels.toString().length() - 1);
        model.put("weights", weights); // Weights: [111.0, 222.0, 333.0]
        model.put("labels", labels);//"'one', 'two', 'three','four','five','six'"
        return "chartPage";
    }

    private String getLoggedInUserName() {
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }


}
