package com.fliurkevych.pdp.pdpspringcore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fliurkevych.pdp.pdpspringcore.converter.TicketConverter;
import com.fliurkevych.pdp.pdpspringcore.model.Ticket;
import com.fliurkevych.pdp.pdpspringcore.repository.TicketRepository;
import com.fliurkevych.pdp.pdpspringcore.util.TicketTestUtils;
import com.fliurkevych.pdp.pdpspringcore.xml.XmlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Oleh Fliurkevych
 */
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

  @Mock
  private TicketRepository ticketRepository;
  @Mock
  private XmlService xmlService;
  @InjectMocks
  private TicketService ticketService;

  @Test
  public void preloadTicketsTest() {
    var ticketsXml = TicketTestUtils.buildTicketsXml(3);
    var tickets = TicketConverter.toTickets(ticketsXml);
    var t1 = tickets.get(0);
    var t2 = tickets.get(1);
    var t3 = tickets.get(2);
    t1.setId((long) 0);
    t2.setId((long) 1);
    t3.setId((long) 2);

    Mockito.when(xmlService.unmarshal(anyString(), any()))
      .thenReturn(ticketsXml);
    Mockito.when(ticketRepository.save(t1)).thenReturn(t1);
    Mockito.when(ticketRepository.save(t2)).thenReturn(t2);
    Mockito.when(ticketRepository.save(t3)).thenReturn(t3);

    var success = ticketService.preloadTickets();

    Mockito.verify(xmlService).unmarshal(anyString(), any());
    Mockito.verify(ticketRepository, Mockito.times(3)).save(any(Ticket.class));

    Assertions.assertTrue(success);
  }

}