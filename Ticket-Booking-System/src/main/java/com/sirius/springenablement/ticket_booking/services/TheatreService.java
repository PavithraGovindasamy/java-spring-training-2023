package com.sirius.springenablement.ticket_booking.services;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sirius.springenablement.ticket_booking.repository.TheatreRepository;
import com.sirius.springenablement.ticket_booking.entity.Theatre;
@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;


    public void addTheatre(Theatre requestDto) {
        Theatre theatre=new Theatre();
       theatre.setName(requestDto.getName());
       theatreRepository.save(theatre);
    }


    public void removeTheatre(Long theatreId) throws Exception {
        if (theatreRepository.existsById(theatreId)) {
            theatreRepository.deleteById(theatreId);
        } else {
            throw new Exception("Theatre not found with ID: " + theatreId);
        }
    }
}