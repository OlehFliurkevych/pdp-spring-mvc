package com.fliurkevych.pdp.pdpspringcore.service;

import com.fliurkevych.pdp.pdpspringcore.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Oleh Fliurkevych
 */
@Slf4j
@Service
public class TicketService {
  
  @Autowired
  private TicketRepository ticketRepository;


}