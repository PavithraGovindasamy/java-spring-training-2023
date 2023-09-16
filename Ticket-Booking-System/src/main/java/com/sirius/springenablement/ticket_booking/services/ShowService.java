package com.sirius.springenablement.ticket_booking.services;
import java.util.List;
import com.sirius.springenablement.ticket_booking.entity.Shows;
import com.sirius.springenablement.ticket_booking.repository.ShowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.ArrayList;
import com.sirius.springenablement.ticket_booking.entity.Tickets;
import java.time.LocalDate;
import com.sirius.springenablement.ticket_booking.dto.ShowRequestDto;
import com.sirius.springenablement.ticket_booking.dto.AvailableShowResponseDto;
import com.sirius.springenablement.ticket_booking.repository.TicketsRepository;
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
        return showsRepository.findAll();

    }

    public void findAvailableShows(ShowRequestDto showRequestDto) {
        List<Shows> allShows = showsRepository.findAll();
        System.out.println("allshows"+ allShows);

    }


    @Autowired
    private TicketsRepository ticketRepository;

    public List<AvailableShowResponseDto> findAvailableShows(LocalDate bookingDate, String timeSlot) {

        List<Shows> availableShows = showsRepository.findByDate(bookingDate);
        List<AvailableShowResponseDto> responseDtoList = new ArrayList<>();

        for (Shows show : availableShows) {
            AvailableShowResponseDto responseDto = new AvailableShowResponseDto();

            List<Tickets> showTickets = ticketRepository.findByShowId(show.getId());

            responseDto.setShowName(show.getMovieName());
            responseDto.setAvailableTicketCount(showTickets.size());

            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }




}
