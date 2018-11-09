package io.dDriven.unleash.login.AuthenticationAndAuthorization;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login {
	public Login(){
		
	}
    private static final transient Logger log = LoggerFactory.getLogger(Login.class);

    public void check(String userName,String password) {
       log.info("My First Apache Shiro Application");
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("resources/shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        
        Subject currentUser = SecurityUtils.getSubject();
        
        Session session = currentUser.getSession();
        session.setAttribute( "someKey", "aValue" );
        String value = (String) session.getAttribute("someKey");
        
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }
       

  
        if (currentUser.hasRole("role_admin")) {
            System.out.println("role_admin is assigned");
        } else {
        	System.out.println("role_admin is not assigned");
        }
        String perm = "edit every thing";

        if(currentUser.isPermitted(perm)){
        	System.out.println(perm);
        } else {
            //don’t show the button?
        }
        currentUser.logout();
        
    }
}