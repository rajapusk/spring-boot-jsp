package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.ExpenseEntity;
import com.cardview.demo.model.MyImage;
import com.cardview.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

	@Value("${spring.project.directory}")
	private String targetPath;

	@Autowired
	ExpenseRepository repository;

	/**
	 * This method is used to fetch all the expense entries from the database.
	 * @return List<ExpenseEntity> This returns the list of expense entities fetched from the database.
	 */
	public List<ExpenseEntity> getAllExpenses()
	{
		List<ExpenseEntity> result = (List<ExpenseEntity>) repository.findAll();
		
		if(result.size() > 0) {

			for (ExpenseEntity entity:
				 result) {
				retriveAttachmentsAndStore(entity.getAttach());
			}
			return result;
		} else {
			return new ArrayList<ExpenseEntity>();
		}
	}

	/**
	 * This method is used to get a single expense entry from the database based on its id.
	 * @param id This attribute is the id of the expense entry.
	 * @return ExpenseEntity This returns the ExpenseEntity object for the requested expense id.
	 */
	public ExpenseEntity getExpenseById(Long id) throws RecordNotFoundException
	{
		Optional<ExpenseEntity> expense = repository.findById(id);
		
		if(expense.isPresent()) {
			return expense.get();
		} else {
			throw new RecordNotFoundException("No expense record exist for given id");
		}
	}

	/**
	 * This method is used to update all the columns of the Expense entry in the database.
	 * @param entity This attribute is the ExpenseEntity object with pre-populated data for all columns.
	 * @return String This returns updated ExpenseEntity object.
	 */
	public ExpenseEntity createOrUpdateExpense(ExpenseEntity entity)
	{
		if(entity.getId()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		} 
		else 
		{
			Optional<ExpenseEntity> employee = repository.findById(entity.getId());
			
			if(employee.isPresent()) 
			{
				ExpenseEntity newEntity = employee.get();
				newEntity.setExpenseType(entity.getExpenseType());
				newEntity.setCustomerType(entity.getCustomerType());
				newEntity.setPurpose(entity.getPurpose());
				newEntity.setVendorName(entity.getVendorName());
				newEntity.setInvoiceDate(entity.getInvoiceDate());
				newEntity.setInvoiceNumber(entity.getInvoiceNumber());
				newEntity.setInvoiceAmount(entity.getInvoiceAmount());
				newEntity.setSanctionedAmount(entity.getSanctionedAmount());
				newEntity.setClaimedAmount(entity.getClaimedAmount());
				newEntity.setRemarks(entity.getRemarks());
				newEntity.setAttachments(entity.getAttachments());
				newEntity.setAttach(retriveAttachmentsAsBytes(entity.getAttachments()));

				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				
				return entity;
			}
		}
	}

	/**
	 * This method is used to retrive all the attachments from the database and store it as files in the local server.
	 * @param bytes This attribute is the byte array of attachments retrieved from the database.
	 * @return void
	 */
	public void retriveAttachmentsAndStore(byte[] bytes){
		try{
			if(bytes != null){
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				ObjectInputStream in = new ObjectInputStream(bais);
				ArrayList<MyImage> attachments = (ArrayList<MyImage>) in.readObject();
				if(attachments != null){
					for(MyImage obj : attachments){
						byte[] arr = obj.getImageAsBytes();
						String name = obj.getImageName();
						File file = new File(targetPath+"/WEB-INF/classes/static/images/");
						if (!file.exists()) {
							file.mkdirs();
						}
						BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(new File(targetPath+"/WEB-INF/classes/static/images/"+name)));
						stream.write(arr);
						stream.flush();
						stream.close();
					}
				}
				in.close();
				bais.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to create byte array from the uploaded file which can be stored in the database.
	 * @param filenames This attribute is the names of files which will be byte converted.
	 * @return byte[] This returns the byte array of all the attachments for a particular expense entity.
	 */
	public byte[] retriveAttachmentsAsBytes(String filenames){
		ArrayList<MyImage> attachments = new ArrayList<MyImage>();
		try{
			if(filenames != null){
				String[] names = filenames.split("\\|");
				for (String name : names)
				{
					if(name.trim().length() > 0){
						File file = new File(targetPath+"/WEB-INF/classes/static/images/"+name);
						byte[] arr = Files.readAllBytes(file.toPath());
						attachments.add(new MyImage(name, arr));
					}
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(baos);
				out.writeObject(attachments);
				byte[] bytes = baos.toByteArray();
				out.flush();
				baos.close();
				return bytes;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is used to delete the expense entry from the database by its id.
	 * @param id This attribute is the id of the expense entry to be deleted.
	 * @return void
	 */
	public void deleteExpenseById(Long id) throws RecordNotFoundException
	{
		Optional<ExpenseEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) 
		{
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No expense record exist for given id");
		}
	} 
}