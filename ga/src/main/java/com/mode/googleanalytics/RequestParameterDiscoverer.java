package com.mode.googleanalytics;

/**
 * Mechanism to discover some default request parameters. 
 */
public interface RequestParameterDiscoverer {
	
	public DefaultRequest discoverParameters(GoogleAnalyticsConfig config, DefaultRequest request);

}
