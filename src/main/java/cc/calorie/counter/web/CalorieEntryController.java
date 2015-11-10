package cc.calorie.counter.web;

import cc.calorie.counter.configuration.CalorieAuthenticationPrincipal;
import cc.calorie.counter.service.CalorieEntryDTO;
import cc.calorie.counter.service.CalorieEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            FilterDTO filterDTO,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        return calorieEntryService.findCalorieEntries(
                filterDTO.setUserId(Optional.of(calorieAuthenticationPrincipal.getUserId())));
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody List<CalorieEntryDTO> addCalorieEntry(
            FilterDTO filterDTO,
            @RequestBody CalorieEntryDTO calorieEntryDTO,
            @AuthenticationPrincipal CalorieAuthenticationPrincipal calorieAuthenticationPrincipal)
    {
        Long userId = calorieAuthenticationPrincipal.getUserId();
        calorieEntryService.addCalorieEntry(calorieEntryDTO, userId);
        return calorieEntryService.findCalorieEntries(filterDTO.setUserId(Optional.of(userId)));
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
