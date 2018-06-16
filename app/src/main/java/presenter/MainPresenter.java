package presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.hariofspades.dagger2advanced.di.AppModule;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.model.Address;
import com.hariofspades.dagger2advanced.model.ApiResponse;
import com.hariofspades.dagger2advanced.model.PersonalDetails;
import com.hariofspades.dagger2advanced.model.Phone;
import com.hariofspades.dagger2advanced.model.UserCredentials;
import com.hariofspades.dagger2advanced.model.UserDetails;
import com.hariofspades.dagger2advanced.network.NetworkManager;
import com.hariofspades.dagger2advanced.security.AESAlgorithm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPresenter {

    NetworkManager mNetworkManager = new NetworkManager();

    Retrofit retrofit;
    RandomUsersApi mRandomUsersApi;
    @Inject
    public MainPresenter() {}

    public void set(Retrofit retrofit, RandomUsersApi randomUsersApi) {
        this.retrofit = retrofit;
        mRandomUsersApi = randomUsersApi;
    }

    public String sayHello() {
        return "Hello";
    }

    public void callJoinApi(String userName, String password) {
        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        UserCredentials userCredentials = new UserCredentials(userName, password, timeStamp);

        String data = AppModule.provideGson().toJson(userCredentials);
        String encryptedData = AESAlgorithm.encrypt(data);

        Map<String, String> header = mNetworkManager.getBasicHeader();
        header.put("oauth_timestamp", timeStamp);
        header.put("data", encryptedData);
        List<String> values = new ArrayList<>();
        try {
            values.add("POST");
            values.add(URLEncoder.encode(mNetworkManager.baseUrl + "account/join", "UTF-8"));
            for (Map.Entry entry : header.entrySet()) {
                values.add(URLEncoder.encode("{" + entry.getKey() + "}={" + entry.getValue() + "}","UTF-8"));
            }
            String signatureValue = mNetworkManager.joinStringListWithAmpersand(values);
            String signatureHash = mNetworkManager.generateHMACSHA256EncodedHash(signatureValue);
            header.put("oauth_signature",signatureHash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Call<ApiResponse> response = mRandomUsersApi.join(header, getDummyUserDetails());
        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(MainPresenter.class.getSimpleName(), String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    private UserDetails getDummyUserDetails() {
        return new UserDetails("", new PersonalDetails("11/11/1990",
                new Address("Suburb", "country", "city", "postal", "l1", "l2"),
                "FamilyName", new Phone("STD", "IDD", "local"),
                "Title", "GivenName", "temp@gmail.com"));

    }

    public RandomUsersApi getRandomUserService(){
        return retrofit.create(RandomUsersApi.class);
    }


}
