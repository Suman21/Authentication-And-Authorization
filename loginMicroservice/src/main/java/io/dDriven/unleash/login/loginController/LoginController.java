package io.dDriven.unleash.login.loginController;
import javax.print.DocFlavor.STRING;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.dDriven.unleash.login.AuthenticationAndAuthorization.Login;

@RestController
public class LoginController{
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<String>login(@RequestBody String s) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(s);
		Login test=new Login();
		test.check(json.get("username").toString(), json.get("password").toString());
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}

}