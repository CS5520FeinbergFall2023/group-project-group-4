package edu.northeastern.groupproject_outandabout.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import edu.northeastern.groupproject_outandabout.data.LoginRepository;
import edu.northeastern.groupproject_outandabout.data.Result;
import edu.northeastern.groupproject_outandabout.data.model.LoggedInUser;
import edu.northeastern.groupproject_outandabout.R;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        Result result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            Result.Success successResult = (Result.Success) result;
            Object data = successResult.getData();

            if (data instanceof LoggedInUser) {
                LoggedInUser loggedInUser = (LoggedInUser) data;
                String email = loggedInUser.getEmail(); // Ensure getEmail() is defined in LoggedInUser
                String displayName = loggedInUser.getDisplayName();
                loginResult.setValue(new LoginResult(new LoggedInUserView(displayName, email)));
            } else {
                // Handle the case where data is not an instance of LoggedInUser
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }



    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}