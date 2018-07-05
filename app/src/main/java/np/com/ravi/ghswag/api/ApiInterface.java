package np.com.ravi.ghswag.api;

import java.util.List;

import np.com.ravi.ghswag.model.GithubUser;
import np.com.ravi.ghswag.model.UserRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ravi on 12/27/17.
 */

public interface ApiInterface {
    /**
     *
     * @param user
     * @return GithubUser object
     */
    @GET("/users/{user}")
    Call<GithubUser> getGithubUserDetails(@Path("user") String user);

    /**
     *
     * @param user
     * @return List of UserRepo object
     */
    @GET("/users/{user}/repos")
    Call<List<UserRepo>> getGithubUserRepos(@Path("user") String user);
}
