package com.sirius.springenablement.ticket_booking.services;
import java.util.List;
import com.sirius.springenablement.ticket_booking.entity.Shows;
import com.sirius.springenablement.ticket_booking.repository.ShowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.sirius.springenablement.ticket_booking.dto.ShowRequestDto;
import com.sirius.springenablement.ticket_booking.dto.AvailableShowResponseDto;
import com.sirius.springenablement.ticket_booking.entity.Theatre;
@Service
public class ShowService{

    @Autowired
    public ShowsRepository showsRepository;

    /**
     * Method which saves the shows
     * @param shows
     * @return
     */

    public Shows save(Shows shows) {

        showsRepository.save(shows);
        return  shows;

    }

    /**
     * Method which fetches a shows by id
     * @param showsId
     * @return
     *
     */
    public Optional<Shows> findById(int showsId) {
        return showsRepository.findById((long) showsId);
    }

    /**
     * Method which deletes a shows
     * @param showsId
     * @return
     */

    public  String deleteById(int showsId) {
        showsRepository.deleteById((long) showsId);
        return "deleted"+ showsId;
    }

    /**
     *  Method which finds all the Shows
     * @return
     */

    public List<Shows> findAll() {
        System.out.println("jna");
        try {
            return showsRepository.findAll();
        }
        catch (Exception e){
            System.out.println("exce[tiom" + e);
        }

        return null;
    }

    public void findAvailableShows(ShowRequestDto showRequestDto) {
        List<Shows> allShows = showsRepository.findAll();
        System.out.println("allshows"+ allShows);

    }





    public List<AvailableShowResponseDto> findAvailableShows(java.time.LocalDate bookingDate, String timeSlot, String movieName) {

        List<Shows> availableShows=null;
        if (bookingDate != null && movieName != null && timeSlot != null) {
            availableShows = showsRepository.findByDateAndMovieNameAndTimeSlot(bookingDate, movieName, timeSlot);
        } else if (bookingDate != null && movieName != null) {
            availableShows = showsRepository.findByDateAndMovieName(bookingDate, movieName);
        }
        else if (bookingDate != null && timeSlot != null) {
            availableShows = showsRepository.findByDateAndTimeSlot(bookingDate, timeSlot);
        } else if (bookingDate != null) {
            availableShows = showsRepository.findByDate(bookingDate);
        }

        List<AvailableShowResponseDto> responseDtoList = new java.util.ArrayList<>();

        for (Shows show : availableShows) {
            AvailableShowResponseDto responseDto = new AvailableShowResponseDto();
            int id=show.getId();
            System.out.println("id" + id);

            int availableTicketCount = show.getAvailableCount();
           if(availableTicketCount>0)
                responseDto.setAvailableTicketCount(availableTicketCount);
           else
                responseDto.setAvailableTicketCount(0);

            responseDto.setShowName(show.getMovieName());

            responseDto.setTheatreName(show.getTheatre().getName());
            responseDto.setLocationName(show.getTheatre().getLocation().getName());



            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }



}
