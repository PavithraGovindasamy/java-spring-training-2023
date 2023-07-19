package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 
 * @author pavithra
 * class that implements methods to display various regions birth and death counts
 * 
 *
 */
public class Main {
	static List<DataModel> dataModels = new ArrayList<>();
	static Set<String> uniqueRegions = new HashSet<>();
	static Set<Integer> uniqueYears = new HashSet<>();
	static DataModel dataModel;
    /**
     * 
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(
				new FileReader("/Users/pavithra/eclipse-workspace/CSV_FILE/src/csv/exercis3.csv"));
		String line;
		reader.readLine();

		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			int period = Integer.parseInt(values[0]);
			String birth_death = values[1].trim();
			String region = values[2].trim();
			int overAllCount = Integer.parseInt(values[3]);

			DataModel dataModel = new DataModel(period, birth_death, region, overAllCount);
			dataModels.add(dataModel);
			uniqueRegions.add(region);
			uniqueYears.add(period);
		}
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice");
			System.out.println("1. Display Unique Regions");
			System.out.println("2. Display Unique years");
			System.out.println("3. Display OverAll Birth Death Counts");
			System.out.println("4. Display Region Wise Count");
			System.out.println("5. Display Year Wise Count");
			System.out.println("6. Display Overall Highest Salary");
			System.out.println("7. Display Overall Highest Region Salary");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				DataModel.displayUniqueRegions(uniqueRegions);
				break;
			case 2:
				DataModel.displayUniqueYears(uniqueYears);
				break;
			case 3:
				DataModel.displayOverallBirthDeathCounts(dataModels);
				break;
			case 4:
				DataModel.displayRegionWiseCount(dataModels,uniqueRegions);
				break;
			case 5:
				DataModel.displayYearWiseCount(dataModels,uniqueYears);
				break;
			case 6:
				DataModel.displayOverallHighestSalary(dataModels,uniqueRegions);
				break;
			case 7:
				DataModel.displayOverallHighestRegionSalary(dataModels,uniqueRegions);
				break;
			default:
				System.out.println("");
				break;

			}
		}

	}

}
