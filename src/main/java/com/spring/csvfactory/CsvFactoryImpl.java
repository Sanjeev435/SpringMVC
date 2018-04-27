package com.spring.csvfactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.spring.model.Login;
import com.spring.model.User;

@Service
public class CsvFactoryImpl implements CsvFactory {

	private static Logger LOG = Logger.getLogger(CsvFactoryImpl.class);
	private static final String CSV_FILE = "userDataBase.csv";

	@Override
	public User validateUser(Login login) throws IOException {
		return read(new User(login.getUsername(), login.getPassword()));
	}

	@Override
	public User read(User user) throws IOException {
		List<User> users = getAllUsers();
		if (!CollectionUtils.isEmpty(users)) {
			for (User userFromService : users) {
				if (userFromService.getUsername().equalsIgnoreCase(user.getUsername())
						&& userFromService.getPassword().equalsIgnoreCase(user.getPassword())) {
					return userFromService;
				}
			}
		}
		return null;
	}

	@Override
	public String write(User user) throws IOException {

		Path path = checkIfFileExistElseCreate();

		if (read(user) != null) {
			return "User Exists";
		} else {
			try (BufferedWriter writer = Files.newBufferedWriter(path);

					CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Username", "Password",
							"Firstname", "Lastname", "Email", "Address", "Phone"));) {

				// used to update
				List<User> users = getAllUsers();
				if (!CollectionUtils.isEmpty(users)) {
					for (User existingUser : users) {
						printRecords(csvPrinter, existingUser);
					}
				}
				printRecords(csvPrinter, user);
				csvPrinter.flush();
				
				LOG.info("CSV file successfully written !!");

				return "success";
			}
		}
	}

	private void printRecords(CSVPrinter csvPrinter, User user) throws IOException {
		csvPrinter.printRecord(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(),
				user.getEmail(), user.getAddress(), user.getPhone());
	}

	private List<User> getAllUsers() throws IOException {
		List<User> userData = new ArrayList<User>();
		Path path = checkIfFileExistElseCreate();

		try (Reader reader = Files.newBufferedReader(path);
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

		) {
			for (CSVRecord csvRecord : csvParser) {
				userData.add(setUserData(csvRecord));
			}
		}

		return userData;
	}

	private User setUserData(CSVRecord csvRecord) {
		User user = new User();

		user.setUsername(csvRecord.get("Username"));
		user.setPassword(csvRecord.get("Password"));
		user.setFirstname(csvRecord.get("Firstname"));
		user.setLastname(csvRecord.get("Lastname"));
		user.setEmail(csvRecord.get("Email"));
		user.setAddress(csvRecord.get("Address"));
		user.setPhone(Integer.parseInt(csvRecord.get("Phone")));

		return user;
	}

	private Path checkIfFileExistElseCreate() throws IOException {
		String pathName = getClass().getClassLoader().getResource(CSV_FILE).getPath();
		if (pathName.contains("%20")) {
			pathName = pathName.replaceAll("%20", " ");
		}
		File file = new File(pathName);
		Path path = Paths.get(file.getAbsolutePath());

		boolean pathNotExist = Files.notExists(path);

		if (pathNotExist) {
			file.createNewFile();
			LOG.info("File Not Found !! Created new file for  : " + CSV_FILE + " at location : "
					+ file.getAbsolutePath());
		}else {
			LOG.info("File found successfully !!");
		}

		return path;
	}

}
