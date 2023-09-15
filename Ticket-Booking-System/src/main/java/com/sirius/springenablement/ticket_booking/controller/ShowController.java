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
@RestController
@RequestMapping("/api/shows")
public class ShowController {
    @Autowired
    public ShowService showService;

    /**
     * Get Availble Shows
     * @param showRequestDto
     * @return
     */
    @PostMapping("/availableShows")
    public ResponseEntity<String> getAvailableShowsAndTickets(@RequestBody ShowRequestDto showRequestDto) {
        try {
            java.time.LocalDate date = showRequestDto.getDate();
            String timeslot = showRequestDto.getTimeSlot();

            List<AvailableShowResponseDto> availableShowsWithTickets = showService.findAvailableShows(date, timeslot);
            System.out.println("available " + availableShowsWithTickets);

            StringBuilder responseBuilder = new StringBuilder();
            for (AvailableShowResponseDto responseDto : availableShowsWithTickets) {
                responseBuilder.append("Show: ").append(responseDto.getShowName());
                responseBuilder.append(", Available Tickets: ").append(responseDto.getAvailableTicketCount()).append("\n");
            }

            return ResponseEntity.ok(responseBuilder.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
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
