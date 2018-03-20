package service;

import java.util.Date;

import dao.RecordDAO;
import entity.Category;
import entity.Record;


public class RecordService {
	RecordDAO recordDAO = new RecordDAO();
	public void add(int spend,Category category, String comment, Date date) {
		Record record = new Record();
		record.spend = spend;
		record.cid = category.id;
		record.comment = comment;
		record.date = date;
		recordDAO.add(record);
	}
}
