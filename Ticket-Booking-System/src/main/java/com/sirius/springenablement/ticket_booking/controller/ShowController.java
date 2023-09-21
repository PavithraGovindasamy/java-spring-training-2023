package com.sirius.springenablement.ticket_booking.controller;
import com.sirius.springenablement.ticket_booking.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import  com.sirius.springenablement.ticket_booking.entity.*;
import  java.util.*;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.web.bind.annotation.*;
import  com.sirius.springenablement.ticket_booking.dto.ShowRequestDto;
import org.springframework.http.*;
import com.sirius.springenablement.ticket_booking.dto.AvailableShowResponseDto;
import com.sirius.springenablement.ticket_booking.repository.LocationsRepository;
@RestController
@RequestMapping("/shows")
public class ShowController {
    @Autowired
    public ShowService showService;

    @Autowired
    private LocationsRepository locationsRepository;

    /**
     * Get Availble Shows
     * @param showRequestDto
     * @return
     */
    @PostMapping("/availableShows")
    public ResponseEntity<List<AvailableShowResponseDto>> getAvailableShowsAndTickets(@RequestBody ShowRequestDto showRequestDto) {
        try {
            java.time.LocalDate date = showRequestDto.getDate();
            String timeslot = showRequestDto.getTimeSlot();

            String movieName=showRequestDto.getMovieName();
            List<AvailableShowResponseDto> availableShowsWithTickets = showService.findAvailableShows(date, timeslot,movieName);
            System.out.println("available " + availableShowsWithTickets);
            return ResponseEntity.ok(availableShowsWithTickets);
        } catch (Exception e) {
            AvailableShowResponseDto responseDto = new AvailableShowResponseDto();
            System.out.println("exeception"+e);
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed ");

            List<AvailableShowResponseDto> errorList = new ArrayList<>();
            errorList.add(responseDto);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorList);
        }
        }




    @GetMapping("/allShows")
    public List<Shows> getAllShows(){

        return showService.findAll();
    }

    /**
     * Method which fetches a shows
     * @param ShowId
     * @return
     */

    @GetMapping("/{ShowId}")
    public String getShowByID(@PathVariable int ShowId) {
        Optional<Shows> Show = showService.findById(ShowId);
        if (Show.isPresent()) {
            Shows shows = Show.get();

            String response = "Show: " + shows.getId() + " " + shows.getMovieName()+ "\n";

            return response;
        } else {
            return "Show not found.";
        }
    }


    /**
     * Method that implements to Add a Shows
     * @param shows
     * @return
     */

    @PostMapping
    public String addShows(@RequestBody Shows shows){
        shows.setActive(true);
        showService.save(shows);
        return "Show added";
    }


    /**
     * Method which updates details of Show
     * @param ShowId
     * @param show
     * @return
     */
    @PutMapping("/{ShowId}")
    public String updateShows(@PathVariable int ShowId, @RequestBody Shows show){
        Optional<Shows> shows = showService.findById(ShowId);
        if(shows.isPresent()) {
            show.setId(ShowId);
            Shows updateShows = showService.save(show);
            return "updated" + ShowId;
        }
        else
            return "shows not found";
    }

    /**
     * Method which deletes a shows
     * @param showsId
     * @return
     */
    @DeleteMapping("/{showsId}")
    public String deleteShows(@PathVariable int showsId){
        Optional<Shows> deleteShows=showService.findById(showsId);
        if(deleteShows.isPresent()){
            showService.deleteById(showsId);
        }
        return  "deleted Shows with ID" + showsId;
    }



}
