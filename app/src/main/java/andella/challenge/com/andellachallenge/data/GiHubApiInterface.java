package andella.challenge.com.andellachallenge.data;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ememobong on 15/03/2017.
 */
public interface GiHubApiInterface {

    @GET("search/users?q=location:lagos+language:java")
    Call<User> getUserDetailsLagos();

// TODO: I need to check out something with these
//    @GET("movie/top_rated")
//    Call<User> getUserDetailsLagos();

}
