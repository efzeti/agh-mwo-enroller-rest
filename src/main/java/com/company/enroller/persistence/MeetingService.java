package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {

//	DatabaseConnector connector;
	Session session;

	public MeetingService() {
//		connector = DatabaseConnector.getInstance();
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = session.createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {

		return (Meeting) DatabaseConnector.getInstance().getSession().get(Meeting.class, id);

	}

	public void addMeeting(Meeting meeting) {

		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();

	}

	public void deleteMeeting(Meeting meeting) {

		Transaction transaction = this.session.beginTransaction();
		session.delete(meeting);
		transaction.commit();
	}

	public void putMeeting(Meeting meeting) {

		Transaction transaction = this.session.beginTransaction();
		session.merge(meeting);
		transaction.commit();
	}
}
