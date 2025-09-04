package org.exp.incaskbot.repository;

import org.exp.incaskbot.model.entity.IncognitoUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncognitoUriRepository extends JpaRepository<IncognitoUri, Long> {
}
