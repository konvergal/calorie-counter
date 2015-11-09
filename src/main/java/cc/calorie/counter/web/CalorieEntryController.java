package cc.calorie.counter.web;

import cc.calorie.counter.configuration.CalorieAuthenticationPrincipal;
import cc.calorie.counter.service.CalorieEntryDTO;
import cc.calorie.counter.service.CalorieEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by konvergal on 28/10/15.
 */
@RestController
@RequestMapping(value = "/api/calories")
public class CalorieEntryController {

    @Autowired
    private CalorieEntryService calorieEntryService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<CalorieEntryDTO> findCalorieEntries(
            @RequestParam(required = false, value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> fromDate,
            @RequestParam(required = false, value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> toDate,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        return calorieEntryService.findCalorieEntries(fromDate, toDate, calorieAuthenticationPrincipal.getUserId());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody List<CalorieEntryDTO> addCalorieEntry(
            @RequestBody CalorieEntryDTO calorieEntryDTO,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        return calorieEntryService.addCalorieEntry(calorieEntryDTO, calorieAuthenticationPrincipal.getUserId());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCalorieEntry(
            @RequestBody CalorieEntryDTO calorieEntryDTO,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        calorieEntryService.updateCalorieEntry(calorieEntryDTO, calorieAuthenticationPrincipal.getUserId());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCalorieEntry(
            @PathVariable Long id,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        calorieEntryService.deleteCalorieEntry(id, calorieAuthenticationPrincipal.getUserId());
    }

}
