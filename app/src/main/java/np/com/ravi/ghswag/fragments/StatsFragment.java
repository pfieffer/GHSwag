package np.com.ravi.ghswag.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import np.com.ravi.ghswag.R;
import np.com.ravi.ghswag.activity.MainActivity;
import np.com.ravi.ghswag.api.ApiClient;
import np.com.ravi.ghswag.api.ApiInterface;
import np.com.ravi.ghswag.model.UserRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsFragment extends Fragment {
    private static final String TAG = StatsFragment.class.getSimpleName();
    private List<UserRepo> userRepoList;

    //for languages piechart
    HashMap<String, Integer> languageCount;
    PieChart languagesPieChart;
    ArrayList<PieEntry> pieEntries;
    PieDataSet pieDataSet;
    PieData pieData;
    List<String> languagesList;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepoList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        languagesPieChart = rootView.findViewById(R.id.pie_chart_languages);
        pieEntries = new ArrayList<>();
        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //setting data to chart using respective chart data
        languagesPieChart.setData(pieData);
        languagesPieChart.setNoDataText("No Data provided"); // IMP: method to set text if no data is supplied

        languagesPieChart.animateY(3000);
        languagesPieChart.setHoleRadius(30f);

        //Customize the piechart pieChartLegend here:
        Legend pieChartLegend = languagesPieChart.getLegend();
        pieChartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChartLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        pieChartLegend.setFormSize(22f);
        pieChartLegend.setForm(Legend.LegendForm.CIRCLE);


        //customise piechart description here
        final Description pieDesc = new Description();
        pieDesc.setText("Languages used");
        pieDesc.setTextSize(20f); //min 6f
//        pieDesc.setPosition(1000, 200);
        languagesPieChart.setDescription(pieDesc);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity parent = (MainActivity) getActivity();
        hitGetStatsApi(parent.ghUsername);

//        view.findViewById(R.id.pie_chart_languages).set
    }

    private void hitGetStatsApi(final String username) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<List<UserRepo>> call = apiService.getGithubUserRepos(username);

       call.enqueue(new Callback<List<UserRepo>>() {
           @Override
           public void onResponse(Call<List<UserRepo>> call, Response<List<UserRepo>> response) {
//               Log.d(TAG, response.body().toString());
//               for (UserRepo userRepo: response.body()){
//                   Log.d(TAG, userRepo.getName());
//                   if (userRepo.getLanguage()!=null){
//                       Log.d(TAG, userRepo.getLanguage());
//                   }
//               }
               try {
                   if (response.isSuccessful()){
                       Log.d(TAG, " Response is succssful");
                       //populate our List<UserRepo> from the response
                       if (response.body()!=null){
                           userRepoList.addAll(response.body());
                          languagesList = new ArrayList<>();
                           Set<String> languages = new HashSet<>();
                           for (UserRepo userRepo : userRepoList) {
                               // turn your data into Entry objects
                               languagesList.add(userRepo.getLanguage());
                               languages.add(userRepo.getLanguage());
                           }
                           languageCount = new HashMap<>();
                           for (String language: languages){
                               int value = Collections.frequency(languagesList, language);
//                               languageCount.put(language, Collections.frequency(languagesList, language));
                               addValuesToPieEntry(value, language);
                           }
                           languagesPieChart.notifyDataSetChanged();
                           languagesPieChart.invalidate(); //refresh
                           Log.d(TAG, languageCount.toString());
                       } else {
                           Log.e(TAG, " Response body is null");
                       }
                   } else {
                       Log.e(TAG, "Response is not successful");
                   }
               } catch (Exception e){
                   Log.e(TAG, " Caught Exception");
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(Call<List<UserRepo>> call, Throwable t) {
                Log.d(TAG, " in onFailure()");
                t.printStackTrace();
           }
       });
    }

    private void addValuesToPieEntry(int value, String language) {
        pieEntries.add(new PieEntry(value, language));
    }


}
