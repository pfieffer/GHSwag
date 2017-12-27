package np.com.ravi.ghswag.api;

import np.com.ravi.ghswag.model.GithubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ravi on 12/27/17.
 */

public interface ApiInterface {
    /*
    * Retrofit get annotation with our URL
    * And our method that will return us details of user.
   */
    @GET("/users/{user}")
    Call<GithubUser> getGithubUserDetails(@Path("user") String user);
}
