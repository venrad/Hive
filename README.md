Hive
====

Hive UDF to calculate the distance between two places given the latitude and longitudes of the places. usage

hive> Add jar <add the jar including the path>;
hive> Create temporary function geodistance as 'com.venk.hive.udf.UDFGeoDistance';
hive> SELECT geodistance(lat1, lon1, lat2, lon2) FROM table_with_lats_lons; 


