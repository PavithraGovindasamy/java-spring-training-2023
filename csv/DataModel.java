package csv;
import java.util.List;
import java.util.Set;

public class DataModel {
    private int period;
    private String births_deaths;
    private String region;
	private int overAllCount;
	static int highest = 0;
    static int year = 0;
    static int highestBirthDate = 0;
    static int highestDeathRate = 0;
    static int highestBirthRateyear = 0;
    static int highestDeathRateYear = 0;
    static int birthCount = 0;
    static int deathCount = 0;
    

    public DataModel(int period, String births_deaths,String region,int overAllCount) {
        this.period = period;
        this.births_deaths=births_deaths;
        this.region = region;
        this.overAllCount=overAllCount;
    }

    public int getPeriod() {
        return period;
    }
    public String getBirthOrDeaths() {
    	return births_deaths;
    }

    public String getRegion() {
        return region;
    }
    public int getOverAllCount() {
    	return overAllCount;
    }
    

    public static void displayOverallHighestRegionSalary(List<DataModel> dataModels,Set<String> uniqueRegions) {

        for (String region : uniqueRegions) {
            for (DataModel dataModel : dataModels) {
                if (dataModel.getRegion().equals(region)) {

                    if (dataModel.getBirthOrDeaths().equals(Constants.BIRTHS_CONSTANT)) {
                        if (dataModel.getOverAllCount() > highestBirthDate) {
                            highestBirthDate = dataModel.getOverAllCount();
                            year = dataModel.getPeriod();

                        }
                    } else if (dataModel.getBirthOrDeaths().equals(Constants.DEATHS_CONSTANT)) {
                        if (dataModel.getOverAllCount() > highestDeathRate)
                            highestDeathRate = dataModel.getOverAllCount();

                    }
                }
            }
            System.out.println("Year:" + year + " Highest BirthRate " + highestBirthDate +" region " +region);
            System.out.println("Year:" + year + " Highest DeathRate " + highestDeathRate+" region "+region);
            highestBirthDate = 0;
            highestDeathRate = 0;

        }
    }

     public static void displayOverallHighestSalary(List<DataModel> dataModels,Set<String> uniqueRegions) {
        for (String region : uniqueRegions) {
            for (DataModel dataModel : dataModels) {
                if (dataModel.getRegion().equals(region)) {
                    if (dataModel.getBirthOrDeaths().equals(Constants.BIRTHS_CONSTANT)) {
                        if (dataModel.getOverAllCount() > highestBirthDate) {
                            highestBirthDate = dataModel.getOverAllCount();
                            highestBirthRateyear = dataModel.getPeriod();
                        }
                    } else if (dataModel.getBirthOrDeaths().equals(Constants.DEATHS_CONSTANT)) {
                        if (dataModel.getOverAllCount() > highestDeathRate)
                            highestDeathRate = dataModel.getOverAllCount();
                        highestDeathRateYear = dataModel.getPeriod();
                    }
                }
            }

        }
        System.out.println("Highest BirthRate " + highestBirthDate + "year " + highestBirthRateyear);
        System.out.println("Highest DeathRate " + highestDeathRate + "year " + highestDeathRateYear);

    }

    public static void displayOverallBirthDeathCounts(List<DataModel> dataModels) {
        for (DataModel dataModel : dataModels) {
            if (dataModel.getBirthOrDeaths().equals(Constants.BIRTHS_CONSTANT)) {
                birthCount = dataModel.getOverAllCount() + birthCount;
            } else if (dataModel.getBirthOrDeaths().equals(Constants.DEATHS_CONSTANT)) {
                deathCount = dataModel.getOverAllCount() + deathCount;
                ;
            }
        }

        System.out.println("BirthCount:" + birthCount + " DeathCount:" + deathCount);
        System.out.println("");

    }

    public static void displayYearWiseCount(List<DataModel> dataModels,Set<Integer> uniqueYears) {
        for (int year : uniqueYears) {
            for (DataModel dataModel : dataModels) {
                if (dataModel.getPeriod() == year) {
                    if (dataModel.getBirthOrDeaths().equals(Constants.BIRTHS_CONSTANT)) {
                        birthCount = dataModel.getOverAllCount() + birthCount;
                    } else if (dataModel.getBirthOrDeaths().equals(Constants.DEATHS_CONSTANT)) {
                        deathCount = dataModel.getOverAllCount() + deathCount;
                    }
                }
            }
            System.out.println("Year: " + year);
            System.out.println("Birth Count: " + birthCount);
            System.out.println("Death Count: " + deathCount);
            System.out.println();
            birthCount = 0;
            deathCount = 0;

        }

    }

    public static void displayRegionWiseCount(List<DataModel> dataModels,Set<String> uniqueRegions) {
        for (String region : uniqueRegions) {
            for (DataModel dataModel : dataModels) {
                if (dataModel.getRegion().equals(region)) {
                    if (dataModel.getBirthOrDeaths().equals(Constants.BIRTHS_CONSTANT)) {
                        birthCount = dataModel.getOverAllCount() + birthCount;
                    } else if (dataModel.getBirthOrDeaths().equals(Constants.DEATHS_CONSTANT)) {
                        deathCount = dataModel.getOverAllCount() + deathCount;

                    }
                }
            }
            System.out.println("Region: " + region);
            System.out.println("Birth Count: " + birthCount);
            System.out.println("Death Count: " + deathCount);
            System.out.println();
            birthCount = 0;
            deathCount = 0;

        }
    }

    public static void displayUniqueYears(Set<Integer> uniqueYears) {
        System.out.println("Unique Years:");
        for (int year : uniqueYears) {
            System.out.println(year);
        }
        System.out.println();
    }

    public static void displayUniqueRegions( Set<String> uniqueRegions) {
        System.out.println("Unique Regions:");
        for (String region : uniqueRegions) {
            System.out.println(region);
        }
        System.out.println();
    }
    
    

 
}
