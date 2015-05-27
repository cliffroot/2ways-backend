package two.ways.service;


public class App  {
	public static void send( String message, String title, String regId  ) {
		System.out.println( "Sending POST to GCM" );

		String apiKey = "AIzaSyCevx9E_9i6QOHrjLKz8zBYcTkNEk14x9I";
		Content content = createContent(message, title, regId);

		POST2GCM.post(apiKey, content);
	}

	public static Content createContent(String message, String title, String regId){

		Content c = new Content();
		c.addRegId(regId);
		c.createData(title, message);

		return c;
	}
}
