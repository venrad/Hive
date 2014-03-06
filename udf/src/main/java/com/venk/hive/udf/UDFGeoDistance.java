package com.venk.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description (
		name = "geoDistance",
		value = "_FUNC_(str) - Calculates a distance between two stores when Latitude and Longitude are given",
		extended = "Example:\n" +
		"  > SELECT geoDistance(store1.lat1, store1.lon1, store2.lat2, store2.lon2) FROM STORES stores1;\n" +
		"  CROSS JOIN STORES stores2 WHERE store1.id = 'ABC' and store2.id ='DEF'"

		)
public class UDFGeoDistance extends UDF {
	
	public double evaluate(Double lat1, Double lon1, Double lat2, Double lon2){
			double retVal =0.0;
			
			try {
				retVal = distanceFormula4(lat1,lat2,lon1,lon2, 1);
			} catch(Exception e) {
				retVal = 0.0;
			}
		return  Math.round(retVal * 100.0)/100.0;
	}
	
	public double evaluate(Double lat1, Double lon1, Double lat2, Double lon2, int milesOrKms) {
		return distanceFormula4(lat1, lat2, lon1, lon2, milesOrKms);
	}
	/**
	 * @param lat1 - Latitude of Point 1
	 * @param lat2 - Latitude of Point 2
	 * @param lon1 - Longitude of Point 1
	 * @param lon2 - Longitude of Point 2
	 * @param milesOrKms - 1 for miles and 0 for kilometers
	 * @return  distance in Miles
	 */
	private double distanceFormula4(Double lat1, Double lat2, Double lon1, Double lon2, int milesOrKms) {
		
		double R = 6371.0; // Earth's radius in Kms
		double milesToKms = 1.60934; //Miles to Kilometers conversion 
		
		double deltaLat = Math.toRadians((lat2 - lat1));
		double deltaLon = Math.toRadians((lon2 - lon1));
		
		double intermediate = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
		* Math.sin(deltaLon/2) * Math.sin(deltaLon/2);

		double distance = R * 2 * Math.atan2(Math.sqrt(intermediate), Math.sqrt(1 - intermediate));
		
		return milesOrKms == 1 ? (distance /milesToKms) : distance;
	}
	
}
