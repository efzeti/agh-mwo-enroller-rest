package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("participantService")
public class ParticipantService {

//	DatabaseConnector connector;
	Session session;

	public ParticipantService() {
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Participant> getAll() {
		return session.createCriteria(Participant.class).list();
	}

    public Participant findByLogin(String login) {

		return (Participant) DatabaseConnector.getInstance().getSession().get(Participant.class, login);


//		Collection<Participant> participants = getAll();
//
//		Optional<Participant> participant = participants.stream().filter(p -> p.getLogin().equals(login)).findAny();
//
//		if (participant.isPresent()){
//			return participant.get();
//		} else {
//			throw new IllegalStateException();
//		}
    }

	public void addParticipant(Participant participant) {

		Transaction transaction = this.session.beginTransaction();
		session.save(participant);
		transaction.commit();

	}

	public void deleteParticipant(Participant participant) { ;

		Transaction transaction = this.session.beginTransaction();
		session.delete(participant);
		transaction.commit();
	}

//	public void putParticipant(String login, String password) { ;
//
//		Transaction transaction = this.session.beginTransaction();
//
//		Participant participant = findByLogin(login);
//		participant.setPassword(password);
//
//		session.save(participant);
//		transaction.commit();
//	}

	public void putParticipant(Participant participant) {

		Transaction transaction = this.session.beginTransaction();
		session.merge(participant);
		transaction.commit();
	}
}
