package com.example.rohit.interviewapp.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForUsers;
import com.example.rohit.interviewapp.MainActivity;
import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;
import com.example.rohit.interviewapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    Button cancel_button;
    Button add_button;
    ImageButton addUser;

    private String name;
    private String userName;
    private String email;
    private String phone;
    private String website;

    private String street;
    private String suite;
    private String city;
    private String zipCode;

    private String lat;
    private String lng;

    private String companyName;
    private String catchPhrase;
    private String bs;

    View view;

    UserModel userModel = new UserModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        cancel_button = (Button) view.findViewById(R.id.cancel_Button_user);
        add_button= (Button) view.findViewById(R.id.add_Button_user); // add button for adding user details.

        addUser = (ImageButton) view.findViewById(R.id.addButtonId);

        final TextView user_name = (TextView) view.findViewById(R.id.user_name);
        final TextView user_userName = (TextView) view.findViewById(R.id.user_userName);
        final TextView user_user_email = (TextView) view.findViewById(R.id.user_email);
        final TextView user_phone = (TextView) view.findViewById(R.id.user_phone);
        final TextView user_website = (TextView) view.findViewById(R.id.user_website);

        final TextView user_user_street = (TextView) view.findViewById(R.id.user_street);
        final TextView user_user_suit = (TextView) view.findViewById(R.id.user_suit);
        final TextView user_user_city = (TextView) view.findViewById(R.id.user_city);
        final TextView user_user_zipcode = (TextView) view.findViewById(R.id.user_zipcode);

        final TextView user_user_lat = (TextView) view.findViewById(R.id.user_lat);
        final TextView user_user_long = (TextView) view.findViewById(R.id.user_long);

        final TextView user_user_company_name = (TextView) view.findViewById(R.id.user_company_name);
        final TextView user_user_company_catchphrase = (TextView) view.findViewById(R.id.user_company_catchphrase);
        final TextView user_user_company_bs = (TextView) view.findViewById(R.id.user_company_bs);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("user_name", user_name.getText().toString());
                userModel.setName(user_name.getText().toString());
                userModel.setUserName(user_userName.getText().toString());
                userModel.setEmail(user_user_email.getText().toString());
                userModel.setPhone(user_phone.getText().toString());
                userModel.setWebsite(user_website.getText().toString());


                UserModel.Company company = userModel.new Company();
                UserModel.Address address = userModel.new Address();
                UserModel.Address.Geo geo = address.new Geo();

//                company.setCompanyName("test company");
//                userModel.setCompany(company);

//                geo.setLng("1111111");
//                geo.setLat("11111111");
//                address.setGeo(geo);
//                userModel.setAddress(address);

                //userModel.setCompany(new UserModel.Company().setCompanyName());

                address.setStreet(user_user_street.getText().toString());
                address.setSuite(user_user_suit.getText().toString());
                address.setCity(user_user_city.getText().toString());
                address.setZipCode(user_user_zipcode.getText().toString());

                //userModel.setCompany(company);
//
                geo.setLat(user_user_lat.getText().toString());
                geo.setLng(user_user_long.getText().toString());

                address.setGeo(geo);
                userModel.setAddress(address);

                company.setCompanyName(user_user_company_name.getText().toString());
                company.setCatchPhrase(user_user_company_catchphrase.getText().toString());
                company.setBs(user_user_company_bs.getText().toString());

                userModel.setCompany(company);

                MainActivity mainActivity = new MainActivity();

                mainActivity.addToUserModelList(userModel); // adds object to UserModelList. The same list used for UserAdapter
//                List<UserModel>userModelList = mainActivity.getUserModelList();
//
//                userModelList.add(userModel);
//                ListView listView = (ListView)view.findViewById(R.id.fullListViewUsers);
//
//                CustomAdapterForUsers customAdapter = mainActivity.getCustomAdapter();
//
//                customAdapter.notifyDataSetChanged();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FetchApiData fetchApiData = new FetchApiData();
                        fetchApiData.postUser(userModel);
                    }
                }).start();


            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
                MainActivity mainActivity = (MainActivity)getActivity();

                ListView listView = (ListView)getActivity().findViewById(R.id.fullListViewUsers);
                listView.setVisibility(View.VISIBLE);
//                addUser.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
