package com.example.rohit.interviewapp;

import java.util.List;

/**
 * Created by Rohit on 1/15/2016.
 */
public class UserModel {

    private int userId;
    private String name;
    private String userName;
    private String email;
    private String phone;
    private String website;
    private List<Address> addressList;
    private List<company> companies;

    UserModel() {
    }

    public class Address{
        private String street;
        private String suite;
        private String city;
        private String zipCode;
        private List<Geo> geoList;

        Address(){

        }

        public class Geo{
            private String lat;
            private String lng;

            Geo(){
            }
        } // End of Geo
    } // End of Address

    public class company{
        private String companyName;
        private String catchPhrase;
        private String bs;

        company(){
        }
    }
}
