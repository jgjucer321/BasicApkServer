package main;

import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.ObjectMapper;

public class ApkMetadata {

	public String Packagename;
	public int NrOfPermissions;
	public String iconData;
	
	public ApkMetadata(String Packagename, int NrPermissions, String iconData) {
		this.Packagename = Packagename;
		this.NrOfPermissions = NrPermissions;
		this.iconData = iconData;
	
	}
	
	public String toJson() {
		String Json ="";
		try {
		Json = new ObjectMapper().writeValueAsString(this);
		}
		catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return Json;
	}
}
