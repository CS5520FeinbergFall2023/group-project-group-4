package edu.northeastern.groupproject_outandabout.data;

import edu.northeastern.groupproject_outandabout.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result.Error login(String ignoredUsername, String ignoredPassword) {
        try {
            // TODO: handle loggedInUser authentication
            // Add a fake email for the fakeUser
            String fakeEmail = "jane.doe@example.com"; // Replace with actual logic to retrieve email
            new LoggedInUser(
                    java.util.UUID.randomUUID().toString(),
                    "Jane Doe",
                    fakeEmail);
            return Result.Success;
        } catch (Exception e) {
            return new Result.Error();
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
