package APITesting.com.org.api;
import org.testng.Assert;
import org.testng.annotations.*;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;
public class WeatherGetRequest {
	//Simple get request for getting weather request by city name
	//status Code: 200
	//@Test
	public void Test_01(){
		Response resp= when().
				get("http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1");

		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);
	}

	//how to do parameterization with rest assured

	//@Test
	public void Test_02(){
		Response resp= given().
				param("q", "London").
				param("appid","b1b15e88fa797225412429c1c50c122a1").				
				when().
				get("http://samples.openweathermap.org/data/2.5/weather?");

		System.out.println(resp.getStatusCode());
		Assert.assertEquals(resp.getStatusCode(), 200);

		if(resp.getStatusCode()==200){
			System.out.println("API is working correctly");
		}
		else{
			System.out.println("API is not working correctly");
		}
	}

	//assert our test case in Rest Assured API
	//@Test
	public void Test_03(){
		given().
		param("q", "London").
		param("appid","b1b15e88fa797225412429c1c50c122a1").				
		when().
		get("http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1").
		then().
		assertThat().statusCode(200);

	}
	//Getting the response as string
	//@Test
	public void Test_04(){
		Response resp= given().
				param("q", "London").
				param("appid","b1b15e88fa797225412429c1c50c122a1").				
				when().
				get("http://samples.openweathermap.org/data/2.5/weather");
		System.out.println(resp.asString());
	}
	//get response code using zip code
	//@Test
	public void Test_05(){
		Response resp= given().
				param("zip", "32801,us").
				param("appid","b1b15e88fa797225412429c1c50c122a1").
				when().get("http://samples.openweathermap.org/data/2.5/weather");
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asString());
	}

	//Verify the response return is json and get specific element using json path
	//@Test
	public void Test_06(){
		String weatherreport= given().
				parameter("q", "London").
				parameter("appid","b1b15e88fa797225412429c1c50c122a1").
				when().get("http://samples.openweathermap.org/data/2.5/weather").
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");

		System.out.println("Weather report is " +weatherreport);
	}

	//Verify the response return is json and get specific element using json path. assert expected result and actual result
	//@Test
	public void Test_07(){
		Response resp= given().
				parameter("q", "London").
				parameter("appid","b1b15e88fa797225412429c1c50c122a1").
				when().get("http://samples.openweathermap.org/data/2.5/weather");
		String actualWeatherReport=	resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");

		String expectedWeatherReport = null;
		if (actualWeatherReport.equalsIgnoreCase(expectedWeatherReport)){

			System.out.println("TestCase Pass");
		}
		else{
			System.out.println("TestCase Failed");
		}
	}
	
	//get weather using longitude and latitude that was retrieved
	@Test
	public void Test_08(){
		Response resp= given().
				parameter("id", "2643743").
				parameter("appid","b1b15e88fa797225412429c1c50c122a1").
				when().get("http://samples.openweathermap.org/data/2.5/weather");
		String actualWeatherReport=	resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");

		System.out.println("Report by " +actualWeatherReport);
		
		String lon=	resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lon").toString();
		
		System.out.println("longitude is : " +lon);
		
		String lat=	resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lat").toString();
		
		System.out.println("latitude is : " +lat);
		
		String  reportbycoordinates= given().
				parameter("lat", lat).
				parameter("lon",lon).
				parameter("appid","b1b15e88fa797225412429c1c50c122a1").
				when().get("http://samples.openweathermap.org/data/2.5/weather").
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		System.out.println("Report by " +reportbycoordinates);
		
	}

	
}



