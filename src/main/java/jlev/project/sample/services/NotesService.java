package jlev.project.sample.services;

import jlev.project.sample.model.Notes;

import java.util.List;
import java.util.Optional;

/**
 * Service for Notes
 * Implemented depending on the profile e.g. H2 or File Storage
 */
public interface NotesService {

   Notes add(Notes note);

   List<Notes> findAll();

   Optional<Notes> find(Long id);

   Notes update(Long id, Notes note);

   void delete(Long id);


}
